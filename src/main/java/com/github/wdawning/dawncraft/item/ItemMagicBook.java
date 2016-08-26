package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.entity.EntityMagnetBall;
import com.github.wdawning.dawncraft.worldgen.WorldTeleporterDawn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemMagicBook extends Item
{
	public ItemMagicBook()
	{
		super();
		this.setUnlocalizedName("magicBook");
        this.setMaxStackSize(16);
		this.setCreativeTab(CreativeTabsLoader.tabMagic);
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
		return itemStackIn;
    }
}
