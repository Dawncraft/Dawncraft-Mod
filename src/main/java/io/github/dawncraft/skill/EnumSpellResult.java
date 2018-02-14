package io.github.dawncraft.skill;

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
}
