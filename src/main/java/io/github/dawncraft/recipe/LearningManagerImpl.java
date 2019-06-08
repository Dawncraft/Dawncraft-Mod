package io.github.dawncraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.List;

import io.github.dawncraft.api.skill.learning.IRecipe;
import io.github.dawncraft.api.skill.learning.LearningManager;
import io.github.dawncraft.api.skill.learning.Recipes;
import io.github.dawncraft.container.SkillInventoryLearning;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;

public class LearningManagerImpl extends LearningManager
{
    /** The static instance of this class */
    public static final LearningManager INSTANCE = new LearningManagerImpl();
    
    private final List<IRecipe> recipes = Lists.<IRecipe>newArrayList();
    
    @Override
    public Recipes addRecipe(SkillStack skillStack, Object... recipeComponents)
    {
        List<ItemStack> itemsList = Lists.<ItemStack>newArrayList();
        List<SkillStack> skillsList = Lists.<SkillStack>newArrayList();
        
        for (Object object : recipeComponents)
        {
            if (object instanceof ItemStack)
            {
                itemsList.add(((ItemStack) object).copy());
            }
            else if (object instanceof SkillStack)
            {
                skillsList.add(((SkillStack) object).copy());
            }
            else if (object instanceof Item)
            {
                itemsList.add(new ItemStack((Item) object));
            }
            else if (object instanceof Block)
            {
                itemsList.add(new ItemStack((Block) object));
            }
            else if (object instanceof Skill)
            {
                skillsList.add(new SkillStack((Skill) object));
            }
            else
            {
                throw new IllegalArgumentException("Invalid skill learn recipe: unknown type " + object.getClass().getName() + "!");
            }
        }

        Recipes recipes = new Recipes(skillStack, skillsList, itemsList);
        this.recipes.add(recipes);
        return recipes;
    }
    
    @Override
    public void addRecipe(IRecipe recipe)
    {
        this.recipes.add(recipe);
    }
    
    @Override
    public SkillStack findMatchedRecipe(SkillInventoryLearning skillInventoryLearning, World world)
    {
        for (IRecipe recipe : this.recipes)
        {
            if (recipe.matches(skillInventoryLearning, world))
            {
                return recipe.getLearningResult(skillInventoryLearning);
            }
        }
        
        return null;
    }
    
    @Override
    public ItemStack[] getRemainingItems(SkillInventoryLearning skillInventoryLearning, World world)
    {
        for (IRecipe recipe : this.recipes)
        {
            if (recipe.matches(skillInventoryLearning, world))
            {
                return recipe.getRemainingItems(skillInventoryLearning);
            }
        }
        
        ItemStack[] itemStack = new ItemStack[skillInventoryLearning.getSizeInventory()];
        
        for (int i = 0; i < itemStack.length; ++i)
        {
            itemStack[i] = skillInventoryLearning.getStackInSlot(i);
        }
        
        return itemStack;
    }
    
    @Override
    public List<IRecipe> getRecipeList()
    {
        return this.recipes;
    }
}
