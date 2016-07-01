package com.github.wdawning.dawncraft.achievement;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.item.ItemLoader;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementLoader
{
    public static Achievement Ger = new Achievement("achievement.science.Ger", "science.Ger", 0, 0, ItemLoader.gerHeart, null);
    //AchievementList.XX

    public AchievementLoader()
    {
    	Ger.setSpecial().registerStat();
        AchievementPage.registerAchievementPage(pageScience);
    }

    //register new Achievement page
    public static AchievementPage pageScience = new AchievementPage(dawncraft.NAME, Ger);
}