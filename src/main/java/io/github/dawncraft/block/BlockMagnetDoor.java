package io.github.dawncraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Magnet door
 *
 * @author QingChenW
 */
public class BlockMagnetDoor extends BlockDoor implements ITileEntityProvider
{
    public BlockMagnetDoor()
    {
        super(Material.iron);
        this.isBlockContainer = true;
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMagnetDoor();
    }
    
    @Override
    public boolean hasTileEntity(IBlockState blockState)
    {
        return blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
            boolean isUpper = blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER;
            BlockPos blockPos2 = isUpper ? blockPos.down() : blockPos;
            IBlockState blockState2 = isUpper ? world.getBlockState(blockPos2) : blockState;
            if (blockState2.getBlock() == this)
            {
                TileEntity tileentity = world.getTileEntity(blockPos2);
                if (tileentity instanceof TileEntityMagnetDoor)
                {
                    TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                    boolean canUnlock = !tileentityMagnetDoor.isLocked();
                    ItemStack itemStack = player.getHeldItem();
                    if (itemStack != null && itemStack.getItem() == ItemLoader.magnetCard && itemStack.hasTagCompound())
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
                                    playerMagic.sendOverlayMessage(new ChatComponentTranslation("container.locked", this.getLocalizedName()));
                                    return true;
                                }
                            }
                            else
                            {
                                if (UUID.equals(tileentityMagnetDoor.getUUID()))
                                {
                                    canUnlock = true;
                                }
                            }
                        }
                    }
                    if (canUnlock)
                    {
                        blockState = blockState2.cycleProperty(OPEN);
                        world.setBlockState(blockPos2, blockState, 2);
                        world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
                        world.playAuxSFXAtEntity(player, blockState.getValue(OPEN).booleanValue() ? 1003 : 1006, blockPos2, 0);
                        return true;
                    }
                    playerMagic.sendOverlayMessage(new ChatComponentTranslation("container.isLocked", this.getLocalizedName()));
                }
            }
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState blockState, Block neighborBlock)
    {
        if (blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos blockPos2 = blockPos.down();
            IBlockState blockState2 = world.getBlockState(blockPos2);

            if (blockState2.getBlock() != this)
            {
                world.setBlockToAir(blockPos);
            }
            else if (neighborBlock != this)
            {
                this.onNeighborBlockChange(world, blockPos2, blockState2, neighborBlock);
            }
        }
        else
        {
            boolean removed = false;
            BlockPos blockpos2 = blockPos.up();
            IBlockState blockState2 = world.getBlockState(blockpos2);

            if (blockState2.getBlock() != this)
            {
                world.setBlockToAir(blockPos);
                removed = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()))
            {
                world.setBlockToAir(blockPos);
                removed = true;

                if (blockState2.getBlock() == this)
                {
                    world.setBlockToAir(blockpos2);
                }
            }

            if (!world.isRemote && removed)
            {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
            }
        }
    }

    @Override
    public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState blockState, int eventID, int eventParam)
    {
        super.onBlockEventReceived(world, blockPos, blockState, eventID, eventParam);
        TileEntity tileentity = world.getTileEntity(blockPos);
        return tileentity != null ? tileentity.receiveClientEvent(eventID, eventParam) : false;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos blockPos, IBlockState blockState, int fortune)
    {
        List<ItemStack> ret = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(blockState, rand, fortune);
        if (item != null)
        {
            ItemStack itemStack = new ItemStack(item);
            if (blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) blockPos = blockPos.down();
            TileEntity tileentity = world.getTileEntity(blockPos);
            if (tileentity instanceof TileEntityMagnetDoor)
            {
                TileEntityMagnetDoor tileentityMagnetDoor = (TileEntityMagnetDoor) tileentity;
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("UUID", tileentityMagnetDoor.getUUID());
                itemStack.setTagCompound(nbt);
            }
            ret.add(itemStack);
        }
        return ret;
    }

    @Override
    public Item getItemDropped(IBlockState blockState, Random rand, int fortune)
    {
        return blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? ItemLoader.magnetDoor : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos)
    {
        return ItemLoader.magnetDoor;
    }
}
