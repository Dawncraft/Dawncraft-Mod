package io.github.dawncraft.api.event.player;

import java.util.List;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillTooltipEvent extends PlayerEvent
{
    /**
     * Whether the advanced information on skill tooltips is being shown, toggled by F3+H.
     */
    public final ITooltipFlag flags;
    /**
     * The {@link SkillStack} with the tooltip.
     */
    public final SkillStack skillStack;
    /**
     * The {@link SkillStack} tooltip.
     */
    public final List<String> toolTip;

    /**
     * This event is fired in {@link SkillStack#getTooltip(EntityPlayer, boolean)}, which in turn is called from it's respective GUIContainer.
     */
    public SkillTooltipEvent(SkillStack skillStack, EntityPlayer player, List<String> toolTip, ITooltipFlag flags)
    {
        super(player);
        this.skillStack = skillStack;
        this.toolTip = toolTip;
        this.flags = flags;
    }
}
