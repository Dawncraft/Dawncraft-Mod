package io.github.dawncraft.tileentity;

import java.util.List;

import io.github.dawncraft.Dawncraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

/**
 * @author QingChenW
 **/
public class TileEntityMagnetChest extends TileEntityChest
{
    // @SideOnly(Side.CLIENT)
    public TileEntityMagnetChest() {}

    public boolean insertStackFromEntity(EntityItem entityItem)
    {
        if (entityItem == null || entityItem.isDead || entityItem.getEntityItem() == null) return false;
        else
        {
            ItemStack itemStack = entityItem.getEntityItem().copy();
            ItemStack newItemStack = this.insertStack(itemStack);

            if (newItemStack != null && newItemStack.stackSize > 0)
                entityItem.setEntityItemStack(newItemStack);
            else
            {
                entityItem.setDead();
                return true;
            }

            return false;
        }
    }

    public ItemStack insertStack(ItemStack itemStack)
    {
        int j = this.getSizeInventory();

        for (int k = 0; k < j && itemStack != null && itemStack.stackSize > 0; ++k)
            itemStack = this.tryInsertStackToSlot(itemStack, k);

        if (itemStack != null && itemStack.stackSize <= 0)
            itemStack = null;

        return itemStack;
    }
    
    public ItemStack tryInsertStackToSlot(ItemStack itemStack, int slot)
    {
        ItemStack slotStack = this.getStackInSlot(slot);
        
        if (this.isItemValidForSlot(slot, itemStack))
        {
            boolean changed = false;
            
            if (slotStack == null || slotStack.stackSize <= 0)
            {
                int max = Math.min(itemStack.getMaxStackSize(), this.getInventoryStackLimit());
                if (itemStack.stackSize <= max)
                {
                    this.setInventorySlotContents(slot, itemStack);
                    itemStack = null;
                }
                else
                    this.setInventorySlotContents(slot, itemStack.splitStack(max));
                changed = true;
            }
            else if (ItemStack.areItemsEqual(slotStack, itemStack) && ItemStack.areItemStackTagsEqual(slotStack, itemStack))
            {
                int max = Math.min(itemStack.getMaxStackSize(), this.getInventoryStackLimit());
                if (max > slotStack.stackSize)
                {
                    int l = Math.min(itemStack.stackSize, max - slotStack.stackSize);
                    itemStack.stackSize--;
                    slotStack.stackSize++;
                    changed = l > 0;
                }
            }
            
            if (changed) this.markDirty();
        }
        return itemStack;
    }

    @Override
    public void update()
    {
        super.update();
        
        this.pullItemsIn();
    }
    
    /**
     * @author ProPercivalalb
     */
    public void pullItemsIn()
    {
        List entity = this.worldObj.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos, this.pos).expand(2D, 1.0D, 2D));

        if (!entity.isEmpty())
        {
            for (int i = 0; i < entity.size(); i++)
            {
                Entity target = (Entity) entity.get(i);
                if (!(target instanceof EntityItem)) continue;

                EntityItem entityItem = (EntityItem) target;
                double centreX = (double) this.pos.getX() + 0.5D;
                double centreY = (double) this.pos.getY() + 0.5D;
                double centreZ = (double) this.pos.getZ() + 0.5D;
                double d7 = centreX <= entityItem.posX ? -(entityItem.posX - centreX) : centreX - entityItem.posX;
                double d8 = centreY <= entityItem.posY ? -(entityItem.posY - centreY) : centreY - entityItem.posY;
                double d9 = centreZ <= entityItem.posZ ? -(entityItem.posZ - centreZ) : centreZ - entityItem.posZ;
                double speedMultiper = 0.05D;
                double d11 = entityItem.posX - centreX;
                double d12 = entityItem.posZ - centreZ;
                double d13 = Math.sqrt(d7 * d7 + d9 * d9);
                double d14 = Math.asin(d7 / d13);
                double d15 = (double) MathHelper.sin((float)d14) * speedMultiper;
                double d16 = (double) MathHelper.cos((float)d14) * speedMultiper;
                d16 = d9 <= 0.0D ? -d16 : d16;

                if ((double) MathHelper.abs((float) (entityItem.motionX + entityItem.motionY + entityItem.motionZ)) >= 0.1D)
                    continue;

                if (d7 != 0.0D && (double) MathHelper.abs((float)entityItem.motionZ) < 0.1D)
                    entityItem.motionX = d15;

                if (d9 != 0.0D && (double) MathHelper.abs((float)entityItem.motionZ) < 0.1D)
                    entityItem.motionZ = d16;
            }
        }
    }

    @Override
    public String getGuiID()
    {
        return Dawncraft.MODID + ":" + "magnet_chest";
    }
}
