package io.github.dawncraft.network;

import com.google.common.base.Throwables;

import java.io.IOException;

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
     * <br>Write the SkillStack's ID (short), then level (byte).</br>
     *
     * @param to The buffer to write to
     * @param stack The skill stack to write
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
            pb.writeByte(stack.getSkillLevel());
            pb.writeNBTTagCompoundToBuffer(stack.getTagCompound());
        }
    }

    /**
     * Read an {@link SkillStack} from the byte buffer provided. It uses the dawncraft encoding.
     *
     * @param from The buffer to read from
     * @return The skill stack to read
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
                int level = pb.readByte();
                stack = new SkillStack(Skill.getSkillById(id), level);
                stack.setTagCompound(pb.readNBTTagCompoundFromBuffer());
            }
        }
        catch (IOException e)
        {
            // Still impossible?
            throw Throwables.propagate(e);
        }
        return stack;
    }
}
