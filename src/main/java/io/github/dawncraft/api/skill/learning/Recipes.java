package io.github.dawncraft.api.skill.learning;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import io.github.dawncraft.container.SkillInventoryLearning;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class Recipes implements IRecipe
{
    public final List<SkillStack> skills;
    public final List<ItemStack> items;
    /** Is the SkillStack that you get when learn the recipe. */
    private final SkillStack output;

    public Recipes(SkillStack output, List<SkillStack> skills, List<ItemStack> items)
    {
        this.skills = skills;
        this.items = items;
        this.output = output;
    }

    @Override
    public boolean matches(SkillInventoryLearning inventory, World world)
    {
        List<SkillStack> skillsList = Lists.newArrayList(this.skills);
        for (int i = 0; i < inventory.getSkillInventoryCount(); ++i)
        {
            SkillStack stack = inventory.getSkillStackInSlot(i);

            if (stack != null)
            {
                boolean flag = false;

                for (SkillStack skillStack : skillsList)
                {
                    if (stack.getSkill() == skillStack.getSkill() && stack.getLevel() == skillStack.getLevel())
                    {
                        flag = true;
                        skillsList.remove(skillStack);
                        break;
                    }
                }

                if (!flag)
                {
                    return false;
                }
            }
        }

        List<ItemStack> itemsList = Lists.newArrayList(this.items);
        for (int i = 0; i < inventory.getInventoryCount(); ++i)
        {
            ItemStack stack = inventory.getStackInSlot(i);

            if (stack != null)
            {
                boolean flag = false;

                for (ItemStack itemStack : itemsList)
                {
                    if (stack.getItem() == itemStack.getItem() && (itemStack.getMetadata() == 32767 || stack.getMetadata() == itemStack.getMetadata()))
                    {
                        flag = true;
                        itemsList.remove(itemStack);
                        break;
                    }
                }

                if (!flag)
                {
                    return false;
                }
            }
        }

        return skillsList.isEmpty() && itemsList.isEmpty();
    }

    @Override
    public ItemStack[] getRemainingItems(SkillInventoryLearning inventory)
    {
        ItemStack[] itemStacks = new ItemStack[inventory.getSizeInventory()];

        for (int i = 0; i < itemStacks.length; ++i)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);
            itemStacks[i] = ForgeHooks.getContainerItem(itemstack);
        }

        return itemStacks;
    }

    @Override
    public SkillStack getLearningResult(SkillInventoryLearning inventory)
    {
        return this.output.copy();
    }

    @Override
    public Pair<Integer, Integer> getSize()
    {
        return Pair.of(this.skills.size(), this.items.size());
    }

    @Override
    public SkillStack getOutput()
    {
        return this.output;
    }
}
