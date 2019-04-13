package io.github.dawncraft.container;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public SlotSkill(ISkillInventory inventory, int index, int xPosition, int yPosition)
    {
        this.inventory = inventory;
        this.slotIndex = index;
        this.xDisplayPosition = xPosition;
        this.yDisplayPosition = yPosition;
    }
    
    public int getSlotIndex()
    {
        return this.slotIndex;
    }

    public boolean hasStack()
    {
        return this.getStack() != null;
    }
    
    public SkillStack getStack()
    {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    public boolean isHere(ISkillInventory inventory, int slot)
    {
        return inventory == this.inventory && slot == this.slotIndex;
    }

    public boolean isSkillValid(SkillStack stack)
    {
        return true;
    }
    
    public void putStack(SkillStack stack)
    {
        this.inventory.setInventorySlot(this.slotIndex, stack);
        this.onSlotChanged();
    }
    
    public boolean canTakeStack(EntityPlayer player)
    {
        return true;
    }

    public void onPickupFromSlot(EntityPlayer player, SkillStack stack)
    {
        this.onSlotChanged();
    }
    
    public void onSlotChanged()
    {
        this.inventory.markDirty();
    }

    public void onLearning(SkillStack stack)
    {
    }
    
    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return null;
    }
}
