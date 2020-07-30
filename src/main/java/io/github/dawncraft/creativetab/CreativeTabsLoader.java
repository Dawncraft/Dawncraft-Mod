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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Register CreativeTabs in the class.
 *
 * @author QingChenW
 */
public class CreativeTabsLoader
{
    public static final CreativeTabs SCIENCE;
    public static final CreativeTabs ENERGY;
    public static final CreativeTabs MACHINE;
    public static final CreativeTabs COMPUTER;
    public static final CreativeTabs FURNITURE;
    public static final CreativeTabs CUISINE;
    public static final CreativeTabs WEAPONS;
    public static final CreativeTabs MAGIC;
    public static final CreativeTabs COLOUR_EGG;

    public static final CreativeSkillTabs SKILLS;
    public static final CreativeSkillTabs SEARCH;
    public static final CreativeSkillTabs INVENTORY;

    static
    {
        SCIENCE = new CreativeTabs("Science")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(Items.STICK);
            }
        };
        ENERGY = new CreativeTabs("Energy")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(BlockInit.ELECTRIC_CABLE);
            }
        };
        MACHINE = new CreativeTabs("Machine")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(BlockInit.MACHINE_FURNACE);
            }
        };
        COMPUTER = new CreativeTabs("Computer")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(BlockInit.SIMPLE_COMPUTER);
            }
        };

        FURNITURE = new CreativeTabs("Furniture")
        {
            @Override
            public ItemStack createIcon()
            {
                return  new ItemStack(BlockInit.WOOD_TABLE);
            }
        };
        CUISINE = new CreativeTabs("Cuisine")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.COOKED_EGG);
            }
        };
        WEAPONS = new CreativeTabs("Weapons")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.GUN_RPG);
            }
        };
        MAGIC = new CreativeTabs("Magic")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.MAGIC_DUST);
            }
        };
        COLOUR_EGG = new CreativeTabs("ColourEgg")
        {
            @Override
            public ItemStack createIcon()
            {
                return new ItemStack(ItemInit.GOLDIAMOND_SWORD);
            }
        };

        SKILLS = new CreativeSkillTabs("Skills")
        {
            @Override
            public SkillStack createIcon()
            {
                return new SkillStack(SkillInit.HEAL);
            }
        };
        SEARCH = new CreativeSkillTabs(5, "Search")
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
        INVENTORY = new CreativeSkillTabs(11, "SkillInventory")
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

    public static void initCreativeTabs()
    {
        addEnchantmentTypes(CreativeTabs.COMBAT, EnchantmentInit.WAND);
    }

    public static void addEnchantmentTypes(CreativeTabs tab, EnumEnchantmentType... types)
    {
        EnumEnchantmentType[] newEnchantmentTypes = ObjectArrays.concat(tab.getRelevantEnchantmentTypes(), types, EnumEnchantmentType.class);
        tab.setRelevantEnchantmentTypes(newEnchantmentTypes);
    }
}
