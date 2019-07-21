package io.github.dawncraft.container;

import io.github.dawncraft.api.event.DawnEventFactory;
import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class SkillSlotLearning extends SkillSlot
{
    /** The player that is using the GUI where this slot resides. */
    private final EntityPlayer player;
    /** The learn matrix inventory linked to this result slot. */
    private final SkillInventoryLearning learnMatrix;

    public SkillSlotLearning(EntityPlayer player, SkillInventoryLearning learningInventory, ISkillInventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
        this.player = player;
        this.learnMatrix = learningInventory;
    }

    @Override
    public boolean isSkillValid(SkillStack stack)
    {
        return false;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, SkillStack stack)
    {
        DawnEventFactory.firePlayerCraftingEvent(player, stack, this.learnMatrix);
        this.onLearning(stack);

        for (int i = 0; i < this.learnMatrix.getSkillInventorySize(); ++i)
        {
            this.learnMatrix.setSkillInventorySlot(i, null);
        }

        ForgeHooks.setCraftingPlayer(player);
        ItemStack[] remainingItems = LearningManager.INSTANCE.getRemainingItems(this.learnMatrix, player.getEntityWorld());
        ForgeHooks.setCraftingPlayer(null);
        for (int i = 0; i < remainingItems.length; ++i)
        {
            ItemStack itemStack = this.learnMatrix.getStackInSlot(i);
            ItemStack itemStack2 = remainingItems[i];

            if (itemStack != null)
            {
                this.learnMatrix.decrStackSize(i, 1);
            }

            if (itemStack2 != null)
            {
                if (this.learnMatrix.getStackInSlot(i) == null)
                {
                    this.learnMatrix.setInventorySlotContents(i, itemStack2);
                }
                else if (!this.player.inventory.addItemStackToInventory(itemStack2))
                {
                    this.player.dropItem(itemStack2, false);
                }
            }
        }
    }

    @Override
    public void onLearning(SkillStack stack)
    {
        stack.onLearning(this.player.getEntityWorld(), this.player);
    }
}
