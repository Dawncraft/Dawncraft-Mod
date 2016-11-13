package com.github.wdawning.dawncraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.github.wdawning.dawncraft.entity.EntityFlanBomb;
import com.github.wdawning.dawncraft.entity.EntityGerKing;
import com.github.wdawning.dawncraft.entity.EntityMagnetBall;
import com.github.wdawning.dawncraft.entity.EntityMouse;
import com.github.wdawning.dawncraft.entity.EntitySavage;
import com.github.wdawning.dawncraft.item.ItemLoader;

public class EntityRenderLoader
{
    public EntityRenderLoader()
    {
        registerEntityRender(EntityMouse.class, new RenderMouse(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntitySavage.class, new RenderSavage(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityGerKing.class, new RenderGerKing(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityMagnetBall.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
                ItemLoader.magnetBall, Minecraft.getMinecraft().getRenderItem()));
        registerEntityRender(EntityFlanBomb.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
                ItemLoader.flanRPGRocket, Minecraft.getMinecraft().getRenderItem()));
    }
    
    private static void registerEntityRender(Class<? extends Entity> entityClass, Render renderer)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
    }
}
