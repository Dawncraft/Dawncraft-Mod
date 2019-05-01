package io.github.dawncraft.client;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.EnumHelper;

public class DawnEnumHelperClient extends EnumHelper
{
    private static Class[][] clientTypes =
        {
                {RenderGameOverlayEvent.ElementType.class}
        };

    public static RenderGameOverlayEvent.ElementType addGameOverlayElementType(String name)
    {
        return addEnum(RenderGameOverlayEvent.ElementType.class, name);
    }

    public static <T extends Enum<? >> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
    {
        return addEnum(clientTypes, enumType, enumName, paramValues);
    }
}
