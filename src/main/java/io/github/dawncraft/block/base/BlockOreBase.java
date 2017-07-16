package io.github.dawncraft.block.base;

import java.util.Random;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Ore block.
 * <br>Magnet Ore and others.</br>
 * 
 * @author QingChenW
 */
public class BlockOreBase extends Block
{
	private Item droppedItem;
	private int droppedCount;
	private int droppedRange;
	private int droppedBonus;
	private int droppedExpMin;
	private int droppedExpMax;
	
	public BlockOreBase()
	{
		this(Material.rock.getMaterialMapColor());
	}
	
	public BlockOreBase(MapColor color)
	{
		this(color, null, 0, 0, 0, 0, 0);
		this.setItemDropped(Item.getItemFromBlock(this));
		// TODO bug:矿石不掉落
	}

	public BlockOreBase(Item item, int count, int range, int bonus, int expMin, int expMax)
	{
		this(Material.rock.getMaterialMapColor(), item, count, range, bonus, expMax, expMax);
	}

    public BlockOreBase(MapColor color, Item item, int count, int range, int bonus, int expMin, int expMax)
    {
        super(Material.rock, color);
		this.droppedItem = item;
		this.droppedCount = count;
		this.droppedRange = range;
		this.droppedBonus = bonus;
		this.droppedExpMin = expMin;
		this.droppedExpMax = expMax;
        this.setHardness(3.0f);
        this.setResistance(5.0f);
    }
    
    public Block setItemDropped(Item item)
    {
    	this.droppedItem = item;
		return this;
	}
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.droppedItem;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return this.droppedItem != Item.getItemFromBlock(this) ? this.droppedCount + random.nextInt(this.droppedRange) : 1;
    }
    
    /** 这是比较简单的，如果你需要的话就用匿名内部类重写此方法
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.droppedItem != Item.getItemFromBlock(this) ? this.quantityDropped(random) + random.nextInt(fortune * this.droppedBonus) : this.quantityDropped(random);
    }

    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        	return MathHelper.getRandomIntegerInRange(rand, droppedExpMin, droppedExpMax);
        return 0;
    }
}
