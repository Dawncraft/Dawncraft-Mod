package io.github.dawncraft.item;

import io.github.dawncraft.common.CreativeTabsLoader;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Magnet ball.
 *  
 * @author QingChenW
 */
public class ItemMagnetBall extends Item
{
    public ItemMagnetBall()
    {
        super();
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if(!worldIn.isRemote)
        {
            BlockPos blockPos = new BlockPos(playerIn);
            
            if(blockPos != null)
            {
                //EntityMagnetBall entityMagnetBall = new EntityMagnetBall(worldIn, playerIn);// TODO 磁铁球
                EntityEnderEye entityMagnetBall = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);//Test
                entityMagnetBall.moveTowards(blockPos);
                worldIn.spawnEntityInWorld(entityMagnetBall);
                worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                if(!playerIn.capabilities.isCreativeMode)
                {
                    --itemStackIn.stackSize;
                }
            }
        }
        return itemStackIn;
    }
}
