package com.github.wdawning.dawncraft.item;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRecord extends net.minecraft.item.ItemRecord
{
    protected ItemRecord(String name)
    {
		super(name);
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }

    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(dawncraft.MODID + ":" + "dawncraft." + name);
    }
}
