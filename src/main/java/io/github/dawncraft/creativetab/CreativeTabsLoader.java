package io.github.dawncraft.creativetab;

import com.google.common.collect.ObjectArrays;

import java.lang.reflect.Field;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.enchantment.EnchantmentLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Register CreativeTabs in the class.
 *
 * @author QingChenW
 */
public class CreativeTabsLoader
{
    public static CreativeTabs tabEnergy;
    public static CreativeTabs tabMagnet;
    public static CreativeTabs tabMachine;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabScience;
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabCuisine;
    public static CreativeTabs tabWar;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabColourEgg;
    
    public static CreativeSkillTabs tabSkills;
    public static CreativeSkillTabs tabSearch;
    public static CreativeSkillTabs tabInventory;

    public static void initCreativeTabs()
    {
        addEnchantmentTypes(CreativeTabs.tabCombat, EnchantmentLoader.WAND);

        tabEnergy = new CreativeTabs("Energy")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.bucketPetroleum;
            }
        };
        tabMagnet = new CreativeTabs("Magnet")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magnetIngot;
            }
        };
        tabMachine = new CreativeTabs("Machine")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.machineFurnace);
            }
        };
        tabComputer = new CreativeTabs("Computer")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.simpleComputer);
            }
        };
        tabScience = new CreativeTabs("Science")
        {
            @Override
            public Item getTabIconItem()
            {
                return Items.stick;
            }
        };
        tabFurniture = new CreativeTabs("Furniture")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(BlockLoader.woodTable);
            }
        };
        tabCuisine = new CreativeTabs("Cuisine")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.cookedEgg;
            }
        };
        tabWar = new CreativeTabs("War")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.gunRPG;
            }
        };
        tabMagic = new CreativeTabs("Magic")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.magicDust;
            }
        };
        tabColourEgg = new CreativeTabs("ColourEgg")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.goldiamondSword;
            }
        };

        tabSkills = new CreativeSkillTabs("Skills")
        {
            @Override
            public Skill getTabIconSkill()
            {
                return SkillLoader.heal;
            }
        };
        tabSearch = new CreativeSkillTabs(5, "Search")
        {
            @Override
            public Skill getTabIconSkill()
            {
                return null;
            };
            
            @Override
            public TextureAtlasSprite getTabIcon()
            {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Dawncraft.MODID + ":" + "items/magic/skill_book");
            }
        };
        tabInventory = new CreativeSkillTabs(11, "inventory")
        {
            @Override
            public Skill getTabIconSkill()
            {
                return null;
            };
            
            @Override
            public TextureAtlasSprite getTabIcon()
            {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Dawncraft.MODID + ":" + "items/magic/skill_book");
            }
        }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    }
    
    public static void addEnchantmentTypes(CreativeTabs tab, EnumEnchantmentType... types)
    {
        try
        {
            Field field = ReflectionHelper.findField(CreativeTabs.class, "enchantmentTypes", "field_111230_s");
            EnumEnchantmentType[] newEnchantmentTypes = ObjectArrays.concat((EnumEnchantmentType[]) field.get(tab), types, EnumEnchantmentType.class);
            tab.setRelevantEnchantmentTypes(newEnchantmentTypes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
