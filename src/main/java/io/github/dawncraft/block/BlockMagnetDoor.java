package io.github.dawncraft.block;

import java.util.Random;

import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Magnet door
 *
 * @author QingChenW
 */
public class BlockMagnetDoor extends BlockDoor implements ITileEntityProvider
{
    public BlockMagnetDoor()
    {
        super(Material.IRON);
        this.hasTileEntity = true;
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.IRON;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return super.hasTileEntity(state) && state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMagnetDoor();
    }

    @Override
    public Item getItem()
    {
        return ItemInit.MAGNET_DOOR;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos)
    {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos pos2 = pos.down();
            IBlockState state2 = world.getBlockState(pos2);

            if (state2.getBlock() != this)
            {
                world.setBlockToAir(pos);
            }
            else if (neighborBlock != this)
            {
                state2.neighborChanged(world, pos2, neighborBlock, fromPos);
            }
        }
        else
        {
            boolean removed = false;
            BlockPos pos2 = pos.up();
            IBlockState state2 = world.getBlockState(pos2);

            if (state2.getBlock() != this)
            {
                world.setBlockToAir(pos);
                removed = true;
            }

            if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP))
            {
                world.setBlockToAir(pos);
                removed = true;

                if (state2.getBlock() == this)
                {
                    world.setBlockToAir(pos2);
                }
            }

            if (!world.isRemote && removed)
            {
                this.dropBlockAsItem(world, pos, state, 0);
            }
        }
    }

    // TODO 重制
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            BlockPos newPos = state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? pos.down() : pos;
            IBlockState newState = world.getBlockState(newPos);
            if (newState.getBlock() == this)
            {
                boolean canUnlock = true;
                TileEntity tileentity = world.getTileEntity(newPos);
                if (tileentity instanceof TileEntityMagnetDoor)
                {
                    TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                    ItemStack itemStack = player.getHeldItem(hand);
                    if (itemStack != null && itemStack.getItem() == ItemInit.MAGNET_CARD && itemStack.hasTagCompound())
                    {
                        NBTTagCompound nbt = itemStack.getTagCompound();
                        String UUID = nbt.getString("UUID");
                        if (!StringUtils.isNullOrEmpty(UUID))
                        {
                            if (player.isSneaking())
                            {
                                if (!tileentityMagnetDoor.isLocked())
                                {
                                    tileentityMagnetDoor.setUUID(UUID);
                                    player.sendStatusMessage(new TextComponentTranslation("container.lock", this.getLocalizedName()), true);
                                    return true;
                                }
                            }
                            else
                            {
                                canUnlock = UUID.equals(tileentityMagnetDoor.getUUID());
                            }
                        }
                    }
                }
                if (canUnlock)
                {
                    state = newState.cycleProperty(OPEN);
                    world.setBlockState(newPos, state, 2);
                    world.markBlockRangeForRenderUpdate(newPos, pos);
                    world.playEvent(player, state.getValue(OPEN).booleanValue() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
                    return true;
                }
                player.sendStatusMessage(new TextComponentTranslation("container.isLocked", this.getLocalizedName()), true);
            }
        }
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState blockState, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(blockState, rand, fortune);
        if (item != null)
        {
            ItemStack itemStack = new ItemStack(item);
            if (blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) pos = pos.down();
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityMagnetDoor)
            {
                TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("UUID", tileentityMagnetDoor.getUUID());
                itemStack.setTagCompound(nbt);
            }
            drops.add(itemStack);
        }
    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null ? tileentity.receiveClientEvent(id, param) : false;
    }
}
