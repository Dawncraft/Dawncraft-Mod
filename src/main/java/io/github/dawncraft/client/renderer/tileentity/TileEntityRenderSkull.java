package io.github.dawncraft.client.renderer.tileentity;

import java.util.HashMap;
import java.util.Map;

import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** TileEntityRenderSkull 头颅方块实体渲染类
 * <br>使用TileEntityRenderSkull.addSkullTexture(String type, ResourceLocation res)方法来注册你的材质</br>
 * 注:最好在preInit阶段
 *
 * @author QingChenW
 */
@SideOnly(Side.CLIENT)
public class TileEntityRenderSkull extends TileEntitySpecialRenderer<TileEntitySkull>
{
    private static final ResourceLocation DEFAULT_TEXTURES = new ResourceLocation("textures/entity/steve.png");
    private static Map<String, ResourceLocation> SKULL_TEXTURES = new HashMap();
    public static TileEntityRenderSkull instance;
    private final ModelSkeletonHead head = new ModelSkeletonHead(0, 0, 64, 32);
    
    /** addTextures
     * 注册头颅方块实体的材质
     *
     * @param type 要和你的头颅物品中填写的一致
     * @param res 资源位置
     */
    public static void addSkullTexture(String type, ResourceLocation res)
    {
        SKULL_TEXTURES.put(type, res);
    }
    
    @Override
    public void renderTileEntityAt(TileEntitySkull tileentityskull, double x, double y, double z, float partialTicks, int destroyStage)
    {
        EnumFacing enumfacing = EnumFacing.getFront(tileentityskull.getBlockMetadata() & 7);
        this.renderSkull((float)x, (float)y, (float)z, enumfacing, tileentityskull.getSkullType(), tileentityskull.getSkullRotation() * 360 / 16.0F, destroyStage);
    }
    
    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }

    public void renderSkull(float x, float y, float z, EnumFacing enumfacing, String skulltype, float skullrotation, int destroystage)
    {
        ModelBase modelbase = this.head;
        
        if (destroystage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroystage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            ResourceLocation res = SKULL_TEXTURES.getOrDefault(skulltype, DEFAULT_TEXTURES);
            this.bindTexture(res);
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        
        if (enumfacing != EnumFacing.UP)
        {
            switch (enumfacing)
            {
                case NORTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.74F);
                    break;
                case SOUTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.26F);
                    skullrotation = 180.0F;
                    break;
                case WEST:
                    GlStateManager.translate(x + 0.74F, y + 0.25F, z + 0.5F);
                    skullrotation = 270.0F;
                    break;
                case EAST:
                default:
                    GlStateManager.translate(x + 0.26F, y + 0.25F, z + 0.5F);
                    skullrotation = 90.0F;
            }
        }
        else
        {
            GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        }
        
        float f = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, skullrotation, 0.0F, f);
        GlStateManager.popMatrix();
        
        if (destroystage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
