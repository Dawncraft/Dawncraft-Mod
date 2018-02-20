package io.github.dawncraft.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

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
    public static Map<Pair<Item, Integer>, TileEntity> TileEntityItemMap = Maps.newHashMap();
    
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
     * 注册方块实体渲染器在物品栏中渲染时所用的方块实体,非常不建议使用,因为Forge在新版本中提供了解决方案,不过这个已经被遗弃的版本还得用这唯一的方法
     * <br>这回吐槽Forge,好不容易打了个补丁还不能渲染带特殊值的方块实体，例如头颅</br>
     *
     * @param item 物品
     * @param meta 数据值
     * @param tileentity 方块实体
     */
    @Deprecated
    public static void registerTileEntityItem(Item item, int meta, TileEntity tileentity)
    {
        TileEntityItemMap.put(Pair.of(item, meta), tileentity);
    }
    
    /**
     * 注册BuiltIn方块
     * <br>{@link net.minecraft.client.renderer.BlockModelShapes#registerAllBlocks()}</br>
     * 用ASM插在方法末尾
     */
    public static void addBuiltInBlocks(BlockModelShapes shapes)
    {
        shapes.registerBuiltInBlocks(BuiltInBlocks.toArray(new Block[0]));
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
    
    /**
     * 获取方块实体渲染器在物品栏中渲染时所用的方块实体
     * <br>{@link net.minecraftforge.client.ForgeHooksClient#renderTileItem(Item, int)}</br>
     * 用ASM将<b>r.renderTileEntityAt(null, 0, 0, 0, 0, -1);</b>中的<b>null</b>替换为本方法
     * <br><i>这回是给Forge打补丁了[手动滑稽]</i></br>
     *
     * @param item 方块
     * @param meta 数据值
     * @return
     * @return 一个已经实例化好的实体
     */
    @Deprecated // 因为ForgeHooksClient标记为已过时,所以一起过时吧233
    public static TileEntity getTileEntityForRender(Item item, int meta)
    {
        TileEntity tileentity = TileEntityItemMap.get(Pair.of(item, meta));
        if(tileentity != null)
        {
            return tileentity;
        }
        return null;// 方便别人改[滑稽]
    }
    
    // 还差点{@link net.minecraft.client.renderer.entity.layers.LayerCustomHead#doRenderLayer(EntityLivingBase, float, float, float, float, float, float, float)}
}
