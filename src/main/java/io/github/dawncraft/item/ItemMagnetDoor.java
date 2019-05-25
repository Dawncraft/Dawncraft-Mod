package io.github.dawncraft.item;

import java.util.List;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetDoor extends ItemDoor
{
    public ItemMagnetDoor()
    {
        super(BlockLoader.magnetDoor);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (super.onItemUse(itemStack, player, world, blockPos, side, hitX, hitY, hitZ))
        {
            if (!world.isRemote)
            {
                Block block = world.getBlockState(blockPos).getBlock();
                if (!block.isReplaceable(world, blockPos)) blockPos = blockPos.offset(side);
                TileEntity tileentity = world.getTileEntity(blockPos);
                if (tileentity instanceof TileEntityMagnetDoor)
                {
                    TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                    if (itemStack.hasTagCompound())
                    {
                        NBTTagCompound nbt = itemStack.getTagCompound();
                        if (nbt.hasKey("UUID", 8))
                        {
                            tileentityMagnetDoor.setUUID(nbt.getString("UUID"));
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (itemStack.hasTagCompound())
        {
            NBTTagCompound nbt = itemStack.getTagCompound();
            String name = nbt.getString("UUID");
            if (!StringUtils.isNullOrEmpty(name))
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".desc", name));
            }
        }
    }
}
