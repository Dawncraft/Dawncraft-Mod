package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.tileentity.TileEntityMagnetChest;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Register tileentity renderer.
 *
 * @author QingChenW
 */
public class TileEntityRendererInit
{
    public static void initTileEntityRenderer()
    {
        registerTileEntityRenderer(TileEntityMagnetChest.class, new TileEntityRendererChest());
        registerTileEntityRenderer(TileEntitySkull.class, new TileEntityRendererSkull());
    }

    public static void initTileEntityItemStackRenderer()
    {
        registerTileEntityItemStackRenderer(BlockInit.MAGNET_CHEST, DawnTileEntityItemStackRenderer.instance);
        registerTileEntityItemStackRenderer(BlockInit.SUPER_CHEST, DawnTileEntityItemStackRenderer.instance);
        registerTileEntityItemStackRenderer(ItemInit.SKULL, DawnTileEntityItemStackRenderer.instance);
    }

    /**
     * Register a tileentity's renderer.
     *
     * @param tileEntityClass the class of tileentity
     * @param renderer the renderer of tileentity
     */
    private static <T extends TileEntity> void registerTileEntityRenderer(Class<T> tileEntityClass, TileEntitySpecialRenderer<? super T> renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }

    /**
     * Register tileentity itemstack renderer for an item.
     *
     * @param item the item to register
     * @param teisr the teisr of item
     */
    private static void registerTileEntityItemStackRenderer(Item item, TileEntityItemStackRenderer teisr)
    {
        item.setTileEntityItemStackRenderer(teisr);
    }

    /**
     * Register tileentity itemstack renderer for a block's item.
     *
     * @param block the block to register
     * @param teisr the teisr of block
     */
    private static void registerTileEntityItemStackRenderer(Block block, TileEntityItemStackRenderer teisr)
    {
        registerTileEntityItemStackRenderer(Item.getItemFromBlock(block), teisr);
    }
}
