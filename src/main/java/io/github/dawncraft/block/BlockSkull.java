package io.github.dawncraft.block;

import java.util.Random;

import io.github.dawncraft.item.ItemSkull;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Dawncraft's entity skull
 *
 * @author QingChenW
 */
public abstract class BlockSkull extends net.minecraft.block.BlockSkull
{
    public BlockSkull()
    {
        super();
    }

    /**
     * Return skull item.
     *
     * @return ItemSkull
     */
    public abstract ItemSkull getSkullItem();

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySkull();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.getSkullItem();
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        int meta = 0;
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntitySkull)
        {
            meta = ((TileEntitySkull) tileentity).getSkullType();
        }
        return new ItemStack(this.getSkullItem(), 1, meta);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if (!state.getValue(NODROP).booleanValue())
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileEntitySkull)
            {
                int meta = ((TileEntitySkull) tileentity).getSkullType();
                drops.add(new ItemStack(this.getSkullItem(), 1, meta));
            }
        }
    }

    @Override
    public boolean canDispenserPlace(World world, BlockPos pos, ItemStack itemStack)
    {
        return false;
    }

    @Override
    protected BlockPattern getWitherBasePattern()
    {
        return this.getEntityBasePattern();
    }

    @Override
    protected BlockPattern getWitherPattern()
    {
        return this.getEntityPattern();
    }

    @Override
    public void checkWitherSpawn(World world, BlockPos pos, net.minecraft.tileentity.TileEntitySkull tileentitySkull) {}

    public BlockPattern getEntityBasePattern()
    {
        return null;
    }

    public BlockPattern getEntityPattern()
    {
        return null;
    }

    /**
     * Check the generation of bosses like the wither.
     *
     * @param world
     * @param pos
     * @param tileentityskull
     */
    public void checkEntitySpawn(World world, BlockPos pos, TileEntitySkull tileentityskull) {}
}
