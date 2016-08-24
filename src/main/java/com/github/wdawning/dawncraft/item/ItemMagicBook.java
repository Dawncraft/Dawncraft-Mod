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
    	if(!worldIn.isRemote)
    	{
    	if(playerIn instanceof EntityPlayerMP)
    	{
            ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
            WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(23));
            scm.transferPlayerToDimension((EntityPlayerMP) playerIn, 23, teleporter);
        }
    	else
    	{
            ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
            WorldTeleporterDawn teleporter = new WorldTeleporterDawn(MinecraftServer.getServer().worldServerForDimension(23));
            scm.transferEntityToWorld(playerIn, 23,(WorldServer) worldIn,MinecraftServer.getServer().worldServerForDimension(23),teleporter);
        }
    	}
		return itemStackIn;
    }
}
