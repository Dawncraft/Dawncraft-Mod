package io.github.dawncraft.skill;

public enum EnumSpellAction
{
    NONE,
    PREPARE,
    SPELL;

    public String getUnlocalizedName()
    {
        return "gui.skill." + this.name().toLowerCase();
    }
}
