package io.github.dawncraft.capability;

import java.util.Set;

import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.entity.player.SpellCooldownTracker;
import io.github.dawncraft.skill.EnumSpellResult;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.talent.Talent;

public interface IMagic
{
    public float getMana();
    
    public float getMaxMana();
    
    public void setMana(float amount);

    public boolean shouldRecover();
    
    public void recover(float recoverAmount);
    
    public void reduce(float reduceAmount);
    
    public void replenish();
    
    public EnumSpellResult getSpellAction();
    
    void setSpellAction(EnumSpellResult action);
    
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

    // 因为冷却是倒计时,不需要再减一下了
    public int getPublicCooldownCount();

    public void setPublicCooldownCount(int count);
    
    public SpellCooldownTracker getCooldownTracker();
    
    public void setCooldownTracker(SpellCooldownTracker tracker);
    
    public ISkillInventory getInventory();
    
    public void setInventory(ISkillInventory inventory);

    public int getTalentLevel(Talent talent);

    public void setTalent(Talent talent, int level);

    /**
     * 现在先这么凑合着,天赋栏啥的以后再说
     */
    @Deprecated
    public Set<Talent> getTalents();

    public void update();
}
