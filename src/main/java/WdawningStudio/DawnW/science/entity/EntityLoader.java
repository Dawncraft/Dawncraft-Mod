package WdawningStudio.DawnW.science.entity;

import WdawningStudio.DawnW.science.science;
import WdawningStudio.DawnW.science.item.ItemLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader
{
    public static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
        EntityRegistry.registerModEntity(entityClass, name, entityID, science.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
    {
        EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
    }
    
    
    private static void registerEntitySpawn(Class<? extends Entity> entityClass, int spawnWeight, int min,
            int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
    {
        if (EntityLiving.class.isAssignableFrom(entityClass))
        {
            Class<? extends EntityLiving> entityLivingClass = entityClass.asSubclass(EntityLiving.class);
            EntityRegistry.addSpawn(entityLivingClass, spawnWeight, min, max, typeOfCreature, biomes);
        }
    }
    
    public EntityLoader()
    {
        registerEntity(EntityMouse.class, "Mouse", 64, 3, true);
        registerEntity(EntitySavage.class, "Savage", 64, 3, true);
        registerEntity(EntityGerKing.class, "GerKing", 64, 3, true);
        registerEntity(EntityMagnetBall.class, "MagnetBall", 64, 10, true);
        registerEntity(EntityFlanBomb.class, "FlanBomb", 64, 10, true);
        registerEntityEgg(EntityMouse.class, 0x5b0f00, 0x573131);
        registerEntityEgg(EntitySavage.class, 0x795949, 0x513830);
        registerEntityEgg(EntityGerKing.class, 0x795949, 0x800000);
        registerEntitySpawn(EntitySavage.class, 40, 2, 6, EnumCreatureType.CREATURE, BiomeGenBase.plains);
    }
  
    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerEntityRender(EntityMouse.class, new RenderMouse(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntitySavage.class, new RenderSavage(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityGerKing.class, new RenderGerKing(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityMagnetBall.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
        		ItemLoader.magnetBall, Minecraft.getMinecraft().getRenderItem()));
        registerEntityRender(EntityFlanBomb.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(),
        		ItemLoader.flanRPGRocket, Minecraft.getMinecraft().getRenderItem()));
    }

    @SideOnly(Side.CLIENT)
    private static void registerEntityRender(Class<? extends Entity> entityClass, Render renderer)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
    }
}