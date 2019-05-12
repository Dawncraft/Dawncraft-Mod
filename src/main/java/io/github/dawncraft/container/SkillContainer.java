package io.github.dawncraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;

public abstract class SkillContainer extends Container
{
    public List<SkillStack> inventorySkillStacks = Lists.<SkillStack>newArrayList();
    public List<SkillSlot> inventorySkillSlots = Lists.<SkillSlot>newArrayList();
    private final Set<SkillSlot> dragSkillSlots = Sets.<SkillSlot>newHashSet();
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
        SkillStack skillStack = null;
        SkillInventoryPlayer inventoryplayer = player.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();

        if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1))
        {
            if (slotId == -999)
            {
                if (inventoryplayer.getSkillStack() != null)
                {
                    inventoryplayer.addSkillStackToInventory(skillStack);
                    inventoryplayer.setSkillStack(null);
                }
            }
            else if (mode == 1)
            {
                if (slotId < 0)
                {
                    return null;
                }

                SkillSlot slot = this.inventorySkillSlots.get(slotId);

                if (slot != null && slot.canTakeStack(player))
                {
                    SkillStack skillStack2 = this.transferSkillStackInSlot(player, slotId);

                    if (skillStack2 != null)
                    {
                        Skill skill = skillStack2.getSkill();
                        skillStack = skillStack2.copy();
                    }
                }
            }
            else
            {
                if (slotId < 0)
                {
                    return null;
                }

                SkillSlot slot = this.inventorySkillSlots.get(slotId);

                if (slot != null)
                {
                    SkillStack skillStack2 = slot.getStack();
                    SkillStack skillStack3 = inventoryplayer.getSkillStack();

                    if (skillStack2 != null)
                    {
                        skillStack = skillStack2.copy();
                    }

                    if (skillStack2 == null)
                    {
                        if (skillStack3 != null && slot.isSkillValid(skillStack3))
                        {
                            slot.putStack(skillStack3);
                            inventoryplayer.setSkillStack(null);
                        }
                    }
                    else if (slot.canTakeStack(player))
                    {
                        if (skillStack3 == null)
                        {
                            inventoryplayer.setSkillStack(slot.removeStack());
                            slot.onPickupFromSlot(player, inventoryplayer.getSkillStack());
                        }
                        else if (slot.isSkillValid(skillStack3))
                        {
                            slot.putStack(skillStack3);
                            inventoryplayer.setSkillStack(skillStack2);
                        }
                    }

                    slot.onSlotChanged();
                }
            }
        }
        else if (mode == 2 && clickedButton >= 0 && clickedButton < 9)
        {
            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot.canTakeStack(player))
            {
                SkillStack skillStack2 = inventoryplayer.getStackInSlot(clickedButton);
                boolean flag = skillStack2 == null || slot.inventory == inventoryplayer && slot.isSkillValid(skillStack2);
                int k1 = -1;

                if (!flag)
                {
                    k1 = inventoryplayer.getFirstEmptyStack();
                    flag |= k1 > -1;
                }

                if (slot.hasStack() && flag)
                {
                    SkillStack itemstack3 = slot.getStack();
                    inventoryplayer.setInventorySlot(clickedButton, itemstack3.copy());

                    if ((slot.inventory != inventoryplayer || !slot.isSkillValid(skillStack2)) && skillStack2 != null)
                    {
                        if (k1 > -1)
                        {
                            inventoryplayer.addSkillStackToInventory(skillStack2);
                            slot.removeStack();
                            slot.putStack(null);
                            slot.onPickupFromSlot(player, itemstack3);
                        }
                    }
                    else
                    {
                        slot.removeStack();
                        slot.putStack(skillStack2);
                        slot.onPickupFromSlot(player, itemstack3);
                    }
                }
                else if (!slot.hasStack() && skillStack2 != null && slot.isSkillValid(skillStack2))
                {
                    inventoryplayer.setInventorySlot(clickedButton, null);
                    slot.putStack(skillStack2);
                }
            }
        }
        else if (mode == 3 && player.capabilities.isCreativeMode && inventoryplayer.getSkillStack() == null && slotId >= 0)
        {
            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot != null && slot.hasStack())
            {
                inventoryplayer.setSkillStack(slot.getStack().copy());
            }
        }
        else if (mode == 4 && inventoryplayer.getSkillStack() == null && slotId >= 0)
        {
            SkillSlot slot = this.inventorySkillSlots.get(slotId);

            if (slot != null && slot.hasStack() && slot.canTakeStack(player))
            {
                SkillStack skillStack2 = slot.removeStack();
                slot.onPickupFromSlot(player, skillStack2);
                inventoryplayer.addSkillStackToInventory(skillStack2);
            }
        }

        this.detectAndSendChanges();
        return skillStack;
    }
    
    public SkillStack transferSkillStackInSlot(EntityPlayer player, int index)
    {
        SkillSlot slot = this.inventorySkillSlots.get(index);
        return slot != null ? slot.getStack() : null;
    }
    
    @Override
    public void onCraftGuiOpened(ICrafting listener)
    {
        super.onCraftGuiOpened(listener);
        
        if (listener instanceof EntityPlayer)
        {
            IPlayerMagic magic = ((EntityPlayer) listener).getCapability(CapabilityLoader.playerMagic, null);
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
    
    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        SkillInventoryPlayer skillInventoryPlayer = player.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        
        if (skillInventoryPlayer.getSkillStack() != null)
        {
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
    
    public void onLearnMatrixChanged(ISkillInventory inventory)
    {
        this.detectAndSendChanges();
    }
}
