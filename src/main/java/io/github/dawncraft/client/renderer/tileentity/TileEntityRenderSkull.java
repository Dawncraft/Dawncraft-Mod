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
public class TileEntityRenderSkull extends TileEntitySpecialRenderer<TileEntitySkull>
{
    public static TileEntityRenderSkull instance;
    private RenderManager renderManager;
    private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);

    @Override
    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcher)
    {
        super.setRendererDispatcher(rendererDispatcher);
        instance = this;
    }

    @Override
    public void render(TileEntitySkull tileentityskull, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(tileentityskull, x, y, z, partialTicks, destroyStage, alpha);

        if (tileentityskull != null)
        {
            if (tileentityskull.usedByItemStackRenderer())
            {
                this.renderSkullItem((float) x, (float) y, (float) z, tileentityskull.getSkullType(), destroyStage);
            }
            else
            {
                EnumFacing facing = EnumFacing.byIndex(tileentityskull.getBlockMetadata() & 7);
                this.renderSkull((float) x, (float) y, (float) z, facing, tileentityskull.getSkullType(), tileentityskull.getSkullRotation() * 360 / 16.0F, destroyStage);
            }
        }
        else
        {
            this.renderSkullItem((float) x, (float) y, (float) z, 0, destroyStage);
        }
    }

    public void renderSkullItem(float x, float y, float z, int skulltype, int destroystage)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5F, 0.0F, -0.5F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.disableCull();
        this.renderSkull(x, y, z, EnumFacing.UP, skulltype, 0.0F, destroystage);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    public void renderSkull(float x, float y, float z, EnumFacing enumfacing, int skulltype, float skullrotation, int destroystage)
    {
        if (this.renderManager == null) this.renderManager = Minecraft.getMinecraft().getRenderManager();

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
            Render<? extends Entity> renderer = this.renderManager.getEntityClassRenderObject(ItemSkull.skullTypes[skulltype]);
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
        modelbase.render((Entity) null, 0.0F, 0.0F, 0.0F, skullrotation, 0.0F, 0.0625F);
        GlStateManager.popMatrix();

        if (destroystage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
