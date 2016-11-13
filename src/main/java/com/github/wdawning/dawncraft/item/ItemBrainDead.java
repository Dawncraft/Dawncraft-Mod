package com.github.wdawning.dawncraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;
import com.github.wdawning.dawncraft.potion.PotionLoader;

public class ItemBrainDead extends ItemFood
{

	public ItemBrainDead()
	{
        super(2, 8.0F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName("brainDead");
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
	}
	
    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(PotionLoader.potionBrainDead.id, 160, 1));
            player.addExperience(29);
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}
