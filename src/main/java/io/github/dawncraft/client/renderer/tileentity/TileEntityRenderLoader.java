package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TileEntityRenderLoader
{
    private static int nextID = 0;

    public TileEntityRenderLoader(FMLPreInitializationEvent event)
    {
        registerSkullTexture("textures/entity/savage.png", false);
        registerSkullTexture("textures/entity/barbarian_king.png", false);
        registerSkullTexture("textures/entity/ger_king.png", true);
        registerTileEntityRender(TileEntitySkull.class, new TileEntityRenderSkull());
        registerTileItemRenderWithMeta(ItemLoader.skull, 0, TileEntitySkull.class);
        registerTileItemRenderWithMeta(ItemLoader.skull, 1, TileEntitySkull.class);
        registerTileItemRenderWithMeta(ItemLoader.skull, 2, TileEntitySkull.class);
    }
    
    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }

    /**
     * 这回吐槽Forge的设计，在1.9将会被移除，别忘了改
     * <br>知道为啥了，这个不能渲染带特殊值的te，例如头颅，需替代方案...目前没有</br>
     * @param metadata
     * @deprecated
     */
    @Deprecated
    private static void registerTileItemRender(Item item, Class<? extends TileEntity> TileClass)
    {
        registerTileItemRenderWithMeta(item, 0, TileClass);
    }
    
    @Deprecated
    private static void registerTileItemRenderWithMeta(Item item, int metadata, Class<? extends TileEntity> TileClass)
    {
        ForgeHooksClient.registerTESRItemStack(item, metadata, TileClass);
    }

    private static void registerSkullTexture(String skullTexture, Boolean layer)
    {
        TileEntityRenderSkull.addSkullTexture(nextID, new ResourceLocation(Dawncraft.MODID + ":" + skullTexture));
        TileEntityRenderSkull.setSkullLayer(nextID, layer);
        nextID++;
    }
}
