package io.github.dawncraft.api.skill.learning;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.apache.commons.lang3.tuple.Pair;

import io.github.dawncraft.container.SkillInventoryLearning;
import io.github.dawncraft.skill.SkillStack;

public interface IRecipe
{
    /**
     * Used to check if a recipe matches current learning inventory
     */
    boolean matches(SkillInventoryLearning inventory, World world);
    
    ItemStack[] getRemainingItems(SkillInventoryLearning inventory);
    
    /**
     * Returns an Skill that is the result of this recipe
     */
    SkillStack getLearningResult(SkillInventoryLearning inventory);
    
    /**
     * Returns the size of the recipe areas
     */
    Pair<Integer, Integer> getSize();

    SkillStack getOutput();
}
