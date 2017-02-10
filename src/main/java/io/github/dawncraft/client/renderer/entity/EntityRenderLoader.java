package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.EntityGerKing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register entity render.
 * 
 * @author QingChenW
 */
public class EntityRenderLoader
{
    public EntityRenderLoader(FMLPreInitializationEvent event)
    {
//        registerEntityRender(EntityMouse.class, new RenderMouse(Minecraft.getMinecraft().getRenderManager()));
//        registerEntityRender(EntitySavage.class, new RenderSavage(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityGerKing.class, RenderGerKing.class);
//        registerEntityRender(EntityMagnetBall.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
//                ItemLoader.magnetBall, Minecraft.getMinecraft().getRenderItem()));
//        registerEntityRender(EntityFlanBomb.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
//                ItemLoader.flanRPGRocket, Minecraft.getMinecraft().getRenderItem()));
    }
    
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
