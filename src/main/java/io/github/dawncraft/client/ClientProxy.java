package io.github.dawncraft.client;

import io.github.dawncraft.client.event.ClientEventLoader;
import io.github.dawncraft.client.renderer.block.BlockRenderLoader;
import io.github.dawncraft.client.renderer.entity.EntityRenderLoader;
import io.github.dawncraft.client.renderer.entity.RenderSkill;
import io.github.dawncraft.client.renderer.item.ItemRenderLoader;
import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.client.renderer.skill.SkillRenderLoader;
import io.github.dawncraft.client.renderer.tileentity.TileEntityRenderLoader;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.server.ServerProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * The client proxy of Dawncraft Mod.
 *
 * @author QingChenW
 */
public class ClientProxy extends ServerProxy
{
    private static RenderSkill skillRender;

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ItemRenderLoader.initItemRender();
        BlockRenderLoader.initBlockRender();
        SkillRenderLoader.initSkillRender();
        EntityRenderLoader.initEntityRender();
        TileEntityRenderLoader.initTileEntityRender();
        ModelLoader.initModelLoader();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        skillRender = new RenderSkill();
        KeyLoader.initKeys();
        ClientEventLoader.initClientEvents();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
    
    public static RenderSkill getSkillRender()
    {
        return skillRender;
    }
}
