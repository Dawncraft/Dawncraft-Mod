package io.github.dawncraft.item;

import io.github.dawncraft.magic.Skill;
import io.github.dawncraft.magic.SkillLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMagicBook extends Item
{
	public ItemMagicBook()
	{
		super();
        this.setMaxStackSize(16);
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        SkillLoader.heal.spellMagic(itemStackIn, worldIn, playerIn);
		return itemStackIn;
    }
}
