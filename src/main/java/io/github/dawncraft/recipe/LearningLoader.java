package io.github.dawncraft.recipe;

import io.github.dawncraft.api.skill.learning.IRecipe;
import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.skill.SkillLoader;
import io.github.dawncraft.skill.SkillStack;

public class LearningLoader
{
    public static void initLearning()
    {
        registerRecipe(new SkillStack(SkillLoader.heal, 2), SkillLoader.heal, SkillLoader.heal);
        registerRecipe(new SkillStack(SkillLoader.fireball, 1), SkillLoader.attack, SkillLoader.heal);
    }

    private static void registerRecipe(SkillStack output, Object... params)
    {
        LearningManager.INSTANCE.addRecipe(output, params);
    }

    private static void registerRecipe(IRecipe recipe)
    {
        LearningManager.INSTANCE.addRecipe(recipe);
    }
}
