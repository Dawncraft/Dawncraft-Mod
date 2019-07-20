package io.github.dawncraft.item;

import java.util.List;

import javax.annotation.Nullable;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetDoor extends ItemDoor
{
    public ItemMagnetDoor()
    {
        super(BlockInit.magnetDoor);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS)
        {
            if (!world.isRemote)
            {
                Block block = world.getBlockState(pos).getBlock();
                if (!block.isReplaceable(world, pos)) pos = pos.offset(facing);
                TileEntity tileentity = world.getTileEntity(pos);
                if (tileentity instanceof TileEntityMagnetDoor)
                {
                    TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                    ItemStack stack = player.getHeldItem(hand);
                    if (stack.hasTagCompound())
                    {
                        NBTTagCompound nbt = stack.getTagCompound();
                        if (nbt.hasKey("UUID", 8))
                        {
                            tileentityMagnetDoor.setUUID(nbt.getString("UUID"));
                        }
                    }
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            String name = nbt.getString("UUID");
            if (!StringUtils.isNullOrEmpty(name))
            {
                tooltip.add(TextFormatting.GRAY + I18n.format(this.getTranslationKey() + ".desc", name));
            }
        }
    }
}
