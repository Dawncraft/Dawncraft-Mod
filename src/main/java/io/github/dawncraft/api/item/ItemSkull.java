package io.github.dawncraft.api.item;

import io.github.dawncraft.api.block.BlockSkull;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Dawncraft's entity skull.
 *
 * @author QingChenW
 */
public class ItemSkull extends net.minecraft.item.ItemSkull
{
    public ISkullType[] skullTypes;
    private final BlockSkull block;

    public ItemSkull(BlockSkull block, ISkullType... types)
    {
        this.skullTypes = types;
        this.block = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public String getTranslationKey(ItemStack itemStack)
    {
        int i = itemStack.getMetadata();
        if (i < 0 || i >= this.skullTypes.length) i = 0;
        return super.getTranslationKey() + "." + this.skullTypes[i].getEntityName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < this.skullTypes.length; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity)
    {
        return armorType == EntityEquipmentSlot.HEAD;
    }

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt)
    {
        return false;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing == EnumFacing.DOWN)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (world.getBlockState(pos).getBlock().isReplaceable(world, pos))
            {
                facing = EnumFacing.UP;
                pos = pos.down();
            }

            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (!block.isReplaceable(world, pos))
            {
                if (!state.getMaterial().isSolid() && !world.isSideSolid(pos, facing, true))
                {
                    return EnumActionResult.FAIL;
                }
                pos = pos.offset(facing);
            }

            ItemStack itemStack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemStack) && this.block.canPlaceBlockAt(world, pos))
            {
                if (!world.isRemote)
                {
                    world.setBlockState(pos, this.block.getDefaultState().withProperty(net.minecraft.block.BlockSkull.FACING, facing), 11);

                    TileEntity tileentity = world.getTileEntity(pos);
                    if (tileentity instanceof TileEntitySkull)
                    {
                        TileEntitySkull tileentitySkull = (TileEntitySkull) tileentity;
                        tileentitySkull.setSkullType(itemStack.getMetadata());
                        int rotation = facing == EnumFacing.UP ? MathHelper.floor(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15 : 0;
                        tileentitySkull.setSkullRotation(rotation);
                        this.block.checkEntitySpawn(world, pos, tileentitySkull);
                    }

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemStack);
                    }

                    itemStack.shrink(1);
                }
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }
}
