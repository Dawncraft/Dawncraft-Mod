package io.github.dawncraft.container;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class SkillContainer extends Container
{
    // the slots in player's skill inventory
    public List<SkillSlot> inventorySkillSlots = Lists.<SkillSlot>newArrayList();
    // previous skill stack in player's skill slots, which are used to sync
    public List<SkillStack> inventorySkillStacks = Lists.<SkillStack>newArrayList();
    protected List<ILearning> learners = Lists.<ILearning>newArrayList();

    public SkillSlot getSkillSlotFromInventory(ISkillInventory inventory, int slotId)
    {
        for (int i = 0; i < this.inventorySkillSlots.size(); ++i)
        {
            SkillSlot slot = this.inventorySkillSlots.get(i);

            if (slot.isHere(inventory, slotId))
            {
                return slot;
            }
        }
        return null;
    }

    protected SkillSlot addSkillSlotToContainer(SkillSlot slot)
    {
        slot.slotNumber = this.inventorySkillSlots.size();
        this.inventorySkillSlots.add(slot);
        this.inventorySkillStacks.add(null);
        return slot;
    }

    public SkillSlot getSkillSlot(int slotId)
    {
        return this.inventorySkillSlots.get(slotId);
    }

    public List<SkillStack> getSkillStacks()
    {
        List<SkillStack> list = Lists.<SkillStack>newArrayList();

        for (int i = 0; i < this.inventorySkillSlots.size(); ++i)
        {
            list.add(this.inventorySkillSlots.get(i).getStack());
        }

        return list;
    }

    public void putSkillStackInSlot(int slotId, SkillStack stack)
    {
        this.getSkillSlot(slotId).putStack(stack);
    }

    @SideOnly(Side.CLIENT)
    public void putSkillStacksInSlots(SkillStack[] skillStacks)
    {
        for (int i = 0; i < skillStacks.length; ++i)
        {
            this.getSkillSlot(i).putStack(skillStacks[i]);
        }
    }

    public SkillStack skillSlotClick(int slotId, int clickedButton, int mode, EntityPlayer player)
    {
        SkillInventoryPlayer inventoryPlayer = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null).getSkillInventory();
        SkillStack skillStack = null;

        if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1))
        {
            if (slotId < 0)
            {
                return null;
            }

            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot != null)
            {
                if (mode == 0)
                {
                    // 模式0: 左/右键拾起
                    SkillStack skillStack2 = slot.getStack();
                    SkillStack skillStack3 = inventoryPlayer.getSkillStack();

                    if (skillStack2 != null)
                    {
                        skillStack = skillStack2.copy();
                    }

                    if (skillStack2 == null)
                    {
                        if (skillStack3 != null && slot.isSkillValid(skillStack3))
                        {
                            slot.putStack(skillStack3);
                            inventoryPlayer.setSkillStack(null);
                        }
                    }
                    else if (slot.canTakeStack(player))
                    {
                        if (skillStack3 == null)
                        {
                            inventoryPlayer.setSkillStack(slot.removeStack());
                            slot.onPickupFromSlot(player, inventoryPlayer.getSkillStack());
                        }
                        else if (slot.isSkillValid(skillStack3))
                        {
                            slot.putStack(skillStack3);
                            inventoryPlayer.setSkillStack(skillStack2);
                        }
                    }

                    slot.onSlotChanged();
                }
                else
                {
                    // 模式1: Shift键直接移动
                    if (slot.canTakeStack(player))
                    {
                        this.transferSkillStackInSlot(player, slotId);
                    }
                }
            }
        }
        else if (mode == 2 && clickedButton >= 0 && clickedButton < 9)
        {
            // 模式2: 键盘数字键移动
            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot.canTakeStack(player))
            {
                SkillStack skillStack2 = inventoryPlayer.getSkillStackInSlot(clickedButton);
                boolean flag = skillStack2 == null || slot.inventory == inventoryPlayer && slot.isSkillValid(skillStack2);
                int k1 = -1;

                if (!flag)
                {
                    k1 = inventoryPlayer.getFirstEmptyStack();
                    flag |= k1 > -1;
                }

                if (slot.hasStack() && flag)
                {
                    SkillStack itemstack3 = slot.getStack();
                    inventoryPlayer.setSkillInventorySlot(clickedButton, itemstack3.copy());

                    if ((slot.inventory != inventoryPlayer || !slot.isSkillValid(skillStack2)) && skillStack2 != null)
                    {
                        if (k1 > -1)
                        {
                            inventoryPlayer.addSkillStackToInventory(skillStack2);
                            slot.removeStack();
                            slot.onPickupFromSlot(player, itemstack3);
                        }
                    }
                    else
                    {
                        slot.putStack(skillStack2);
                        slot.onPickupFromSlot(player, itemstack3);
                    }
                }
                else if (!slot.hasStack() && skillStack2 != null && slot.isSkillValid(skillStack2))
                {
                    inventoryPlayer.setSkillInventorySlot(clickedButton, null);
                    slot.putStack(skillStack2);
                }
            }
        }
        else if (mode == 3 && player.capabilities.isCreativeMode && inventoryPlayer.getSkillStack() == null && slotId >= 0)
        {
            // 模式3: 鼠标中键
            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot != null && slot.hasStack())
            {
                inventoryPlayer.setSkillStack(slot.getStack().copy());
            }
        }
        /*
         * 模式4: 丢弃
         * 模式5: 拖拽
         * 模式6: 左键双击
         */

        return skillStack;
    }

    public SkillStack transferSkillStackInSlot(EntityPlayer player, int index)
    {
        SkillSlot slot = this.inventorySkillSlots.get(index);
        return slot != null ? slot.getStack() : null;
    }

    protected boolean mergeSkillStack(SkillStack skillStack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean move = false;
        int i = reverseDirection ? endIndex - 1 : startIndex;

        while (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)
        {
            SkillSlot slot = this.inventorySkillSlots.get(i);
            SkillStack skillStack2 = slot.getStack();

            if (skillStack2 == null && slot.isSkillValid(skillStack))
            {
                slot.putStack(skillStack.copy());
                slot.onSlotChanged();
                move = true;
                break;
            }

            if (reverseDirection)
            {
                --i;
            }
            else
            {
                ++i;
            }
        }

        return move;
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);

        if (listener instanceof EntityPlayer)
        {
            IPlayerMagic magic = ((EntityPlayer) listener).getCapability(CapabilityLoader.PLAYER_MAGIC, null);
            magic.getSkillInventoryContainer().onLearnGuiOpened(magic);
        }
    }

    public void onLearnGuiOpened(ILearning listener)
    {
        if (this.learners.contains(listener))
        {
            throw new IllegalArgumentException("Listener already listening");
        }
        else
        {
            this.learners.add(listener);
            listener.updateLearningInventory(this, this.getSkillStacks());
            this.detectAndSendChanges();
        }
    }

    @SideOnly(Side.CLIENT)
    public void removeLearner(ILearning listener)
    {
        this.learners.remove(listener);
    }

    public void onLearnMatrixChanged(ISkillInventory inventory)
    {
        this.detectAndSendChanges();
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        SkillInventoryPlayer skillInventoryPlayer = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null).getSkillInventory();

        if (skillInventoryPlayer.getSkillStack() != null)
        {
            skillInventoryPlayer.addSkillStackToInventory(skillInventoryPlayer.getSkillStack());
            skillInventoryPlayer.setSkillStack(null);
        }
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.inventorySkillSlots.size(); ++i)
        {
            SkillStack newStack = this.inventorySkillSlots.get(i).getStack();
            SkillStack oldStack = this.inventorySkillStacks.get(i);

            if (!SkillStack.areSkillStacksEqual(oldStack, newStack))
            {
                oldStack = newStack == null ? null : newStack.copy();
                this.inventorySkillStacks.set(i, oldStack);

                for (int j = 0; j < this.learners.size(); ++j)
                {
                    this.learners.get(j).sendSlotContents(this, i, oldStack);
                }
            }
        }
    }
}
