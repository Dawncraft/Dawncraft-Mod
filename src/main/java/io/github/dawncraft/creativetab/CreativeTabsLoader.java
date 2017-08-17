package io.github.dawncraft.creativetab;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register CreativeTabs in the class.
 *
 * @author QingChenW
 */
public class CreativeTabsLoader
{
    public static CreativeTabs tabEnergy;
    public static CreativeTabs tabMagnetism;
    public static CreativeTabs tabMachine;
    public static CreativeTabs tabComputer;
    public static CreativeTabs tabScience;
    public static CreativeTabs tabFurniture;
    public static CreativeTabs tabFood;
    public static CreativeTabs tabGuns;
    public static CreativeTabs tabMagic;
    public static CreativeTabs tabColourEgg;

    public static CreativeSkillTabs tabSearch;
    public static CreativeSkillTabs tabSkills;
    
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
        tabMagnetism = new CreativeTabs("Magnetism")
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
        tabFood = new CreativeTabs("Food")
        {
            @Override
            public Item getTabIconItem()
            {
                return ItemLoader.faeces;
            }
        };
        tabGuns = new CreativeTabs("Guns")
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
        
        tabSearch = new CreativeSkillTabs("Search")
        {
            @Override
            public Skill getTabIconSkill()
            {
                return SkillLoader.attack;
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
    }
}
