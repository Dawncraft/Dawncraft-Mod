package io.github.dawncraft.capability;

import io.github.dawncraft.magic.MagicSkill;
import io.github.dawncraft.talent.MagicTalent;

/**
 * @author QingChenW
 *
 */
public interface IMagicInventory
{
    public void setInventory(int slot, MagicSkill skill);
    
    public MagicSkill getInventory(int slot);
    
    public void setTalent(MagicTalent talent, int level);
    
    public int getTalentLevel(MagicTalent talent);
}
