package io.github.dawncraft.capability;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.player.DrinkStats;
import net.minecraft.util.ResourceLocation;

public interface IPlayerThirst extends ICapabilityClonable<IPlayerThirst>
{
    public ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "thrist");

    public DrinkStats getDrinkStats();
    
    public boolean canDrink(boolean ignoreThristy);
}
