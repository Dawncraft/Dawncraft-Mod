package io.github.dawncraft.client;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.EnumHelper;

public class DawnEnumHelperClient extends EnumHelper
{
    private static Class<?>[][] clientTypes =
        {
                {EnumParticleTypes.class, String.class, int.class, boolean.class},
                {RenderGameOverlayEvent.ElementType.class}
        };

    public static EnumParticleTypes addEnumParticleType(String name, String particleName, int particleID, boolean shouldIgnoreRange)
    {
        return addEnum(EnumParticleTypes.class, name, particleName, particleID, shouldIgnoreRange);
    }

    public static RenderGameOverlayEvent.ElementType addGameOverlayElementType(String name)
    {
        return addEnum(RenderGameOverlayEvent.ElementType.class, name);
    }

    public static <T extends Enum<? >> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
    {
        return addEnum(clientTypes, enumType, enumName, paramValues);
    }
}
