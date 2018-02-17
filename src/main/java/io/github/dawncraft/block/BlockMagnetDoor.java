package io.github.dawncraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMagnetDoor extends BlockDoor implements ITileEntityProvider
{
    public BlockMagnetDoor()
    {
        super(Material.iron);
        this.isBlockContainer = true;
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setHarvestLevel("ItemPickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            ItemStack itemStack = player.getHeldItem();
            if(itemStack != null && itemStack.getItem() == ItemLoader.magnetCard && itemStack.hasTagCompound())
            {
                BlockPos blockPos2 = blockState.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? blockPos : blockPos.down();
                IBlockState blockState2 = blockPos.equals(blockPos2) ? blockState : world.getBlockState(blockPos2);
                if (blockState2.getBlock() == this)
                {
                    TileEntity tileentity = world.getTileEntity(blockPos2);
                    if (tileentity instanceof TileEntityMagnetDoor)
                    {
                        TileEntityMagnetDoor tileentitydoor = (TileEntityMagnetDoor) tileentity;
                        NBTTagCompound nbt = itemStack.getTagCompound();
                        if(tileentitydoor.getUUID().equals(nbt.getString("UUID")))
                        {
                            blockState = blockState2.cycleProperty(OPEN);
                            world.setBlockState(blockPos2, blockState, 2);
                            world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
                            world.playAuxSFXAtEntity(player, blockState.getValue(OPEN).booleanValue() ? 1003 : 1006, blockPos2, 0);
                            return true;
                        }
                    }
                }
            }
            ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", this.getLocalizedName()), (byte) 2));
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = world.getBlockState(blockpos);
            
            if (iblockstate.getBlock() != this)
            {
                world.setBlockToAir(pos);
            }
            else if (neighborBlock != this)
            {
                this.onNeighborBlockChange(world, blockpos, iblockstate, neighborBlock);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = world.getBlockState(blockpos1);
            
            if (iblockstate1.getBlock() != this)
            {
                world.setBlockToAir(pos);
                flag1 = true;
            }
            
            if (!World.doesBlockHaveSolidTopSurface(world, pos.down()))
            {
                world.setBlockToAir(pos);
                flag1 = true;
                
                if (iblockstate1.getBlock() == this)
                {
                    world.setBlockToAir(blockpos1);
                }
            }
            
            if (!world.isRemote && flag1)
            {
                this.dropBlockAsItem(world, pos, state, 0);
            }
        }
    }
    
    @Override
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMagnetDoor();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER;
    }
    
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        return null;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        Item item = this.getItemDropped(state, rand, fortune);
        if(item != null)
        {
            ItemStack itemStack = new ItemStack(item);
            if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
                pos = pos.down();
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityMagnetDoor)
            {
                TileEntityMagnetDoor tileentitydoor = (TileEntityMagnetDoor) tileentity;
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("Owner", tileentitydoor.getName());
                nbt.setString("UUID", tileentitydoor.getUUID());
                itemStack.setTagCompound(nbt);
            }
            ret.add(itemStack);
        }
        return ret;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : ItemLoader.magnetDoor;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return ItemLoader.magnetDoor;
    }
}
