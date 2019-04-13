/**
 *
 */
package io.github.dawncraft.container;

import java.util.List;

import io.github.dawncraft.skill.SkillStack;

/**
 * Players' capability to update their inventories between server and client.
 * Like {@link net.minecraft.inventory.ICrafting}
 *
 * @author QingChenW
 */
public interface ILearning
{
    /**
     * update the learning window inventory with the skill in the list
     */
    void updateLearningInventory(ContainerSkill containerToSend, List<SkillStack> skillsList);
    
    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    void sendSlotContents(ContainerSkill containerToSend, int slotId, SkillStack stack);
}
