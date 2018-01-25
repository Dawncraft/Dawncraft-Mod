package io.github.dawncraft.block;

import java.util.List;
import io.github.dawncraft.block.base.BlockMachineBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author QingChenW
 */
public class BlockComputerCase extends BlockMachineBase
{
    public BlockComputerCase.ComputerCaseType type;

    public BlockComputerCase(ComputerCaseType caseType)
    {
        super();
        this.type = caseType;
        this.setHarvestLevel("ItemPickaxe", this.type.getTool());
        this.setStepSound(soundTypePiston);
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
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH
                || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH)
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        else if (worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.WEST
                || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.EAST)
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        // TODO 电脑机箱的te,别忘了233
        return null;
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
    {
        player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        player.addExhaustion(0.025F);

        List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        Item item = this.getItemDropped(state, null, 0);
        if (item != null)
        {
            ItemStack itemStack = new ItemStack(item);
            if (te == te)
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

    public enum ComputerCaseType
    {
        SIMPLE(0, 1), ADVANCED(1, 2), SUPER(2, 2);

        private int _id;
        private int _tool;

        ComputerCaseType(int id, int tool)
        {
            this._id = id;
            this._tool = tool;
        }

        public int getId()
        {
            return this._id;
        }

        public int getTool()
        {
            return this._tool;
        }
    }
}
