package io.github.dawncraft.capability;

import java.util.Set;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.container.ILearning;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.talent.Talent;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public interface IPlayerMagic extends IEntityMana, ILearning, ICapabilityClonable<IPlayerMagic>
{
    public ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "magic");
    
    public SkillInventoryPlayer getSkillInventory();

    public SkillContainer getSkillInventoryContainer();

    public int getTalentLevel(Talent talent);

    public void setTalent(Talent talent, int level);
    
    @Deprecated
    public Set<Talent> getTalents();
    
    public SpellCooldownTracker getCooldownTracker();
    
    public EnumSpellAction getSpellAction();
    
    void setSpellAction(EnumSpellAction action);
    
    public SkillStack getSkillInSpell();

    public void setSkillInSpell(EnumSpellAction action, SkillStack stack, int duration);
    
    public void clearSkillInSpell();

    public boolean initSkillInSpell(SkillStack stack);

    public void cancelSpellingSkill();
    
    public int getSkillInSpellCount();

    public int getSkillInSpellDuration();

    public void setSkillInSpellCount(int count);
    
    public void update();
    
    public void updateHeldSkill();

    public void sendCancelSpellReason(IChatComponent reason, boolean useActionBar);

    public void sendOverlayMessage(IChatComponent chatComponent);

    public void sendActionBarMessage(IChatComponent chatComponent, EnumChatFormatting foregroundColor);
}
