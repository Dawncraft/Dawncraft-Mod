package io.github.dawncraft.util;

import net.minecraft.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;

/**
 *
   Field field = ReflectionHelper.findField(class, mcpname, srgname);
   EnumHelper.setFailsafeFieldValue(field, this, value);
 *
 * @author QingChenW
 */
public class DawnEnumHelper extends EnumHelper
{
    private static Class[][] newTypes =
        {
                {HoverEvent.Action.class, String.class, boolean.class}
        };
    
    public static HoverEvent.Action addHoverActionType(String name, String canonicalName, boolean allowedInChat)
    {
        return addEnum(HoverEvent.Action.class, name, canonicalName, allowedInChat);
    }
    
    public static <T extends Enum<? >> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
    {
        return addEnum(newTypes, enumType, enumName, paramValues);
    }
}
