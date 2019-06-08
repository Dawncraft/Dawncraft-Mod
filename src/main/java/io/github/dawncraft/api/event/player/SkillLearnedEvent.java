package io.github.dawncraft.api.event.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.skill.SkillStack;

public class SkillLearnedEvent extends Event
{
    public final EntityPlayer player;
    public final SkillStack learning;
    public final ISkillInventory learnMatrix;
    public SkillLearnedEvent(EntityPlayer player, SkillStack learning, ISkillInventory learnMatrix)
    {
        this.player = player;
        this.learning = learning;
        this.learnMatrix = learnMatrix;
    }
}
