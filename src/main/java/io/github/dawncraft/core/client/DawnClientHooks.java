package io.github.dawncraft.core.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.dawncraft.api.client.event.TextComponentEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Some client hooks used by ASM.
 *
 * @author QingChenW
 */
public class DawnClientHooks
{
    public static Set<Block> BuiltInBlocks = new HashSet<>();
    public static Map<Block, String> BlockParticles = new HashMap<>();
    public static Map<Pair<Item, Integer>, TileEntity> TileentityItemMap = Maps.newHashMap();

    /**
     * Register builtin blocks.
     *
     * @param blocks Blocks
     */
    public static void registerBuiltInBlocks(Block... blocks)
    {
        BuiltInBlocks.addAll(Lists.newArrayList(blocks));
    }

    /**
     * Register builtin block's texture.
     *
     * @param block Block
     * @param iconName Texture name
     */
    public static void registerBlockParticle(Block block, String iconName)
    {
        BlockParticles.put(block, iconName);
    }

    /**
     * Register item with a specific tileentity for TEISR.
     *
     * @param item Item
     * @param meta Meta
     * @param tileentity Tileentity
     */
    @Deprecated
    public static void registerItemTileentity(Item item, int meta, TileEntity tileentity)
    {
        TileentityItemMap.put(Pair.of(item, meta), tileentity);
    }

    /**
     * {@link net.minecraft.client.renderer.BlockModelShapes#registerAllBlocks()}
     */
    public static void onRegisterAllBlocks(BlockModelShapes shapes)
    {
        shapes.registerBuiltInBlocks(BuiltInBlocks.toArray(new Block[0]));
    }

    /**
     * {@link net.minecraft.client.renderer.BlockModelShapes#getTexture(IBlockState)}
     */
    public static TextureAtlasSprite getBlockParticle(ModelManager modelManager, Block block)
    {
        if (BlockParticles.containsKey(block))
        {
            return modelManager.getTextureMap().getAtlasSprite(BlockParticles.get(block));
        }
        return modelManager.getMissingModel().getParticleTexture();
    }

    /**
     * {@link net.minecraftforge.client.ForgeHooksClient#renderTileItem(Item, int)}
     */
    @Deprecated
    public static TileEntity getTileentityForItem(Item item, int meta)
    {
        TileEntity tileentity = TileentityItemMap.get(Pair.of(item, meta));
        return tileentity != null ? tileentity : null;
    }

    // {@link net.minecraft.client.renderer.entity.layers.LayerCustomHead#doRenderLayer(EntityLivingBase, float, float, float, float, float, float, float)}

    public static void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {

    }

    public static void onTextComponentHovered(GuiScreen gui, ITextComponent component, int x, int y)
    {
        MinecraftForge.EVENT_BUS.post(new TextComponentEvent.Hover(gui, component, x, y));
    }

    public static void onTextComponentClicked(GuiScreen gui, ITextComponent component)
    {
        MinecraftForge.EVENT_BUS.post(new TextComponentEvent.Click(gui, component));
    }
}
