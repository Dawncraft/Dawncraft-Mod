package io.github.dawncraft.container;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IWorldNameable;

public interface ISkillInventory extends IWorldNameable
{
    /**
     * Returns the number of slots in the inventory.
     */
    int getSkillInventorySize();

    /**
     * Sets the given skill stack to the specified slot in the inventory (can be learning sections).
     */
    void setSkillInventorySlot(int index, SkillStack skillStack);
    
    /**
     * Returns the stack in the given slot.
     */
    SkillStack getSkillStackInSlot(int index);

    /**
     * Removes a stack from the given slot and returns it.
     */
    SkillStack removeSkillStackFromSlot(int index);
    
    /**
     * Clear all stacks in the inventory
     */
    void clearSkillStacks();

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    boolean isUseableByPlayer(EntityPlayer player);
    
    void openInventory(EntityPlayer player);
    
    void closeInventory(EntityPlayer player);

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    void markDirty();
}
