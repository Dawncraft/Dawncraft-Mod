package io.github.dawncraft.skill;

import java.util.Locale;

public enum EnumSpellResult
{
    NONE,
    SELECT,
    PREPARING,
    SPELLING,
    
    COOLING,
    SILENT,
    NOMANA,
    NOTARGET,

    MISSED,
    BROKEN,
    
    CANCEL;
    
    public boolean isSpelling()
    {
        return this == PREPARING || this == SPELLING;
    }
    
    public boolean isSpellFailed()
    {
    	return this.ordinal() > SPELLING.ordinal();
    }
    
    public String getUnlocalizedName()
    {
        return "gui.skill." + this.name().toLowerCase(Locale.ENGLISH);
    }
}
