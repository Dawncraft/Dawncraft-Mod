package io.github.dawncraft.capability;

/**
 * @author QingChenW
 *
 */
public interface IMana
{
    public void setMana(int mana);
    
    public int getMana();
    
    public void replenish();
}
