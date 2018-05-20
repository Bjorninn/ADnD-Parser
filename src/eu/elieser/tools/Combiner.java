package eu.elieser.tools;

import eu.elieser.data.Spell;
import eu.elieser.data.Spells;

import java.util.ArrayList;
import java.util.List;

public final class Combiner
{
    public static Spells Combine(List<Spells> spellsList)
    {
        Spells spells = new Spells();
        List<Spell> spellList = new ArrayList<>();

        for (int i = 0; i < spellsList.size(); i++)
        {
            spellList.addAll(spellsList.get(i).getSpells());
        }

        spells.setSpells(spellList);
        return spells;
    }
}
