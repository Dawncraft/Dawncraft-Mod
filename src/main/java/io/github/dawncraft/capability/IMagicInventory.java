package io.github.dawncraft.capability;

import io.github.dawncraft.magic.Skill;
import io.github.dawncraft.talent.Talent;

/**
 * @author QingChenW
 *
 */
public interface IMagicInventory
{
    public void setInventory(int slot, Skill skill);
    
    public Skill getInventory(int slot);
    
    public void setTalent(Talent talent, int level);
    
    public int getTalentLevel(Talent talent);
}
