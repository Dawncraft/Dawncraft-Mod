package io.github.dawncraft.client.renderer.tileentity;

import java.lang.reflect.Method;

import io.github.dawncraft.item.ItemSkull;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The tile entity renderer of dawncraft's entity skull
 *
 * @author QingChenW
 */
@SideOnly(Side.CLIENT)
public class TileEntityRendererSkull extends TileEntitySpecialRenderer<TileEntitySkull>
{
    public static TileEntityRendererSkull instance;
    private RenderManager renderManager;
    private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcher)
    {
        super.setRendererDispatcher(rendererDispatcher);
        instance = this;
        this.renderManager = Minecraft.getMinecraft().getRenderManager();
    }

    @Override
    public void render(TileEntitySkull tileentityskull, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (tileentityskull != null)
        {
            EnumFacing facing = EnumFacing.byIndex(tileentityskull.getBlockMetadata() & 7);
            this.renderSkull((float) x, (float) y, (float) z, tileentityskull.getSkullType(), facing, tileentityskull.getSkullRotation() * 360 / 16.0F, destroyStage);
        }
    }

    public void renderSkull(float x, float y, float z, int skullType, EnumFacing facing, float rotation, int destroyStage)
    {
        ModelBase modelbase = this.skeletonHead;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            Render<? extends Entity> renderer = this.renderManager.getEntityClassRenderObject(ItemSkull.skullTypes[skullType]);
            Method method = ReflectionHelper.findMethod(Render.class, "getEntityTexture", "func_110775_a", Entity.class);
            ResourceLocation textureResource = null;
            try
            {
                textureResource = (ResourceLocation) method.invoke(renderer, (Entity) null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.bindTexture(textureResource);
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        if (facing != EnumFacing.UP)
        {
            switch (facing)
            {
            default:
            case NORTH:
                GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.75F);
                break;
            case SOUTH:
                GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.25F);
                rotation = 180.0F;
                break;
            case WEST:
                GlStateManager.translate(x + 0.75F, y + 0.25F, z + 0.5F);
                rotation = 270.0F;
                break;
            case EAST:
                GlStateManager.translate(x + 0.25F, y + 0.25F, z + 0.5F);
                rotation = 90.0F;
            }
        }
        else
        {
            GlStateManager.translate(x + 0.5F, y, z + 0.5F);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        modelbase.render(null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
