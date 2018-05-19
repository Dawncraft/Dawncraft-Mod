package io.github.dawncraft.capability;

public interface IMana
{
    public float getMana();

    public float getMaxMana();

    public void setMana(float mana);
    
    public boolean shouldRecover();

    public void recover(float recoverAmount);
}
