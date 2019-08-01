package io.github.dawncraft.creativetab;

import com.google.common.collect.ObjectArrays;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.enchantment.EnchantmentInit;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.skill.SkillInit;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

/**
 * Register CreativeTabs in the class.
 *
 * @author QingChenW
 */
public class CreativeTabsLoader
{
    public static CreativeTabs tabScience;
    public static CreativeTabs tabEnergy;
    public static CreativeTabs tabMachine;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabCuisine;
    public static CreativeTabs tabWeapons;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabColourEgg;

    public static CreativeSkillTabs tabSkills;
    public static CreativeSkillTabs tabSearch;
    public static CreativeSkillTabs tabInventory;

    public static void initCreativeTabs()
    {
        addEnchantmentTypes(CreativeTabs.COMBAT, EnchantmentInit.WAND);

        tabScience = new CreativeTabs("Science")
        {
            @Override
            public ItemStack createIcon()
            {
                return null;
            }
        };
        tabEnergy = new CreativeTabs("Energy")
        {
            @Override
            public ItemStack createIcon()
            {
                return null;
            }
        };
        tabMachine = new CreativeTabs("Machine")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(BlockInit.MACHINE_FURNACE);
            }
        };
        tabComputer = new CreativeTabs("Computer")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(BlockInit.SIMPLE_COMPUTER);
            }
        };

        tabFurniture = new CreativeTabs("Furniture")
        {
            @Override
            public ItemStack createIcon()
            {
                return  new ItemStack(BlockInit.WOOD_TABLE);
            }
        };
        tabCuisine = new CreativeTabs("Cuisine")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.COOKED_EGG);
            }
        };
        tabWeapons = new CreativeTabs("Weapons")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.GUN_RPG);
            }
        };
        tabMagic = new CreativeTabs("Magic")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.MAGIC_DUST);
            }
        };
        tabColourEgg = new CreativeTabs("ColourEgg")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.GOLDIAMOND_SWORD);
            }
        };

        tabSkills = new CreativeSkillTabs("Skills")
        {
            @Override
            public SkillStack createIcon()
            {
                return new SkillStack(SkillInit.HEAL);
            }
        };
        tabSearch = new CreativeSkillTabs(5, "Search")
        {
            @Override
            public SkillStack createIcon()
            {
                return null;
            }

            @Override
            public TextureAtlasSprite getIconSprite()
            {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:items/paper");
            }
        }.setBackgroundImageName("skill_search.png");
        tabInventory = new CreativeSkillTabs(11, "skillInventory")
        {
            @Override
            public SkillStack createIcon()
            {
                return null;
            }

            @Override
            public TextureAtlasSprite getIconSprite()
            {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Dawncraft.MODID + ":" + "items/magic/skill_book");
            }
        }.setBackgroundImageName("skill_inventory.png").setNoScrollbar().setNoTitle();
    }

    public static void addEnchantmentTypes(CreativeTabs tab, EnumEnchantmentType... types)
    {
        EnumEnchantmentType[] newEnchantmentTypes = ObjectArrays.concat(tab.getRelevantEnchantmentTypes(), types, EnumEnchantmentType.class);
        tab.setRelevantEnchantmentTypes(newEnchantmentTypes);
    }
}
