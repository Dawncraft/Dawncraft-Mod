package io.github.dawncraft.item;

import java.lang.reflect.Method;

import io.github.dawncraft.api.item.ISkullType;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntitySavage;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumSkullType implements ISkullType
{
    SAVAGE(EntitySavage.class),
    BARBARIAN_KING(EntityBarbarianKing.class),
    GER_KING(EntityGerKing.class, new ModelHumanoidHead());

    Class<? extends Entity> entityClass;
    @SideOnly(Side.CLIENT)
    ModelSkeletonHead entityHead;

    EnumSkullType(Class<? extends Entity> entityClass)
    {
        this(entityClass, new ModelSkeletonHead(0, 0, 64, 32));
    }

    EnumSkullType(Class<? extends Entity> entityClass, ModelSkeletonHead head)
    {
        this.entityClass = entityClass;
        this.entityHead = head;
    }

    @Override
    public Class<? extends Entity> getEntityClass()
    {
        return this.entityClass;
    }

    @Override
    public String getEntityName()
    {
        return EntityUtils.getEntityName(this.entityClass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getEntityTexure(RenderManager renderManager)
    {
        Render<? extends Entity> renderer = renderManager.getEntityClassRenderObject(this.getEntityClass());
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
        return textureResource;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelSkeletonHead getEntitySkull()
    {
        return this.entityHead;
    }

    public static final ISkullType[] VALUES;
    static
    {
        VALUES = new ISkullType[EnumSkullType.values().length];
        for (EnumSkullType type : values())
        {
            VALUES[type.ordinal()] = type;
        }
    }
}
