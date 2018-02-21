package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.core.RendererHelper;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register tileentity render.
 *
 * @author QingChenW
 */
public class TileEntityRenderLoader
{
    private static int nextID = 0;

    public TileEntityRenderLoader(FMLPreInitializationEvent event)
    {
        registerTileEntityRender(TileEntitySkull.class, new TileEntityRenderSkull());
        registerTileItemRenderWithTE(ItemLoader.skull, 0, new TileEntitySkull(0));
        registerTileItemRenderWithTE(ItemLoader.skull, 1, new TileEntitySkull(1));
        registerTileItemRenderWithTE(ItemLoader.skull, 2, new TileEntitySkull(2));
        
        registerSkullTexture("textures/entity/savage.png", false);
        registerSkullTexture("textures/entity/barbarian_king.png", false);
        registerSkullTexture("textures/entity/ger_king.png", true);
    }
    
    /**
     * Register a tileentity's renderer.
     *
     * @param tileEntityClass the class of tileentity
     * @param renderer the renderer of tileentity
     */
    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }

    /**
     * Register tileentity render for item in inventory.
     * <br>It don't supports tileentity with custom data like skulls.</br>
     *
     * @param item item to register
     * @param metadata metadata to register
     * @param tileEntityClass tileentity's class to register
     */
    @Deprecated
    private static void registerTileItemRender(Item item, int metadata, Class<? extends TileEntity> tileEntityClass)
    {
        ForgeHooksClient.registerTESRItemStack(item, metadata, tileEntityClass);
    }
    
    /**
     * Register tileentity render for item in inventory.
     * <br>This can register a tileentity instance for renderer.</br>
     *
     * @param item item to register
     * @param metadata metadata to register
     * @param tileentity tileentity instance to register
     */
    private static void registerTileItemRenderWithTE(Item item, int metadata, TileEntity tileentity)
    {
        registerTileItemRender(item, metadata, tileentity.getClass());
        RendererHelper.registerTileEntityItem(item, metadata, tileentity);
    }

    /**
     * Register texture of skull.
     *
     * @param skullTexture the texture of skull
     * @param layer is the skin has a layer
     */
    private static void registerSkullTexture(String skullTexture, Boolean layer)
    {
        TileEntityRenderSkull.addSkullTexture(nextID, new ResourceLocation(Dawncraft.MODID + ":" + skullTexture));
        TileEntityRenderSkull.setSkullLayer(nextID, layer);
        nextID++;
    }
}
