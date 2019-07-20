package io.github.dawncraft.util;

import java.lang.reflect.Field;

import net.minecraft.command.CommandResultStats;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 *
   Field field = ReflectionHelper.findField(class, mcpname, srgname);
   EnumHelper.setFailsafeFieldValue(field, this, value);
 *
 * @author QingChenW
 */
public class DawnEnumHelper extends EnumHelper
{
    private static Class<?>[][] newTypes =
        {
                {CommandResultStats.Type.class, int.class, String.class},
                {HoverEvent.Action.class, String.class, boolean.class}
        };

    public static CommandResultStats.Type addCommandResultType(String name, String typeName)
    {
        try
        {
            int id = CommandResultStats.Type.values().length;
            Field field = ReflectionHelper.findField(CommandResultStats.class, "NUM_RESULT_TYPES", "field_179676_a");
            EnumHelper.setFailsafeFieldValue(field, null, id + 1);
            Field field2 = ReflectionHelper.findField(CommandResultStats.class, "STRING_RESULT_TYPES", "field_179674_b");
            EnumHelper.setFailsafeFieldValue(field2, null, new String[field.getInt(null)]);
            return addEnum(CommandResultStats.Type.class, name, id, typeName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static HoverEvent.Action addHoverActionType(String name, String canonicalName, boolean allowedInChat)
    {
        return addEnum(HoverEvent.Action.class, name, canonicalName, allowedInChat);
    }

    public static <T extends Enum<? >> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
    {
        return addEnum(newTypes, enumType, enumName, paramValues);
    }
}
