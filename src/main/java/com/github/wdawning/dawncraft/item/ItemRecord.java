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
	private static final Map RECORDS = Maps.newHashMap();
    public final String recordName;

    protected ItemRecord(String name)
    {
		super(name);
        this.recordName = name;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
        RECORDS.put("records." + name, this);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == Blocks.jukebox && !((Boolean)iblockstate.getValue(BlockJukebox.HAS_RECORD)).booleanValue())
        {
            if (worldIn.isRemote)
            {
                return true;
            }
            else
            {
                ((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, iblockstate, stack);
                worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1005, pos, Item.getIdFromItem(this));
                --stack.stackSize;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
    {
        tooltip.add(this.getRecordNameLocal());
    }

    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal()
    {
        return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    public static ItemRecord getRecord(String name)
    {
        return (ItemRecord)RECORDS.get(name);
    }

    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(dawncraft.MODID + ":" + "dawncraft." + name);
    }
}
