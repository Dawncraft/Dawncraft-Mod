package io.github.dawncraft.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;

/**
 * 用于ASM,不过只是暂时凑合着用
 *
 * @author QingChenW
 */
@Deprecated
public class RendererHelper
{
    public static Set<Block> BuiltInBlocks = new HashSet<Block>();
    public static Map<Block, String> BlockBrokenTextures = new HashMap<Block, String>();
    
    /**
     * 注册BuiltIn方块,实际上并不建议使用,但这是这个已经被遗弃的版本的唯一的方法
     *
     * @param blocks 方块
     */
    @Deprecated
    public static void registerBuiltInBlocks(Block... blocks)
    {
        BuiltInBlocks.addAll(Lists.newArrayList(blocks));
    }
    
    /**
     * 注册BuiltIn方块的破坏粒子材质,也不建议使用,但这依然是这个已经被遗弃的版本的唯一的方法
     *
     * @param block 方块
     * @param iconName 材质名称
     */
    @Deprecated
    public static void registerBreakTexture(Block block, String iconName)
    {
        BlockBrokenTextures.put(block, iconName);
    }
    
    /**
     * 注册BuiltIn方块
     * <br>{@link net.minecraft.client.renderer.BlockModelShapes#registerAllBlocks()}</br>
     * 用ASM插在方法末尾
     */
    public void registerBuiltinBlocks(BlockModelShapes shapes)
    {
        shapes.registerBuiltInBlocks(this.BuiltInBlocks.toArray(new Block[0]));
    }

    /**
     * 获取BuiltIn方块的破坏粒子
     * <br>{@link net.minecraft.client.renderer.BlockModelShapes#getTexture(IBlockState)}</br>
     * 用ASM插在<b>if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel())</b>块的末尾
     *
     * @param block 方块
     * @return 材质
     */
    public static TextureAtlasSprite getBreakTexture(ModelManager modelManager, Block block)
    {
        if(BlockBrokenTextures.containsKey(block))
        {
            return modelManager.getTextureMap().getAtlasSprite(BlockBrokenTextures.get(block));
        }
        return modelManager.getMissingModel().getParticleTexture();
    }
}
