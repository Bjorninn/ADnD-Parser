package eu.elieser.parser;

import eu.elieser.data.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DarkSunParser
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
            line = line.replace("\u0092", "’");
            line = line.replace("\u0096", "–");

            String cline = line.toLowerCase().trim();

            checkLevel(line);

            if (cline.contains(SPHERE))
            {
                // We just found a new spell!
                spellIndex++;
                Spell spell = new Spell();
                spells.add(spell);

                String sphere = cline.replace("sphere:", "").trim();
                spell.setSphere(sphere);
                spell.setLevel(currentLevel);
                spell.setSource("ds");

                // Lets get the previous lines
                String pLine = lines.get(i - 1);
                String ppLine = lines.get(i - 2);

                String cpLine = pLine.toLowerCase().trim();
                String cppLine = ppLine.toLowerCase().trim();

                String name = "";
                String school = "";

                int linesToRemove = 2;

                if (cpLine.contains(REVERSIBLE))
                {
                    spell.setReversible(true);
                    cpLine = cppLine;
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


                int index = cpLine.indexOf('(');

                String subn = cpLine.substring(0, index).trim();
                String subs = cpLine.substring(index).trim();

                name = subn;

                school = subs;
                school = school.replace("(", "");
                school = school.replace(")", "");

                spell.setName(name);
                spell.setSchool(school);
            }
            else if (cline.contains("range:"))
            {
                String range = cline.replace("range:", "").trim();
                spells.get(spellIndex).setRange(range);
            }
            else if (cline.contains("components:"))
            {
                String comp = cline.replace("components:", "").trim();
                comp = comp.replace("component:", "").trim();

                String[] split = comp.split(",");
                for (int j = 0; j < split.length; j++)
                {
                    split[j] = split[j].trim();
                }

                List<String> splitList = Arrays.asList(split);
                spells.get(spellIndex).setComponents(splitList);
            }
            else if (cline.contains("duration:"))
            {
                String duration = cline.replace("duration:", "").trim();
                spells.get(spellIndex).setDuration(duration);
            }
            else if (cline.contains("casting time:"))
            {
                String castingTime = cline.replace("casting time:", "").trim();
                spells.get(spellIndex).setCastingTime(castingTime);
            }
            else if (cline.contains("area of effect:"))
            {
                String aoe = cline.replace("area of effect:", "").trim();
                spells.get(spellIndex).setAoe(aoe);
            }
            else if (cline.contains("saving throw:"))
            {
                String save = cline.replace("saving throw:", "").trim();
                spells.get(spellIndex).setSave(save);
            }
            else if (cline.contains("material component:"))
            {
                String mc = cline.replace("material component:", "").trim();
                spells.get(spellIndex).setComponent(mc);
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

        spell.setDescription(body.toString().trim());
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
