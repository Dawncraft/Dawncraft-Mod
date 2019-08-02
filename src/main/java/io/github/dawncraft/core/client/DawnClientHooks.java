package io.github.dawncraft.core.client;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.dawncraft.api.client.event.TextComponentEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Some client hooks used by ASM.
 *
 * @author QingChenW
 */
public class DawnClientHooks
{
    public static final Set<Block> builtInBlocks = Sets.newHashSet();
    public static final Map<Block, String> blockParticles = Maps.newHashMap();

    /**
     * Register builtin blocks.
     *
     * @param blocks Blocks
     */
    public static void registerBuiltInBlocks(Block... blocks)
    {
        builtInBlocks.addAll(Lists.newArrayList(blocks));
    }

    /**
     * Register builtin block's texture.
     *
     * @param block Block
     * @param iconName Texture name
     */
    public static void registerBlockParticle(Block block, String iconName)
    {
        blockParticles.put(block, iconName);
    }

    /**
     * {@link net.minecraft.client.renderer.BlockModelShapes#registerAllBlocks()}
     */
    public static void onRegisterAllBlocks(BlockModelShapes shapes)
    {
        shapes.registerBuiltInBlocks(builtInBlocks.toArray(new Block[0]));
    }

    /**
     * {@link net.minecraft.client.renderer.BlockModelShapes#getTexture(IBlockState)}
     */
    public static TextureAtlasSprite getBlockParticle(ModelManager modelManager, Block block)
    {
        if (blockParticles.containsKey(block))
        {
            return modelManager.getTextureMap().getAtlasSprite(blockParticles.get(block));
        }
        return modelManager.getMissingModel().getParticleTexture();
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
