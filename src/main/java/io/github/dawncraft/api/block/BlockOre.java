package io.github.dawncraft.api.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * The ore block, you can make your own ore by extending it.
 * <br>
 * harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
 * dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture)
 * dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float 1.0F, int fortune)
 * getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
 *</br>
 * @author QingChenW
 */
public class BlockOre extends Block
{
    private int droppedCount;
    private int droppedRange;
    private int droppedExpMin;
    private int droppedExpMax;

    public BlockOre()
    {
        this(Material.rock.getMaterialMapColor());
    }
    
    public BlockOre(MapColor color)
    {
        this(color, 1, 0);
    }
    
    public BlockOre(int count, int range)
    {
        this(Material.rock.getMaterialMapColor(), count, range);
    }
    
    public BlockOre(MapColor color, int count, int range)
    {
        super(Material.rock, color);
        this.droppedCount = count;
        this.droppedRange = range;
        this.droppedExpMin = 0;
        this.droppedExpMax = 0;
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setHarvestLevel(1);
    }

    public BlockOre setDroppedExp(int expMin, int expMax)
    {
        this.droppedExpMin = expMin;
        this.droppedExpMax = expMax;
        return this;
    }
    
    public BlockOre setHarvestLevel(int level)
    {
        this.setHarvestLevel("pickaxe", level);
        return this;
    }


    public Item getMineral()
    {
        return Item.getItemFromBlock(this);
    }
    
    public boolean dropItself(IBlockState state, Random rand, int fortune)
    {
        return this.getMineral() == Item.getItemFromBlock(this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.dropItself(state, rand, fortune) ? Item.getItemFromBlock(this) : this.getMineral();
    }
    
    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random rand)
    {
        if (!this.dropItself(state, rand, fortune))
        {
            if(fortune > 0)
            {
                return this.quantityDroppedWithBonus(fortune, rand);
            }
            else
            {
                return this.quantityDropped(rand) + rand.nextInt(this.droppedRange);
            }
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        int count = random.nextInt(fortune);
        if (count < 0) count = 0;
        return this.quantityDropped(random) + count;
    }
    
    @Override
    public int quantityDropped(Random random)
    {
        return this.droppedCount;
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
    {
        if(this.droppedExpMax > 0)
        {
            IBlockState state = world.getBlockState(pos);
            Random rand = world instanceof World ? ((World)world).rand : new Random();
            if (this.dropItself(state, rand, fortune)) return 0;
            return MathHelper.getRandomIntegerInRange(rand, this.droppedExpMin, this.droppedExpMax);
        }
        return 0;
    }
}
