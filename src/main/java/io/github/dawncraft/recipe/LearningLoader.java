package io.github.dawncraft.recipe;

import io.github.dawncraft.api.skill.learning.IRecipe;
import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.skill.SkillInit;
import io.github.dawncraft.skill.SkillStack;

public class LearningLoader
{
    public static void initLearning()
    {
        registerRecipe(new SkillStack(SkillInit.heal, 2), SkillInit.heal, SkillInit.heal);
        registerRecipe(new SkillStack(SkillInit.fireball, 1), SkillInit.attack, SkillInit.heal);
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
