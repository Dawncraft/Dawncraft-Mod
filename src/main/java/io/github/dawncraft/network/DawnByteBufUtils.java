package io.github.dawncraft.network;

import com.google.common.base.Throwables;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * 低仿 {@link ByteBufUtils} (大雾)
 *
 * @author QingChenW
 */
public class DawnByteBufUtils
{
    /**
     * Write an {@link SkillStack} using dawncraft compatible encoding.
     * <br>Write the SkillStack's ID (short), then level (int), then cooldown (int).</br>
     *
     * @param to The buffer to write to
     * @param stack The skillstack to write
     */
    public static void writeSkillStack(ByteBuf to, SkillStack stack)
    {
        PacketBuffer pb = new PacketBuffer(to);

        if (stack == null)
        {
            pb.writeShort(-1);
        }
        else
        {
            pb.writeShort(Skill.getIdFromSkill(stack.getSkill()));
            pb.writeInt(stack.getSkillLevel());
        }
    }
    
    /**
     * Read an {@link SkillStack} from the byte buffer provided. It uses the dawncraft encoding.
     *
     * @param from The buffer to read from
     * @return The skillstack read
     */
    public static SkillStack readSkillStack(ByteBuf from)
    {
        PacketBuffer pb = new PacketBuffer(from);
        SkillStack stack = null;
        try
        {
            int id = pb.readShort();
            
            if (id >= 0)
            {
                int level = pb.readInt();
                int cooldown = pb.readInt();
                stack = new SkillStack(Skill.getSkillById(id), level);
            }
        }
        catch (Exception e)
        {
            // Still unpossible?
            // 好吧,读NBT的时候有可能抛IOException异常,但是目前技能系统还没有NBT...
            throw Throwables.propagate(e);
        }
        return stack;
    }
}
