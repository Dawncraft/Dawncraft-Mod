package io.github.dawncraft.block;

import java.util.List;

import io.github.dawncraft.api.block.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The computer case.
 *
 * @author QingChenW
 */
public class BlockComputerCase extends BlockMachine
{
    public EnumCaseType type;
    
    public BlockComputerCase(EnumCaseType caseType)
    {
        super();
        this.type = caseType;
        this.setStepSound(soundTypePiston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false));
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
    {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);

        if (facing.getAxis() == Axis.Z)
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        else if (facing.getAxis() == Axis.X)
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = world.getTileEntity(pos);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tileentity = world.getTileEntity(pos);
            
        }
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileentity)
    {
        player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        player.addExhaustion(0.025F);
        
        List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        Item item = this.getItemDropped(state, null, 0);
        if (item != null)
        {
            ItemStack itemStack = new ItemStack(item);
            if (true)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("Owner", player.getName());
                itemStack.setTagCompound(nbt);
            }
            items.add(new ItemStack(item, 1, this.damageDropped(state)));
        }
        
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, world, pos, world.getBlockState(pos), 0, 1.0f, true, player);
        for (ItemStack stack : items)
        {
            spawnAsEntity(world, pos, stack);
        }
    }
    
    public enum EnumCaseType
    {
        SIMPLE, ADVANCED, PROFESSIONAL, CUSTOM;
    }
}
