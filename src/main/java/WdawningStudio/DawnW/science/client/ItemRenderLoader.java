package WdawningStudio.DawnW.science.client;

import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;

public class ItemRenderLoader
{
    public ItemRenderLoader()
    {
        ItemLoader.registerRenders();
        BlockLoader.registerRenders();
    }
}