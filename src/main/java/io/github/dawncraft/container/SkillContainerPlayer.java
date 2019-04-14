package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;

public class SkillContainerPlayer extends SkillContainer
{
    public boolean isLocalWorld;
    private final EntityPlayer player;

    public SkillContainerPlayer(final SkillInventoryPlayer playerInventory, boolean localWorld, EntityPlayer player)
    {
        this.isLocalWorld = localWorld;
        this.player = player;

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
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
    }

    @Override
    public SkillStack transferSkillStackInSlot(EntityPlayer player, int index)
    {
        return super.transferSkillStackInSlot(player, index);
    }
}
