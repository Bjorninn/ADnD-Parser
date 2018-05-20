package eu.elieser.parser;

import eu.elieser.Log;
import eu.elieser.data.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpellParser
{
    private static final String SPHERE = "sphere:";
    private static final String REVERSIBLE = "reversible";

    private String[] levelNames = new String[]{"First-Level Spells",
            "Second-Level Spells",
            "Third-Level Spells",
            "Fourth-Level Spells",
            "Fifth-Level Spells",
            "Sixth-Level Spells",
            "Seventh-Level Spells",
            "Eighth-Level Spells",
            "Ninth-Level Spells"};

    private int currentLevel = 0;


    public List<Spell> parse(List<String> lines)
    {
        List<Spell> spells = new ArrayList<>();
        int spellIndex = -1;

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            String cline = line.toLowerCase().trim();
            checkLevel(line);

            boolean check = true;

            if (cline.contains(SPHERE))
            {
                // We just found a new spell!
                check = false;
                spellIndex++;
                Spell spell = new Spell();
                spells.add(spell);

                String sphere = cline.replace("sphere:", "").trim();
                spell.setSphere(sphere);
                spell.setLevel(currentLevel);
                spell.setSource("phb");

                // Lets get the previous lines TODO remove them from the previous spell
                String pLine = lines.get(i - 1);
                String ppLine = lines.get(i - 2);
                String pppLine = lines.get(i - 3);

                String cpLine = pLine.toLowerCase().trim();
                String cppLine = ppLine.toLowerCase().trim();
                String cpppLine = pppLine.toLowerCase().trim();

                String name = "";
                String school = "";

                int linesToRemove = 2;

                if (cpLine.contains(REVERSIBLE))
                {
                    // has reversible
                    spell.setReversible(true);
                    cpLine = cppLine;
                    cppLine = cpppLine;

                    linesToRemove++;
                }
                else
                {
                    spell.setReversible(false);
                }

                if (spellIndex > 0)
                {
                    Spell pSpell = spells.get(spellIndex - 1);

                    pSpell.bodyLines = pSpell.bodyLines.subList(0, pSpell.bodyLines.size() - linesToRemove);
                }

                if (cpLine.contains(")"))
                {
                    // Line has end of school
                    school = cpLine;

                    if (cpLine.contains("("))
                    {
                        // School is in this line
                        name = cppLine;
                    }
                    else
                    {
                        // School begins in the previous line
                        if (cppLine.contains("("))
                        {
                            int index = cppLine.indexOf('(');

                            String subs = cppLine.substring(index);
                            String subn = cppLine.substring(0, index);
                            school += subs;
                            name = subn;
                        }
                        else
                        {
                            Log.d("Bad Line: " + cppLine);
                        }
                    }
                }
                else
                {
                    Log.d("Bad Line: " + cpLine);
                }

                school = school.replace("(", "");
                school = school.replace(")", "");

                spell.setName(name);
                spell.setSchool(school);
            }
            else if (cline.contains("range:"))
            {
                check = false;
                // need to split it from components
                int index = cline.indexOf("component");

                String range = cline.substring(0, index);
                range = range.replace("range:", "").trim();

                String comp = cline.substring(index);
                comp = comp.replace("components:", "").trim();
                comp = comp.replace("component:", "").trim();

                String[] split = comp.split(",");
                for (int j = 0; j < split.length; j++)
                {
                    split[j] = split[j].trim();
                }

                List<String> splitList = Arrays.asList(split);

                spells.get(spellIndex).setRange(range);
                spells.get(spellIndex).setComponents(splitList);
            }
            else if (cline.contains("duration:"))
            {
                // need to split it from components
                int index = cline.indexOf("casting time:");

                String duration = cline.substring(0, index);
                duration = duration.replace("duration:", "").trim();

                String castingTime = cline.substring(index);
                castingTime = castingTime.replace("casting time:", "").trim();

                spells.get(spellIndex).setDuration(duration);
                spells.get(spellIndex).setCastingTime(castingTime);
            }
            else if (cline.contains("area of effect:"))
            {
                // need to split it from components
                int index = cline.indexOf("saving throw:");

                String aoe = cline.substring(0, index);
                aoe = aoe.replace("area of effect:", "").trim();

                String save = cline.substring(index);
                save = save.replace("saving throw:", "").trim();

                spells.get(spellIndex).setAoe(aoe);
                spells.get(spellIndex).setSave(save);
            }
            else if (spellIndex > -1)
            {
                spells.get(spellIndex).bodyLines.add(line);
            }
        }

        for (Spell spell :
                spells)
        {
            buildBody(spell);
            buildSpellComponentDescription(spell);
        }

        return spells;
    }

    private void buildSpellComponentDescription(Spell spell)
    {
        int index = -1;

        if (spell.getDescription().contains("The material component "))
        {
            index = spell.getDescription().indexOf("The material component");
        }
        else if (spell.getDescription().contains("The material components "))
        {
            index = spell.getDescription().indexOf("The material component");
        }

        if (index > 0)
        {
            String comp = spell.getDescription().substring(index);
            spell.setComponent(comp.trim());
        }
    }

    private void buildBody(Spell spell)
    {
        StringBuilder body = new StringBuilder();

        for (int i = 0; i < spell.bodyLines.size(); i++)
        {
            body.append(spell.bodyLines.get(i)).append(" ");
        }

        spell.setDescription(body.toString());
        spell.bodyLines.clear();
        spell.bodyLines = null;
    }

    private void checkLevel(String line)
    {
        for (int i = 0; i < levelNames.length; i++)
        {
            if (line.equals(levelNames[i]))
            {
                currentLevel++;
            }
        }
    }
}
