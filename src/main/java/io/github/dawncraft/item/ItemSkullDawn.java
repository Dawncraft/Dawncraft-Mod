package io.github.dawncraft.item;

import java.util.List;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.block.BlockSkullDawn;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Dawncraft's entity skull.
 *
 * @author QingChenW
 */
public class ItemSkullDawn extends ItemSkull
{
    public static final Class<? extends Entity>[] skullTypes = new Class[] {EntitySavage.class, EntityBarbarianKing.class, EntityGerKing.class};
    
    public ItemSkullDawn()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    /**
     * Return skull block.
     *
     * @return BlockSkullBase
     */
    public BlockSkullDawn getSkullBlock()
    {
        return (BlockSkullDawn) BlockLoader.skull;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int i = itemStack.getMetadata();
        if (i < 0 || i >= skullTypes.length) i = 0;
        return super.getUnlocalizedName() + "." + EntityUtils.getEntityStringFromClass(skullTypes[i]).toLowerCase();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i = 0; i < this.skullTypes.length; i++)
        {
            subItems.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public boolean isValidArmor(ItemStack itemStack, int armorType, Entity entity)
    {
        if (armorType == 0)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt)
    {
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlockState(pos).getBlock();
        
        if (block.isReplaceable(world, pos) && side != EnumFacing.DOWN)
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
            block = world.getBlockState(pos).getBlock();
            
            if (!block.isReplaceable(world, pos))
            {
                if (!block.getMaterial().isSolid() && !world.isSideSolid(pos, side, true))
                {
                    return false;
                }
                pos = pos.offset(side);
            }
            
            if (!player.canPlayerEdit(pos, side, itemStack))
            {
                return false;
            }
            else if (!this.getSkullBlock().canPlaceBlockAt(world, pos))
            {
                return false;
            }
            else
            {
                if (!world.isRemote)
                {
                    if (!this.getSkullBlock().canPlaceBlockOnSide(world, pos, side)) return false;
                    world.setBlockState(pos, this.getSkullBlock().getDefaultState().withProperty(this.getSkullBlock().FACING, side), 3);
                    
                    TileEntity tileentity = world.getTileEntity(pos);
                    if (tileentity instanceof TileEntitySkull)
                    {
                        TileEntitySkull tileentityskull = (TileEntitySkull) tileentity;
                        tileentityskull.setSkullType(itemStack.getMetadata());
                        int rotation = side == EnumFacing.UP ? MathHelper.floor_double(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15 : 0;
                        tileentityskull.setSkullRotation(rotation);
                        this.getSkullBlock().checkEntitySpawn(world, pos, tileentityskull);
                    }
                    
                    --itemStack.stackSize;
                }
                return true;
            }
        }
    }
}
