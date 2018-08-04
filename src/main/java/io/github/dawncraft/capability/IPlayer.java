package io.github.dawncraft.capability;

import java.util.Set;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.talent.Talent;
import net.minecraft.util.ResourceLocation;

public interface IPlayer extends IMana
{
    public ResourceLocation domain = new ResourceLocation(Dawncraft.MODID + ":" + "player");

    public EnumSpellAction getSpellAction();

    void setSpellAction(EnumSpellAction action);

    boolean isCanceled();

    void cancelSpelling();

    void setCanceled(boolean isCanceled);
    
    public int getSpellIndex();

    public void setSpellIndex(int index);

    public SkillStack getSkillInSpell();

    public void setSkillInSpell(SkillStack stack);

    public void clearSkillInSpell();

    public int getSkillInSpellCount();
    
    public int getSkillInSpellDuration();
    
    public void setSkillInSpellCount(int count);

    public SpellCooldownTracker getCooldownTracker();

    public SkillInventoryPlayer getInventory();
    
    public int getTalentLevel(Talent talent);
    
    public void setTalent(Talent talent, int level);
    
    /**
     * 现在先这么凑合着,天赋栏啥的以后再说
     */
    @Deprecated
    public Set<Talent> getTalents();
    
    public void update();
}
