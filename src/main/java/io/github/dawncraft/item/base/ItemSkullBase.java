package io.github.dawncraft.item.base;

import java.util.List;

import io.github.dawncraft.block.base.BlockSkullBase;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ItemSkullBase 物品头颅基类
 * <br>想实现自己的头颅只需要实现ItemSkullBase和BlockSkullBase这两个基类</br>
 *
 * @author QingChenW
 */
public abstract class ItemSkullBase extends Item
{
    private String[] skullTypes;
    
    public ItemSkullBase(String[] types)
    {
        super();
        this.skullTypes = types;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    /**
     * getSkullBlock 返回头颅方块
     *
     * @return BlockSkullBase
     */
    public abstract BlockSkullBase getSkullBlock();

    public String[] getSkullTypes()
    {
        return this.skullTypes;
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        
        if (block.isReplaceable(worldIn, pos) && side != EnumFacing.DOWN)
        {
            side = EnumFacing.UP;
            pos = pos.down();
        }

        if (side == EnumFacing.DOWN)
        {
            return false;
        }
        else
        {
            block = worldIn.getBlockState(pos).getBlock();
            
            if (!block.isReplaceable(worldIn, pos))
            {
                if (!block.getMaterial().isSolid() && !worldIn.isSideSolid(pos, side, true))
                {
                    return false;
                }
                pos = pos.offset(side);
            }
            
            if (!playerIn.canPlayerEdit(pos, side, stack))
            {
                return false;
            }
            else if (!this.getSkullBlock().canPlaceBlockAt(worldIn, pos))
            {
                return false;
            }
            else
            {
                if (!worldIn.isRemote)
                {
                    if (!this.getSkullBlock().canPlaceBlockOnSide(worldIn, pos, side)) return false;
                    worldIn.setBlockState(pos, this.getSkullBlock().getDefaultState().withProperty(this.getSkullBlock().FACING, side), 3);
                    
                    TileEntity tileentity = worldIn.getTileEntity(pos);
                    if (tileentity instanceof TileEntitySkull)
                    {
                        TileEntitySkull tileentityskull = (TileEntitySkull) tileentity;
                        tileentityskull.setSkullType(stack.getMetadata());
                        int rotation = side == EnumFacing.UP ? MathHelper.floor_double(playerIn.rotationYaw * 16.0F / 360.0F + 0.5D) & 15 : 0;
                        tileentityskull.setSkullRotation(rotation);
                        this.getSkullBlock().checkSpecialSpawn(worldIn, pos, tileentityskull);
                    }
                    
                    --stack.stackSize;
                }
                return true;
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        if (i < 0 || i >= this.skullTypes.length) i = 0;
        return super.getUnlocalizedName() + "." + this.skullTypes[i];
    }
    
    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i = 0; i < this.skullTypes.length; i++)
        {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}
