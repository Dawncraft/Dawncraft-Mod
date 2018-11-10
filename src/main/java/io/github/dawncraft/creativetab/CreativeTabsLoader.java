package io.github.dawncraft.creativetab;

import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.client.renderer.entity.RenderSkill;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
    public static CreativeTabs tabWeapon;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabColourEgg;

    public static CreativeSkillTabs tabSkills;
    public static CreativeSkillTabs tabSearch;
    public static CreativeSkillTabs tabInventory;
    
    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
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
        tabWeapon = new CreativeTabs("Weapon")
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
                ResourceLocation res = RenderSkill.getActualLocation(new ResourceLocation(SkillLoader.heal.getRegistryName()));
                return TextureLoader.getTextureLoader().getTextureMapSkills().getAtlasSprite(res.toString());
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
                ResourceLocation res = RenderSkill.getActualLocation(new ResourceLocation(SkillLoader.heal.getRegistryName()));
                return TextureLoader.getTextureLoader().getTextureMapSkills().getAtlasSprite(res.toString());
            }
        }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    }
}
