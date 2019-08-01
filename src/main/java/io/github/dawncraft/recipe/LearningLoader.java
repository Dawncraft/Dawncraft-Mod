package io.github.dawncraft.recipe;

import io.github.dawncraft.api.skill.learning.IRecipe;
import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.skill.SkillInit;
import io.github.dawncraft.skill.SkillStack;

public class LearningLoader
{
    public static void initLearning()
    {
        registerRecipe(new SkillStack(SkillInit.HEAL, 2), SkillInit.HEAL, SkillInit.HEAL);
        registerRecipe(new SkillStack(SkillInit.FIREBALL, 1), SkillInit.ATTACK, SkillInit.HEAL);
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
