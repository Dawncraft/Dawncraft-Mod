package io.github.dawncraft.stats;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

/**
 * Register achievements.
 *
 * @author QingChenW
 */
public class AchievementLoader
{
    /** A new achievement page for Dawncraft Mod. */
    public static AchievementPage pageDawncraft = new AchievementPage(Dawncraft.NAME);
    
    public static Achievement basic = new Achievement("achievement.dawncraft.basic", "dawncraft.basic", -2, -1, ItemInit.funny, AchievementList.openInventory).initIndependentStat();
    // Energy
    public static Achievement energy = new Achievement("achievement.dawncraft.energy", "dawncraft.energy", 0, -5, ItemInit.bucketPetroleum, basic);
    public static Achievement electricity = new Achievement("achievement.dawncraft.electricity", "dawncraft.electricity", 2, -5, BlockInit.energyGeneratorHeat, energy);
    // Magnetism
    public static Achievement magnetism = new Achievement("achievement.dawncraft.magnetism", "dawncraft.magnetism", 0, -3, ItemInit.magnetIngot, basic);
    public static Achievement magnetCard = new Achievement("achievement.dawncraft.magnetCard", "dawncraft.magnetCard", 2, -3, ItemInit.magnetCard, magnetism);
    // Machine
    public static Achievement machine = new Achievement("achievement.dawncraft.machine", "dawncraft.machine", 0, -1, BlockInit.electricCable, basic);
    public static Achievement machineStarted = new Achievement("achievement.dawncraft.machineStarted", "dawncraft.machineStarted", 2, -1, BlockInit.machineFurnace, machine);
    // Computer
    public static Achievement computer = new Achievement("achievement.dawncraft.computer", "dawncraft.computer", 0, 1, BlockInit.simpleComputer, basic);
    public static Achievement computerStarted = new Achievement("achievement.dawncraft.computerStarted", "dawncraft.computerStarted", 2, 1, BlockInit.superComputer, computer);
    // Science
    // I don't want to make this, because the science tab has nothing now.
    public static Achievement science = new Achievement("achievement.dawncraft.science", "dawncraft.science", 0, 3, Items.stick, basic);
    // Furniture
    public static Achievement furniture = new Achievement("achievement.dawncraft.furniture", "dawncraft.furniture", 0, 5, BlockInit.woodTable, basic);
    // Food
    public static Achievement food = new Achievement("achievement.dawncraft.food", "dawncraft.food", 0, 7, ItemInit.cookedEgg, basic);
    public static Achievement foodFaeces = new Achievement("achievement.dawncraft.foodFaeces", "dawncraft.foodFaeces", 2, 7, ItemInit.faeces, food);
    // Guns
    public static Achievement guns = new Achievement("achievement.dawncraft.guns", "dawncraft.guns", 0, 9, ItemInit.gunAK47, basic);
    public static Achievement gunsRPG = new Achievement("achievement.dawncraft.gunsRpg", "dawncraft.gunsRpg", 2, 9, ItemInit.gunRPG, guns);
    public static Achievement explodeSkeleton = new Achievement("achievement.dawncraft.explodeSkeleton", "dawncraft.explodeSkeleton", 2, 10, ItemInit.gunRPG, gunsRPG).setSpecial();
    // Magic
    public static Achievement magic = new Achievement("achievement.dawncraft.magic", "dawncraft.magic", -3, 1, ItemInit.skillBook, basic);
    // ColourEgg
    public static Achievement arrive = new Achievement("achievement.dawncraft.arrive", "dawncraft.arrive", -3, -3, new ItemStack(ItemInit.skull, 1, 0), basic);
    public static Achievement kill = new Achievement("achievement.dawncraft.kill", "dawncraft.kill", -3, -5, new ItemStack(ItemInit.skull, 1, 1), arrive);
    public static Achievement ger = new Achievement("achievement.dawncraft.ger", "dawncraft.ger", -5, -5, ItemInit.gerHeart, kill).setSpecial();
    public static Achievement dawnPortal = new Achievement("achievement.dawncraft.dawnPortal", "dawncraft.dawnPortal", -5, -3, Blocks.obsidian, ger);
    public static Achievement dawnArrival = new Achievement("achievement.dawncraft.dawnArrival", "dawncraft.dawnArrival", -5, -1, Items.apple, dawnPortal);
    
    public static void initAchievements()
    {
        addAchievements(pageDawncraft, basic);
        
        addAchievements(pageDawncraft, energy, electricity);
        
        addAchievements(pageDawncraft, magnetism, magnetCard);
        
        addAchievements(pageDawncraft, machine, machineStarted);
        
        addAchievements(pageDawncraft, computer, computerStarted);
        
        addAchievements(pageDawncraft, science);
        
        addAchievements(pageDawncraft, furniture);
        
        addAchievements(pageDawncraft, food, foodFaeces);
        
        addAchievements(pageDawncraft, guns, gunsRPG, explodeSkeleton);
        
        addAchievements(pageDawncraft, magic);
        
        addAchievements(pageDawncraft, arrive, kill, ger, dawnPortal, dawnArrival);
        
        registerAchievementPage(pageDawncraft);
    }

    public static void addAchievements(AchievementPage page, Achievement... achievements)
    {
        for (Achievement achievement : achievements)
            addAchievement(page, achievement);
    }
    
    public static void addAchievement(AchievementPage page, Achievement achievement)
    {
        page.getAchievements().add(achievement.registerStat());
    }

    public static void registerAchievementPage(AchievementPage page)
    {
        AchievementPage.registerAchievementPage(page);
    }
}
