package io.github.dawncraft.stats;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register achievements.
 *
 * @author QingChenW
 */
public class AchievementLoader
{
    public static Achievement basic = new Achievement("achievement.dawncraft.basic", "Dawncraft.basic", -2, -1, ItemLoader.funny, AchievementList.openInventory).initIndependentStat();
    // Energy
    public static Achievement energy = new Achievement("achievement.dawncraft.energy", "Dawncraft.energy", 0, -5, ItemLoader.bucketPetroleum, basic);
    public static Achievement electricity = new Achievement("achievement.dawncraft.electricity", "Dawncraft.electricity", 2, -5, BlockLoader.energyGeneratorHeat, energy);
    // Magnetism
    public static Achievement magnetism = new Achievement("achievement.dawncraft.magnetism", "Dawncraft.magnetism", 0, -3, ItemLoader.magnetIngot, basic);
    public static Achievement magnetCard = new Achievement("achievement.dawncraft.magnetCard", "Dawncraft.magnetCard", 2, -3, ItemLoader.magnetCard, magnetism);
    // Machine
    public static Achievement machine = new Achievement("achievement.dawncraft.machine", "Dawncraft.machine", 0, -1, BlockLoader.electricCable, basic);
    public static Achievement machineStarted = new Achievement("achievement.dawncraft.machineStarted", "Dawncraft.machineStarted", 2, -1, BlockLoader.machineFurnace, machine);
    // Computer
    public static Achievement computer = new Achievement("achievement.dawncraft.computer", "Dawncraft.computer", 0, 1, BlockLoader.simpleComputer, basic);
    public static Achievement computerStarted = new Achievement("achievement.dawncraft.computerStarted", "Dawncraft.computerStarted", 2, 1, BlockLoader.superComputer, computer);
    // Science
    // I don't want to make this, because the science tab has nothing now.
    public static Achievement science = new Achievement("achievement.dawncraft.science", "Dawncraft.science", 0, 3, Items.stick, basic);
    // Furniture
    public static Achievement furniture = new Achievement("achievement.dawncraft.furniture", "Dawncraft.furniture", 0, 5, BlockLoader.woodTable, basic);
    // Food
    public static Achievement food = new Achievement("achievement.dawncraft.food", "Dawncraft.food", 0, 7, ItemLoader.cakeEgg, basic);
    public static Achievement foodFaeces = new Achievement("achievement.dawncraft.foodFaeces", "Dawncraft.foodFaeces", 2, 7, ItemLoader.faeces, food);
    // Flans
    public static Achievement flans = new Achievement("achievement.dawncraft.flans", "Dawncraft.flans", 0, 9, ItemLoader.gunAK47, basic);
    public static Achievement flansRPG = new Achievement("achievement.dawncraft.flansRpg", "Dawncraft.flansRpg", 2, 9, ItemLoader.gunRPG, flans);
    public static Achievement explodeSkeleton = new Achievement("achievement.dawncraft.explodeSkeleton", "Dawncraft.explodeSkeleton", 2, 10, ItemLoader.gunRPG, flansRPG).setSpecial();
    // Magic
    public static Achievement magic = new Achievement("achievement.dawncraft.magic", "Dawncraft.magic", -3, 1, ItemLoader.magicBook, basic);
    // ColourEgg
    public static Achievement arrive = new Achievement("achievement.dawncraft.arrive", "Dawncraft.arrive", -3, -3, new ItemStack(ItemLoader.skull, 1, 0), basic);
    public static Achievement kill = new Achievement("achievement.dawncraft.kill", "Dawncraft.kill", -3, -5, new ItemStack(ItemLoader.skull, 1, 1), arrive);
    public static Achievement ger = new Achievement("achievement.dawncraft.ger", "Dawncraft.ger", -5, -5, ItemLoader.gerHeart, kill).setSpecial();
    public static Achievement dawnPortal = new Achievement("achievement.dawncraft.dawnPortal", "Dawncraft.dawnPortal", -5, -3, Blocks.obsidian, ger);
    public static Achievement dawnArrival = new Achievement("achievement.dawncraft.dawnArrival", "Dawncraft.dawnArrival", -5, -1, Items.apple, dawnPortal);

    /** A new achievement page for Dawncraft Mod. */
    public static AchievementPage pageDawncraft = new AchievementPage(Dawncraft.NAME);

    public AchievementLoader(FMLInitializationEvent event)
    {
        addAchievement(basic);
        addAchievement(energy);
        addAchievement(electricity);
        addAchievement(magnetism);
        addAchievement(magnetCard);
        addAchievement(machine);
        addAchievement(machineStarted);
        addAchievement(computer);
        addAchievement(computerStarted);
        addAchievement(science);
        addAchievement(furniture);
        addAchievement(food);
        addAchievement(foodFaeces);
        addAchievement(flans);
        addAchievement(flansRPG);
        addAchievement(explodeSkeleton);
        addAchievement(magic);
        addAchievement(arrive);
        addAchievement(kill);
        addAchievement(ger);
        addAchievement(dawnPortal);
        addAchievement(dawnArrival);

        register(pageDawncraft);
    }
    
    public static void addAchievement(Achievement achievement)
    {
        addAchievement(pageDawncraft, achievement);
    }
    
    public static void addAchievement(AchievementPage page, Achievement achievement)
    {
        page.getAchievements().add(achievement.registerStat());
    }
    
    public static void register(AchievementPage page)
    {
        AchievementPage.registerAchievementPage(page);
    }
}
