package io.github.dawncraft.client.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register some client events.
 *
 * @author QingChenW
 */
public class ClientEventLoader
{
    public ClientEventLoader(FMLInitializationEvent event)
    {
        register(new GuiIngameDawn(event));
        register(new InputHandler(event));
    }
    
    private static void register(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
    
    // TODO 取消buildin方块的模型加载
    @Deprecated
    public static class BakeEventHandler
    {
        private static List<Block> BuiltInBlocks = new ArrayList<Block>();
        static
        {
            addBuiltInBlock(BlockLoader.superChest);
            addBuiltInBlock(BlockLoader.skull);
        }
        
        public BakeEventHandler(FMLPreInitializationEvent event)
        {
            register(this);
        }

        /**
         * Use this event handler to get ModelLoader.
         *
         * @param event
         */
        public void onModelBake(ModelBakeEvent event)
        {
            BlockModelShapes blockModelShapes = null;
            Class modelloader = ModelLoader.class;
            for(Field field : modelloader.getDeclaredFields())
            {
                if(field.getType() == BlockModelShapes.class)
                {
                    try
                    {
                        field.setAccessible(true);
                        blockModelShapes = (BlockModelShapes) field.get(event.modelLoader);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if(blockModelShapes != null)
                blockModelShapes.getBlockStateMapper().registerBuiltInBlocks(BuiltInBlocks.toArray(new Block[0]));
        }

        /**
         * Register BuiltIn block.
         *
         * @param block
         */
        public static void addBuiltInBlock(Block block)
        {
            BuiltInBlocks.add(block);
        }
    }
}
