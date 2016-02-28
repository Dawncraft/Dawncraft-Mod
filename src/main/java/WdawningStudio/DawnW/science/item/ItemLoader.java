package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.science;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader
{
    public static Item magnet_ingot = new ItemMagnet_ingot();
    
    public ItemLoader(FMLPreInitializationEvent event)
    {
        register(magnet_ingot, "magnet_ingot");
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(magnet_ingot, "magnet_ingot");
    }
    
    private static void register(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(science.MODID + ":" + name, "inventory"));
    }
}