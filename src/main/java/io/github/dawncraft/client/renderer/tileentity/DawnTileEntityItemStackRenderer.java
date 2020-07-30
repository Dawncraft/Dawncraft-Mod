package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;

public class DawnTileEntityItemStackRenderer extends TileEntityItemStackRenderer
{
    public static final DawnTileEntityItemStackRenderer instance;

    private final TileEntityChest chestSuper = new TileEntityChest(BlockChest.Type.BASIC);
    private final TileEntitySkull skull = new TileEntitySkull();

    @Override
    public void renderByItem(ItemStack itemStack, float partialTicks)
    {
        Item item = itemStack.getItem();
        if (item == Item.getItemFromBlock(BlockInit.SUPER_CHEST))
        {
            TileEntityRendererDispatcher.instance.render(this.chestSuper, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
        }
        else if (item == ItemInit.SKULL)
        {
            if (TileEntityRendererSkull.instance != null)
            {
                GlStateManager.pushMatrix();
                GlStateManager.disableCull();
                TileEntityRendererSkull.instance.renderSkull(0.0F, 0.0F, 0.0F, itemStack.getMetadata(), EnumFacing.UP, 180.0F, -1);
                GlStateManager.enableCull();
                GlStateManager.popMatrix();
            }
        }
    }

    static
    {
        instance = new DawnTileEntityItemStackRenderer();
    }
}
