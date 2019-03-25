package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.core.client.DawnClientHooks;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.tileentity.TileEntityMagnetChest;
import io.github.dawncraft.tileentity.TileEntitySkull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Register tileentity render.
 *
 * @author QingChenW
 */
public class TileEntityRenderLoader
{
    public static void initTileEntityRender()
    {
        registerTileentityRenderer(TileEntityMagnetChest.class, new TileEntityRenderChest());
        registerTileentityRenderer(TileEntitySkull.class, new TileEntityRenderSkull());
        
        registerTEISRWithTE(BlockLoader.magnetChest, 0, new TileEntityMagnetChest());
        registerTEISRWithTE(BlockLoader.superChest, 0, new TileEntityChest(0));
        registerTEISRWithTE(ItemLoader.skull, 0, new TileEntitySkull(0));
        registerTEISRWithTE(ItemLoader.skull, 1, new TileEntitySkull(1));
        registerTEISRWithTE(ItemLoader.skull, 2, new TileEntitySkull(2));

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
    private static void registerTileentityRenderer(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
    
    /**
     * Register tileentity itemstack render for item in inventory.
     * <br>It don't supports tileentity with custom data like skulls.</br>
     *
     * @param item item to register
     * @param metadata metadata to register
     * @param tileEntityClass tileentity's class to register
     */
    @Deprecated
    private static void registerTEISRenderer(Item item, int metadata, Class<? extends TileEntity> tileEntityClass)
    {
        ForgeHooksClient.registerTESRItemStack(item, metadata, tileEntityClass);
    }

    /**
     * Register tileentity itemstack render for item with a specific tileentity in inventory.
     * <br>You should new a tileentity instance for renderer.</br>
     *
     * @param item item to register
     * @param metadata metadata to register
     * @param tileentity tileentity instance to register
     */
    private static void registerTEISRWithTE(Item item, int metadata, TileEntity tileentity)
    {
        registerTEISRenderer(item, metadata, tileentity.getClass());
        DawnClientHooks.registerItemTileentity(item, metadata, tileentity);
    }

    /**
     * See {@link registerTEISRWithTE(Item item, int metadata, TileEntity tileentity)}
     *
     * @param block block to register
     * @param metadata metadata to register
     * @param tileentity tileentity instance to register
     */
    private static void registerTEISRWithTE(Block block, int metadata, TileEntity tileentity)
    {
        registerTEISRWithTE(Item.getItemFromBlock(block), metadata, tileentity);
    }
    
    private static int nextID = 0;
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
