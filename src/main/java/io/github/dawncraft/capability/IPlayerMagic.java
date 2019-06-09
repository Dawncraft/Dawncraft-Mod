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

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public interface IPlayerMagic extends IEntityMana, ILearning, ICapabilityClonable<IPlayerMagic>
{
    ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "magic");

    SkillInventoryPlayer getSkillInventory();
    
    SkillContainer getSkillInventoryContainer();
    
    int getTalentLevel(Talent talent);
    
    void setTalent(Talent talent, int level);

    @Deprecated Set<Talent> getTalents();

    SpellCooldownTracker getCooldownTracker();

    EnumSpellAction getSpellAction();

    void setSpellAction(EnumSpellAction action);

    SkillStack getSkillInSpell();
    
    void setSkillInSpell(EnumSpellAction action, SkillStack skillStack, int duration);

    void clearSkillInSpell();
    
    boolean initSkillInSpell(SkillStack skillStack);
    
    void cancelSpellingSkill();

    int getSkillInSpellCount();
    
    int getSkillInSpellDuration();
    
    void setSkillInSpellCount(int count);

    void update();

    void updateHeldSkill();
    
    void sendCancelSpellReason(IChatComponent reason, boolean useActionbar);
    
    void sendOverlayMessage(IChatComponent chatComponent);
    
    void sendActionBarMessage(IChatComponent chatComponent);
}
