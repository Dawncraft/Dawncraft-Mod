package io.github.dawncraft.block;

import java.util.Random;

import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.item.ItemSkullDawn;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Dawncraft's entity skull
 *
 * @author QingChenW
 */
public class BlockSkullDawn extends BlockSkull
{
    public BlockSkullDawn()
    {
        super();
    }
    
    /**
     * Return skull item.
     *
     * @return ItemSkullBase
     */
    public ItemSkullDawn getSkullItem()
    {
        return (ItemSkullDawn) ItemLoader.skull;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySkull();
    }
    
    @Override
    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();

        if (!state.getValue(NODROP).booleanValue())
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileEntitySkull)
            {
                TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
                ret.add(new ItemStack(this.getSkullItem(), 1, tileentityskull.getSkullType()));
            }
        }

        return ret;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.getSkullItem();
    }

    @Override
    public int getDamageValue(World world, BlockPos pos)
    {
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity instanceof TileEntitySkull ? ((TileEntitySkull) tileentity).getSkullType() : super.getDamageValue(world, pos);
    }

    @Override
    public boolean canDispenserPlace(World world, BlockPos pos, ItemStack itemStack)
    {
        return false;
    }
    
    @Override
    protected BlockPattern getWitherBasePattern()
    {
        return null;
    }
    
    @Override
    protected BlockPattern getWitherPattern()
    {
        return null;
    }
    
    @Override
    public void checkWitherSpawn(World world, BlockPos pos, net.minecraft.tileentity.TileEntitySkull tileentityskull) {}
    
    protected BlockPattern getEntityBasePattern()
    {
        return null;
    }
    
    protected BlockPattern getEntityPattern()
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

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos)
    {
        return this.getSkullItem();
    }
}
