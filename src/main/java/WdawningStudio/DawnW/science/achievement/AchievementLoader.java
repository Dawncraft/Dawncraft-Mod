package WdawningStudio.DawnW.science.achievement;

import WdawningStudio.DawnW.science.science;
import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
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
    public static AchievementPage pageScience = new AchievementPage(science.NAME, Ger);
}