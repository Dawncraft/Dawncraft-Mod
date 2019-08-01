package io.github.dawncraft.client;

import io.github.dawncraft.CommonProxy;
import io.github.dawncraft.client.gui.GuiIngameDawn;
import io.github.dawncraft.client.gui.stats.GuiStatLoader;
import io.github.dawncraft.client.particle.ParticleInit;
import io.github.dawncraft.client.renderer.block.BlockRenderInit;
import io.github.dawncraft.client.renderer.entity.EntityRenderInit;
import io.github.dawncraft.client.renderer.item.ItemRenderInit;
import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.client.renderer.skill.RenderSkill;
import io.github.dawncraft.client.renderer.skill.SkillRenderLoader;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.client.renderer.tileentity.TileEntityRenderInit;
import io.github.dawncraft.config.KeyLoader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * The client proxy of Dawncraft Mod.
 *
 * @author QingChenW
 */
public class ClientProxy extends CommonProxy
{
    private static ClientProxy instance;

    private TextureLoader textureLoader;
    private ModelLoader modelLoader;
    private RenderSkill skillRender;
    private GuiIngameDawn ingameGUIDawn;

    public ClientProxy()
    {
        instance = this;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        this.textureLoader = new TextureLoader();
        this.modelLoader = new ModelLoader(this.textureLoader);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        this.skillRender = new RenderSkill(Minecraft.getMinecraft().getTextureManager(), this.modelLoader);
        BlockRenderInit.initBlockRender();
        ItemRenderInit.initItemRender();
        SkillRenderLoader.initSkillRender();
        EntityRenderInit.initEntityRender();
        TileEntityRenderInit.initTileEntityRender();
        ParticleInit.initParticles();
        KeyLoader.initKeys();
        GuiStatLoader.initStatSlots();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        this.ingameGUIDawn = new GuiIngameDawn();
    }

    public TextureLoader getTextureLoader()
    {
        return this.textureLoader;
    }

    public ModelLoader getModelLoader()
    {
        return this.modelLoader;
    }

    public RenderSkill getSkillRender()
    {
        return this.skillRender;
    }

    public GuiIngameDawn getIngameGUIDawn()
    {
        return this.ingameGUIDawn;
    }

    public static ClientProxy getInstance()
    {
        return instance;
    }
}
