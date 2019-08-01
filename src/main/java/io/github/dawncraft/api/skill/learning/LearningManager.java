package io.github.dawncraft.api.skill.learning;

import java.util.List;

import io.github.dawncraft.container.SkillInventoryLearning;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LearningManager
{
    public static final LearningManager INSTANCE;

    public abstract void addRecipe(IRecipe recipe);

    public abstract Recipes addRecipe(SkillStack stack, Object... recipeComponents);

    public abstract SkillStack findMatchedRecipe(SkillInventoryLearning skillInventoryLearning, World world);

    public abstract ItemStack[] getRemainingItems(SkillInventoryLearning skillInventoryLearning, World world);

    public abstract List<IRecipe> getRecipeList();

    static
    {
        try
        {
            Class<?> clazz = Class.forName("io.github.dawncraft.recipe.LearningManagerImpl");
            INSTANCE = (LearningManager) clazz.getDeclaredField("INSTANCE").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot find implementation: ", e);
        }
    }
}
