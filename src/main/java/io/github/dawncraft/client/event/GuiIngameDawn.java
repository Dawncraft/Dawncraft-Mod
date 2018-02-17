package io.github.dawncraft.client.event;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.client.renderer.skill.SkillRenderer;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.skill.EnumSpellResult;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The ingame gui patch of Dawncraft Mod.
 * <br>In fact, it is a render event, not a gui.</br>
 *
 * @author QingChenW
 */
public class GuiIngameDawn extends Gui
{
    protected static final int BLACK = 0x000000;
    protected static final int WHITE = 0xFFFFFF;
    protected static final ResourceLocation widgets = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/widgets.png");
    
    public static GuiIngameDawn ingameDawnGUI;

    public static boolean renderMana = true;
    public static boolean renderSkillbar = true;
    public static boolean renderSight = true; // 是否渲染瞄准镜，原谅我吧，我已经不知道该用啥词了

    protected final Minecraft mc;
    protected final Random rand = new Random();

    // 是否处于施法模式
    public boolean spellMode = false;
    // 当前状态
    public EnumSpellResult spellAction = EnumSpellResult.NONE;
    // 当前选中的技能索引
    public int skillIndex;
    // 当前选中的技能
    public SkillStack highlightSkillStack;
    // 当前施法时间
    public int spellingTicks;
    // 当前全局冷却时间
    public int cooldownTicks;
    // 施法进度条和选中框或是冷却指示器之类的消逝计时器
    public int remainingTicks;
    
    public GuiIngameDawn(FMLInitializationEvent event)
    {
        this.mc = Minecraft.getMinecraft();
        ingameDawnGUI = this;
    }
    
    @SubscribeEvent
    public void PreGUIRender(RenderGameOverlayEvent.Pre event)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(widgets);
        GlStateManager.enableAlpha();
        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();
        
        if(this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if(event.type == ElementType.ALL)
            {
                if (!this.mc.isGamePaused())
                {
                    this.updateTick();
                }
            }
            
            if(this.mc.playerController.shouldDrawHUD())
            {
                if(event.type == ElementType.FOOD)
                {
                    if(renderMana) this.renderMana(width, height);
                }
            }
            if(renderSkillbar)
            {
                if(!this.mc.playerController.isSpectator())
                {
                    if(event.type == ElementType.HOTBAR)
                    {
                        if(this.spellMode)
                        {
                            event.setCanceled(true);
                            this.renderSkillbar(width, height);
                        }
                        this.renderSkillProgress(width, height);
                    }
                }
            }
            if(event.type == ElementType.HELMET)
            {
                if(renderSight) this.renderSight(width, height);
            }
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(super.icons);
        GlStateManager.enableAlpha();
    }

    @SubscribeEvent
    public void TextRender(RenderGameOverlayEvent.Text event)
    {
        if(event.left.isEmpty())
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            event.left.add(0, String.format("Welcome to play Dawncraft Mod, %s!", player.getName()));
            event.left.add(1, String.format("Dawncraft Mod's version is %s!", Dawncraft.VERSION));
            event.left.add(2, String.format("This word will remove in the future!"));
        }
    }

    protected void updateTick()
    {
        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        IMagic magic = null;
        if(player.hasCapability(CapabilityLoader.magic, null))
        {
            magic = player.getCapability(CapabilityLoader.magic, null);
        }

        EnumSpellResult action = magic.getSpellAction();

        if(this.spellAction != action)
        {
            this.spellAction = magic.getSpellAction();
            this.remainingTicks = 40;
        }

        if(this.spellAction == EnumSpellResult.SELECT || this.spellAction.isSpelling())
        {
            this.skillIndex = magic.getSpellIndex();
            this.highlightSkillStack = magic.getSkillInSpell();
            this.spellingTicks = magic.getSkillInSpellDuration();
        }
        else
        {
            if(this.remainingTicks > 0)
            {
                --this.remainingTicks;
                if(this.remainingTicks <= 0)
                {
                    magic.setSpellAction(EnumSpellResult.NONE);
                    this.spellAction = magic.getSpellAction();
                }
            }
        }

        this.cooldownTicks = magic.getPublicCooldownCount();
    }
    
    protected void changeMode()
    {
        this.spellMode = !this.spellMode;
    }

    protected void renderMana(int width, int height)
    {
        this.mc.mcProfiler.startSection("mana");
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();

        int left = width / 2 + 91;
        int top = height - getRightHeight();
        addRightHeight(10);
        
        int mana = 20;
        EntityPlayer player = (EntityPlayer)this.mc.getRenderViewEntity();
        if(player.hasCapability(CapabilityLoader.magic, null))
        {
            mana = MathHelper.ceiling_float_int(player.getCapability(CapabilityLoader.magic, null).getMana());
        }
        
        int i, x, y;
        int u = !ConfigLoader.manaRenderType ? 22 : 22 + 9;
        for (i = 0; i < 10; ++i)
        {
            x = left - i * 8 - 9;
            y = top;

            this.drawTexturedModalRect(x, y, 0, u, 9, 9);

            if (i * 2 + 1 < mana)
            {
                this.drawTexturedModalRect(x, y, 9, u, 9, 9);
            }

            if (i * 2 + 1 == mana)
            {
                this.drawTexturedModalRect(x, y, 17, u, 9, 9);
            }
        }

        GlStateManager.disableBlend();
        this.mc.mcProfiler.endSection();
    }
    
    protected void renderSkillbar(int width, int height)
    {
        this.mc.mcProfiler.startSection("skillBar");

        EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();

        int left = width / 2 - 91;
        int top = height - 22;

        this.drawTexturedModalRect(left, top, 0, 0, 182, 22);
        
        if(this.spellAction != EnumSpellResult.NONE && this.skillIndex >= 0 && this.skillIndex < SkillInventoryPlayer.getHotbarSize())
        {
            this.drawTexturedModalRect(left - 1 + this.skillIndex * 20, top - 1, 182, 0, 24, 22);
        }
        
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        float cooldown = this.cooldownTicks / Skill.getPublicCooldown();
        
        for (int i = 0; i < 9; ++i)
        {
            int x = left + 3 + i * 20;
            int y = top + 3;
            this.renderHotbarSkill(i, x, y, entityplayer);
            if (cooldown > 0.0F)
            {
                this.drawRect(x, y + MathHelper.floor_float(16.0F * (1.0F - cooldown)), 16, MathHelper.ceiling_float_int(16.0F * cooldown), 0x7fffffff);
            }
        }
        
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        
        this.mc.mcProfiler.endSection();
    }
    
    protected void renderHotbarSkill(int index, int xPos, int yPos, EntityPlayer player)
    {
        SkillInventoryPlayer inventory = null;
        if(player.hasCapability(CapabilityLoader.magic, null))
        {
            IMagic inventoryCap = player.getCapability(CapabilityLoader.magic, null);
            inventory = (SkillInventoryPlayer) inventoryCap.getInventory();
        }
        SkillStack skillstack = inventory != null ? inventory.getStackInSlot(index) : null;

        if (skillstack != null)
        {
            float f = (float) skillstack.animationsToGo - 1;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate(xPos + 8, yPos + 12, 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
            }

            SkillRenderer.skillRender.renderSkillIntoGUI(skillstack, xPos, yPos);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }
            
            SkillRenderer.skillRender.renderSkillOverlayIntoGUI(this.mc.fontRendererObj, skillstack, xPos, yPos, null);
        }
    }
    
    protected void renderSkillProgress(int width, int height)
    {
        if (!this.mc.playerController.isSpectator())
        {
            this.mc.mcProfiler.startSection("skillHighlight");
            
            if (this.spellAction != EnumSpellResult.NONE && this.remainingTicks > 0 && this.highlightSkillStack != null)
            {
                String text = I18n.format(this.spellAction.getUnlocalizedName(), this.highlightSkillStack.getDisplayName());
                float progress = 1.0F;
                int color = 0x77FF0000; // Red
                if(this.spellAction == EnumSpellResult.PREPARING)
                {
                    progress = (this.highlightSkillStack.getTotalPrepare() - this.spellingTicks) / this.highlightSkillStack.getTotalPrepare();
                    color = 0x7700FF00; // Green
                }
                else if(this.spellAction == EnumSpellResult.SPELLING)
                {
                    progress = (this.highlightSkillStack.getMaxDuration() - this.spellingTicks) / this.highlightSkillStack.getMaxDuration();
                    if(progress <= 0.0F) progress = 1.0F;
                    color = 0x7700FF00; // Green
                }
                else if(!this.spellAction.isSpellFailed()) return;
                
                int opacity = (int)(this.remainingTicks * 256.0F / 10.0F);
                if (opacity > 255) opacity = 255;
                
                if (opacity > 0)
                {
                    int x = width / 2;
                    int y = height - 59;
                    if (!this.mc.playerController.shouldDrawHUD()) y += 14;
                    
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    this.drawRect(x - 102, y, x + 102, y - 12, BLACK);
                    this.drawRect(x - 100, y - 2, (int) (x - 100 + 200 * progress), y - 10, BLACK);
                    this.drawCenteredString(this.mc.fontRendererObj, text, x, y, WHITE | opacity << 24);
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
            
            this.mc.mcProfiler.endSection();
        }
    }
    
    protected void renderSight(int width, int height)
    {
        EntityPlayer player = (EntityPlayer)this.mc.getRenderViewEntity();
        if(player.isUsingItem() && player.getItemInUse().getItem() == ItemLoader.gunRPG)
        {
            this.drawTexturedModalRect(width / 2 - 20, height / 2 - 20, 214, 0, 42, 42);
        }
    }

    public static void addLeftHeight(int height)
    {
        GuiIngameForge.left_height += height;
    }
    
    public static int getLeftHeight()
    {
        return GuiIngameForge.left_height;
    }

    public static void addRightHeight(int height)
    {
        GuiIngameForge.right_height += height;
    }

    public static int getRightHeight()
    {
        return GuiIngameForge.right_height;
    }

    public static GuiIngameDawn getIngameDawnGUI()
    {
        return ingameDawnGUI;
    }
}
