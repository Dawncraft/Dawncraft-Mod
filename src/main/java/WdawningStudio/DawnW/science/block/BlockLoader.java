package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.science;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader
{
    public static Block magnet_ore = new BlockMagnet_ore();
    public static Block magnet_block = new BlockMagnet_block();

    public BlockLoader(FMLPreInitializationEvent event)
    {
        register(magnet_ore, "magnet_ore"); 
        register(magnet_block, "magnet_block"); 
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(magnet_ore, "magnet_ore");
        registerRender(magnet_block, "magnet_block");    
    }

    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(science.MODID + ":" + name, "inventory"));
    }
}