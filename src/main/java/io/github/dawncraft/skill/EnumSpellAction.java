package io.github.dawncraft.skill;

import java.util.Locale;

public enum EnumSpellAction
{
    NONE,
    PREPARE,
    SPELL;
    
    public String getUnlocalizedName()
    {
        return "gui.skill." + this.name().toLowerCase(Locale.ENGLISH);
    }
}
