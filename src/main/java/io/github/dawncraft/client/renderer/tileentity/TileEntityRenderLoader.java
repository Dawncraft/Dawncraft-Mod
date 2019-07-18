package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.core.client.DawnClientHooks;
import io.github.dawncraft.item.ItemInitializer;
import io.github.dawncraft.item.ItemSkullDawn;
import io.github.dawncraft.tileentity.TileEntityMagnetChest;
import io.github.dawncraft.tileentity.TileEntitySkull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
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
        for (int i = 0; i < ItemSkullDawn.skullTypes.length; i++)
        {
            registerTEISRWithTE(ItemInitializer.skull, i, new TileEntitySkull(i));
        }
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
     * See {@link registerTEISRenderer(Item item, int metadata, Class<? extends TileEntity> tileEntityClass)}
     *
     * @param block block to register
     * @param metadata metadata to register
     * @param tileEntityClass tileentity's class to register
     */
    @Deprecated
    private static void registerTEISRenderer(Block block, int metadata, Class<? extends TileEntity> tileEntityClass)
    {
        registerTEISRenderer(Item.getItemFromBlock(block), metadata, tileEntityClass);
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
}
