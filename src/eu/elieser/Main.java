package eu.elieser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.elieser.data.Spell;
import eu.elieser.data.Spells;
import eu.elieser.parser.DarkSunParser;
import eu.elieser.parser.Dss2SpellParser;
import eu.elieser.parser.SpellParser;
import eu.elieser.reader.ReadWriteTextFileJDK7;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final String SPELLS_PATH = "data/spells.txt";
    private static final String DSS2_PRIEST_SPELL_PATH = "data/dss2.txt";
    private static final String DARK_SUN_PRIEST_SPELL_PATH = "data/darksun.txt";
    private static ReadWriteTextFileJDK7 reader;

    private static List<Spell> allSpells;

    public static void main(String[] args)
    {
        reader = new ReadWriteTextFileJDK7();
        allSpells = new ArrayList<>();

        List<String> lines = reader.readTextFile(SPELLS_PATH);
        ParsePhb(lines);

        List<String> lines2 = reader.readTextFile(DSS2_PRIEST_SPELL_PATH);
        ParseDss2(lines2);

        List<String> lines3 = reader.readTextFile(DARK_SUN_PRIEST_SPELL_PATH);
        ParseDs(lines3);

        Spells spells = new Spells();
        spells.setSpells(allSpells);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(spells);

        reader.write(json, "data/all_spells");
    }

    private static void ParseDs(List<String> lines)
    {
        DarkSunParser parser = new DarkSunParser();
        List<Spell> spellList = parser.parse(lines);

        Spells spells = new Spells();
        spells.setSpells(spellList);
        allSpells.addAll(spellList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(spells);

        reader.write(json, "data/dark_sun_spells");
    }

    private static void ParseDss2(List<String> lines)
    {
        Dss2SpellParser parser = new Dss2SpellParser();
        List<Spell> spellList = parser.parse(lines);

        Spells spells = new Spells();
        spells.setSpells(spellList);
        allSpells.addAll(spellList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(spells);

        reader.write(json, "data/dss2_spells");
    }

    private static void ParsePhb(List<String> lines)
    {
        SpellParser parser = new SpellParser();
        List<Spell> spellList = parser.parse(lines);

        Spells spells = new Spells();
        spells.setSpells(spellList);
        allSpells.addAll(spellList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(spells);

        reader.write(json, "data/spells");
    }
}
