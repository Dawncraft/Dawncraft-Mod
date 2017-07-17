package io.github.dawncraft.stats;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register achievements.
 * 
 * @author QingChenW
 */
public class AchievementLoader
{
    public static Achievement Ger = new Achievement("achievement.dawncraft.Ger", "dawncraft.Ger", 0, -4, ItemLoader.gerHeart, null);
    public static Achievement dawnPortal = new Achievement("achievement.dawncraft.dawnPortal", "dawncraft.dawnPortal", 0, -2, Blocks.obsidian, null);
    public static Achievement dawnArrival = new Achievement("achievement.dawncraft.dawnArrival", "dawncraft.dawnArrival", 2, -2, Items.apple, dawnPortal);
    public static Achievement explodeSkeleton = new Achievement("achievement.dawncraft.explodeSkeleton", "dawncraft.explodeSkeleton", 2, 1, ItemLoader.flanRPG, null);
    
    /** A new achievement page for Dawncraft Mod. */
    public static AchievementPage pageDawncraft = new AchievementPage(dawncraft.NAME, Ger, dawnPortal, dawnArrival, explodeSkeleton);
    
    public AchievementLoader(FMLInitializationEvent event)
    {
        Ger.setSpecial().registerStat();
        dawnPortal.registerStat();
        dawnArrival.registerStat();
        explodeSkeleton.setSpecial().registerStat();
        
        AchievementPage.registerAchievementPage(pageDawncraft);
    }
}
