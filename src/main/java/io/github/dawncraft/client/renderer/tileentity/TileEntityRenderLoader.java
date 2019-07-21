package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.core.client.DawnClientHooks;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.item.ItemSkull;
import io.github.dawncraft.tileentity.TileEntityMagnetChest;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest.Type;
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

        registerTEISRWithTE(BlockInit.magnetChest, 0, new TileEntityMagnetChest());
        registerTEISRWithTE(BlockInit.superChest, 0, new TileEntityChest(Type.BASIC));
        for (int i = 0; i < ItemSkull.skullTypes.length; i++)
        {
            registerTEISRWithTE(ItemInit.skull, i, new TileEntitySkull(i));
        }
    }

    /**
     * Register a tileentity's renderer.
     *
     * @param tileEntityClass the class of tileentity
     * @param renderer the renderer of tileentity
     */
    private static <T extends TileEntity> void registerTileentityRenderer(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> renderer)
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
