package io.github.dawncraft.client;

import io.github.dawncraft.client.event.ClientEventLoader;
import io.github.dawncraft.client.renderer.block.BlockRenderLoader;
import io.github.dawncraft.client.renderer.entity.EntityRenderLoader;
import io.github.dawncraft.client.renderer.item.ItemRenderLoader;
import io.github.dawncraft.client.renderer.skill.MagicRenderLoader;
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
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        new ItemRenderLoader(event);
        new BlockRenderLoader(event);
        new EntityRenderLoader(event);
        new TileEntityRenderLoader(event);
        new MagicRenderLoader(event);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        new KeyLoader(event);
        new ClientEventLoader(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        
        super.postInit(event);
    }
}
