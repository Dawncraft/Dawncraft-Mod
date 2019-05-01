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

public interface IPlayerMagic extends IEntityMana, ILearning
{
    public ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "magic");
    
    public EnumSpellAction getSpellAction();
    
    void setSpellAction(EnumSpellAction action);
    
    public SkillStack getSkillInSpell();
    
    public boolean setSkillInSpell(SkillStack stack);
    
    public void clearSkillInSpell();

    public void stopSpellingSkill();
    
    public void sendCancelSpellReason(IChatComponent reason, boolean useActionBar);
    
    public int getSkillInSpellCount();

    public int getSkillInSpellDuration();

    public void setSkillInSpellCount(int count);
    
    public SpellCooldownTracker getCooldownTracker();
    
    public SkillInventoryPlayer getSkillInventory();

    public SkillContainer getSkillInventoryContainer();

    public int getTalentLevel(Talent talent);

    public void setTalent(Talent talent, int level);

    /**
     * 现在先这么凑合着,天赋栏啥的以后再说
     */
    @Deprecated
    public Set<Talent> getTalents();

    public void sendOverlayMessage(IChatComponent chatComponent);

    public void sendActionBarMessage(IChatComponent chatComponent, EnumChatFormatting foregroundColor);

    public void update();

    public void cloneCapability(IPlayerMagic oldMagic, boolean wasDeath);
}
