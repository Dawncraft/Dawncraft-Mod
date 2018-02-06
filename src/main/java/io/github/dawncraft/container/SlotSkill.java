package io.github.dawncraft.container;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;

public class SlotSkill
{
    /** The index of the slot in the inventory. */
    private final int slotIndex;
    /** The inventory we want to extract a slot from. */
    public final ISkillInventory inventory;
    /** the id of the slot(also the index in the inventory arraylist) */
    public int slotNumber;
    /** display position of the inventory slot on the screen x axis */
    public int xDisplayPosition;
    /** display position of the inventory slot on the screen y axis */
    public int yDisplayPosition;

    public SlotSkill(ISkillInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        this.inventory = inventoryIn;
        this.slotIndex = index;
        this.xDisplayPosition = xPosition;
        this.yDisplayPosition = yPosition;
    }
    
    public boolean isSkillValid(SkillStack stack)
    {
        return true;
    }

    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return true;
    }
    
    public void putStack(SkillStack stack)
    {
        this.inventory.setInventorySlotContents(this.slotIndex, stack);
        this.onSlotChanged();
    }
    
    public void onPickupFromSlot(EntityPlayer playerIn, SkillStack stack)
    {
        this.onSlotChanged();
    }
    
    public int getSlotIndex()
    {
        return this.slotIndex;
    }
    
    public boolean getHasStack()
    {
        return this.getStack() != null;
    }
    
    public SkillStack getStack()
    {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public boolean isHere(ISkillInventory inv, int slot)
    {
        return inv == this.inventory && slot == this.slotIndex;
    }

    public void onSlotChanged()
    {
        this.inventory.markDirty();
    }
}
