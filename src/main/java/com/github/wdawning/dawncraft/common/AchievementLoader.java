package com.github.wdawning.dawncraft.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;

import net.minecraftforge.common.AchievementPage;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.item.ItemLoader;

public class AchievementLoader
{
    public static Achievement Ger = new Achievement("achievement.dawncraft.Ger", "dawncraft.Ger", 0, -4, ItemLoader.gerHeart, null);
    public static Achievement dawnPortal = new Achievement("achievement.dawncraft.dawnPortal", "dawncraft.dawnPortal", 0, -2, Blocks.obsidian, null);
    public static Achievement dawnArrival = new Achievement("achievement.dawncraft.dawnArrival", "dawncraft.dawnArrival", 2, -2, Items.apple, dawnPortal);
    //AchievementList.XX

    public AchievementLoader()
    {
    	Ger.setSpecial().registerStat();
    	dawnPortal.registerStat();
    	dawnArrival.registerStat();
    	
        AchievementPage.registerAchievementPage(pageScience);
    }

    //register new Achievement page
    public static AchievementPage pageScience = new AchievementPage(dawncraft.NAME, Ger, dawnPortal, dawnArrival);
}