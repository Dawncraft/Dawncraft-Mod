package io.github.dawncraft.capability;

import io.github.dawncraft.Dawncraft;
import net.minecraft.util.ResourceLocation;

public interface IEntityMana
{
    public ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "mana");

    public float getMana();
    
    public float getMaxMana();
    
    public void setMana(float mana);

    public boolean shouldRecover();
    
    public void recover(float recoverAmount);
}
