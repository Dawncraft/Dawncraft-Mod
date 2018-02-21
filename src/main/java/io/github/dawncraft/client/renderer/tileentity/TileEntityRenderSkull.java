package io.github.dawncraft.client.renderer.tileentity;

import java.util.HashMap;
import java.util.Map;

import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
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
    private static Map<Integer, ResourceLocation> SKULL_TEXTURES = new HashMap();
    private static Map<Integer, Boolean> SKULL_LAYER = new HashMap();
    public static TileEntityRenderSkull instance;
    private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
    private final ModelSkeletonHead humanoidHead = new ModelHumanoidHead();

    /** addTextures
     * 注册头颅方块实体的材质
     *
     * @param type 要和你的头颅物品中填写的一致
     * @param res 资源位置
     */
    public static void addSkullTexture(int type, ResourceLocation res)
    {
        SKULL_TEXTURES.put(type, res);
    }

    public static void setSkullLayer(int type, Boolean layer)
    {
        SKULL_LAYER.put(type, layer);
    }

    @Override
    public void renderTileEntityAt(TileEntitySkull tileentityskull, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(tileentityskull != null)
        {
            if(tileentityskull.useByRenderer())
            {
                this.renderSkullItem((float)x, (float)y, (float)z, tileentityskull.getSkullType(), destroyStage);
            }
            else
            {
                EnumFacing enumfacing = EnumFacing.getFront(tileentityskull.getBlockMetadata() & 7);
                this.renderSkull((float)x, (float)y, (float)z, enumfacing, tileentityskull.getSkullType(), tileentityskull.getSkullRotation() * 360 / 16.0F, destroyStage);
            }
        }
        else
        {
            this.renderSkullItem((float)x, (float)y, (float)z, 0, destroyStage);
        }
    }

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super.setRendererDispatcher(rendererDispatcherIn);
        instance = this;
    }
    
    public void renderSkullItem(float x, float y, float z, int skulltype, int destroystage)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5F, 0.0F, -0.5F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.disableCull();
        this.renderSkull((float)x, (float)y, (float)z, EnumFacing.UP, skulltype, 0.0F, destroystage);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
    
    public void renderSkull(float x, float y, float z, EnumFacing enumfacing, int skulltype, float skullrotation, int destroystage)
    {
        ModelBase modelbase = this.skeletonHead;

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
            ResourceLocation res = SKULL_TEXTURES.get(skulltype);
            if(res == null)
            {
                res = DefaultPlayerSkin.getDefaultSkinLegacy();
                modelbase = this.humanoidHead;
            }
            else if(SKULL_LAYER.getOrDefault(skulltype, false))
            {
                modelbase = this.humanoidHead;
            }
            this.bindTexture(res);
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        if (enumfacing != EnumFacing.UP)
        {
            switch (enumfacing)
            {
                case NORTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.75F);
                    break;
                case SOUTH:
                    GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.25F);
                    skullrotation = 180.0F;
                    break;
                case WEST:
                    GlStateManager.translate(x + 0.75F, y + 0.25F, z + 0.5F);
                    skullrotation = 270.0F;
                    break;
                case EAST:
                default:
                    GlStateManager.translate(x + 0.25F, y + 0.25F, z + 0.5F);
                    skullrotation = 90.0F;
            }
        }
        else
        {
            GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        modelbase.render((Entity)null, 0.0F, 0.0F, 0.0F, skullrotation, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

        if (destroystage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
