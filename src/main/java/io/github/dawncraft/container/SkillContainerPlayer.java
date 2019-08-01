package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;

public class SkillContainerPlayer extends SkillContainer
{
    private final EntityPlayer player;
    /** Determines if inventory manipulation should be handled. */
    public boolean isLocalWorld;
    /** The learning matrix inventory. */
    public SkillInventoryLearning learnMatrix = new SkillInventoryLearning(this, 0, 2);
    public SkillInventoryLearnResult learnResult = new SkillInventoryLearnResult();

    public SkillContainerPlayer(final SkillInventoryPlayer playerInventory, boolean localWorld)
    {
        this.player = playerInventory.player;
        this.isLocalWorld = localWorld;

        // 9格快捷栏
        for (int i = 0; i < 9; ++i)
        {
            this.addSkillSlotToContainer(new SkillSlot(playerInventory, i, 8 + i * 18, 142));
        }
        
        // 27格背包
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSkillSlotToContainer(new SkillSlot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        // 2+1格技能学习
        this.addSkillSlotToContainer(new SkillSlot(this.learnMatrix, 0, 69, 26));
        this.addSkillSlotToContainer(new SkillSlot(this.learnMatrix, 1, 104, 26));
        this.addSkillSlotToContainer(new SkillSlotLearning(playerInventory.player, this.learnMatrix, this.learnResult, 0, 148, 26));
        
        this.onCraftMatrixChanged(this.learnMatrix);
        this.onLearnMatrixChanged(this.learnMatrix);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        this.learnResult.setSkillInventorySlot(0, LearningManager.INSTANCE.findMatchedRecipe(this.learnMatrix, this.player.getEntityWorld()));
    }

    @Override
    public void onLearnMatrixChanged(ISkillInventory skillInventory)
    {
        this.learnResult.setSkillInventorySlot(0, LearningManager.INSTANCE.findMatchedRecipe(this.learnMatrix, this.player.getEntityWorld()));
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        for (int i = 0; i < this.learnMatrix.getSkillInventorySize(); ++i)
        {
            SkillStack skillStack = this.learnMatrix.removeSkillStackFromSlot(i);
            
            if (skillStack != null)
            {
                IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
                playerMagic.getSkillInventory().addSkillStackToInventory(skillStack);
            }
        }

        this.learnResult.setSkillInventorySlot(0, null);
    }

    @Override
    public SkillStack transferSkillStackInSlot(EntityPlayer player, int index)
    {
        SkillStack skillStack = null;
        SkillSlot slot = this.inventorySkillSlots.get(index);

        if (slot != null && slot.hasStack())
        {
            skillStack = slot.getStack();
            
            if (index >= 0 && index < 9)
            {
                if (!this.mergeSkillStack(skillStack, 9, 36, false))
                {
                    return null;
                }
            }
            else if (index >= 9 && index < 36)
            {
                if (!this.mergeSkillStack(skillStack, 0, 9, false))
                {
                    return null;
                }
            }
            else if (index >= 36 && index < 38)
            {
                if (!this.mergeSkillStack(skillStack, 0, 36, false))
                {
                    return null;
                }
            }
            else if (index == 38)
            {
                if (!this.mergeSkillStack(skillStack, 0, 36, false))
                {
                    return null;
                }
            }
            
            slot.removeStack();
            slot.onPickupFromSlot(player, skillStack);
        }
        
        return skillStack;
    }
}
