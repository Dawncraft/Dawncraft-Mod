package io.github.dawncraft.client.renderer.tileentity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.tileentity.TileEntitySkull;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TileEntityRenderLoader
{
    public TileEntityRenderLoader(FMLPreInitializationEvent event)
    {
        TileEntityRenderSkull.addSkullTexture("savage", new ResourceLocation(Dawncraft.MODID + ":" + "textures/entity/savage.png"));
        TileEntityRenderSkull.addSkullTexture("barbarianking", new ResourceLocation(Dawncraft.MODID + ":" + "textures/entity/barbarian_king.png"));
        TileEntityRenderSkull.addSkullTexture("gerking", new ResourceLocation(Dawncraft.MODID + ":" + "textures/entity/ger_king.png"));
        LogLoader.logger().info("测试: 头颅渲染注册完成!");

        registerTileEntityRender(TileEntitySkull.class, new TileEntityRenderSkull());
    }

    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
}
