package io.github.dawncraft.client.gui;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemGun;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.potion.PotionLoader;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The ingame gui patch of Dawncraft Mod.
 * <br>In fact, it is a render event, not a gui.</br>
 *
 * @author QingChenW
 */
public class GuiIngameDawn extends Gui
{
    public static final int WHITE = 0xFFFFFF;
    public static final ResourceLocation icons = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/icons.png");
    public static final ResourceLocation widgets = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/widgets.png");

    public static boolean renderMana = true;
    public static boolean renderSkillbar = true;
    // If render the sight
    public static boolean renderSight = true;
    
    protected final Random rand = new Random();
    protected final Minecraft mc;
    protected GuiIngameForge ingameGUIForge;
    
    // 玩家魔法的缓存(用于判断玩家是否消耗了魔法)
    public int playerMana = 0;
    public int lastPlayerMana = 0;
    // 魔法条动画更新计时器
    public long manaUpdateCounter = 0L;
    // 是否处于施法模式
    public boolean spellMode = false;
    // 施法状态的缓存(用于判断玩家是否改变了施法状态)
    public EnumSpellAction spellAction = EnumSpellAction.NONE;
    // 当前选中的技能索引
    public int skillIndex;
    // 当前选中的技能
    public SkillStack highlightSkillStack;
    // 当前施法时间
    public int spellingTicks;
    // 施法进度条和选中框或是冷却指示器之类的消逝计时器
    public int remainingTicks;
    // 系统时间缓存
    private long lastSystemTime = 0L;

    public GuiIngameDawn()
    {
        this.mc = Minecraft.getMinecraft();
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void PreGUIRender(RenderGameOverlayEvent.Pre event)
    {
        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();

        if (event.type == ElementType.ALL)
        {
            if (!this.mc.isGamePaused())
            {
                this.updateTick();
            }
        }

        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if (!this.mc.playerController.isSpectator())
            {
                this.bind(widgets);
                
                if (event.type == ElementType.HOTBAR)
                {
                    if (renderSkillbar && this.spellMode)
                    {
                        event.setCanceled(true);
                        this.renderSkillbar(width, height);
                    }
                }
            }
            if (this.mc.playerController.shouldDrawHUD())
            {
                this.bind(icons);
                
                if (event.type == ElementType.FOOD)
                {
                    if (renderMana) this.renderMana(width, height);
                }
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bind(super.icons);
    }

    @SubscribeEvent
    public void PostGUIRender(RenderGameOverlayEvent.Post event)
    {
        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();

        if(this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if(event.type == ElementType.ALL)
            {
                this.renderSkillProgress(width, height);
            }
            if(event.type == ElementType.CROSSHAIRS)
            {
                if(renderSight) this.renderSight(event.resolution);
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bind(super.icons);
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
        EntityPlayer player = this.mc.thePlayer;
        IPlayerMagic playerMagic = null;
        if(player.hasCapability(CapabilityLoader.playerMagic, null))
        {
            playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
        }
        
        EnumSpellAction action = playerMagic.getSpellAction();
        
        if(this.spellAction != action)
        {
            this.spellAction = playerMagic.getSpellAction();
            this.remainingTicks = 40;
        }
        
        if(this.spellAction != EnumSpellAction.NONE)
        {
            this.highlightSkillStack = playerMagic.getSkillInSpell();
            this.spellingTicks = playerMagic.getSkillInSpellCount();
        }
        
        if(this.spellAction != EnumSpellAction.NONE && this.spellingTicks <= 0)
        {
            if(this.remainingTicks > 0) --this.remainingTicks;

            if(this.remainingTicks <= 0)
            {
                playerMagic.clearSkillInSpell();
                this.spellAction = playerMagic.getSpellAction();
            }
        }
    }

    public void changeMode()
    {
        this.spellMode = !this.spellMode;
    }

    public void setSpellIndex(int index)
    {
        this.skillIndex = index;
    }
    
    protected void renderMana(int width, int height)
    {
        this.mc.mcProfiler.startSection("mana");
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        
        EntityPlayer player = (EntityPlayer)this.mc.getRenderViewEntity();
        if(!player.hasCapability(CapabilityLoader.playerMagic, null)) return;
        
        boolean highlight = this.manaUpdateCounter > this.getIngameGUI().getUpdateCounter() && (this.manaUpdateCounter - this.getIngameGUI().getUpdateCounter()) / 3L % 2L == 1L;
        int recover = player.isPotionActive(PotionLoader.potionRecover) ? this.getIngameGUI().getUpdateCounter() % 25 : -1;
        
        float manaMax = (float) player.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue();
        int manaRows = MathHelper.ceiling_float_int(manaMax / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (manaRows - 2), 3);
        
        int mana = MathHelper.ceiling_float_int(player.getCapability(CapabilityLoader.playerMagic, null).getMana());
        if (mana < this.playerMana)
        {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.manaUpdateCounter = this.getIngameGUI().getUpdateCounter() + 20;
        }
        else if (mana > this.playerMana)
        {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.manaUpdateCounter = this.getIngameGUI().getUpdateCounter() + 10;
        }
        if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L)
        {
            this.playerMana = mana;
            this.lastPlayerMana = mana;
            this.lastSystemTime = Minecraft.getSystemTime();
        }
        this.playerMana = mana;

        this.rand.setSeed(this.getIngameGUI().getUpdateCounter() * 312871);
        
        int left = width / 2 + 91;
        int top = height - getRightHeight();
        addRightHeight(manaRows * rowHeight);
        if (rowHeight < 10) addRightHeight(10 - rowHeight);

        final int BG_U = highlight ? 9 : 0;
        final int V = 9 + (ConfigLoader.manaRenderType ? 9 : 0);
        int U = 0;
        if (player.isPotionActive(PotionLoader.potionSilent)) U += 36;
        
        for (int i = MathHelper.ceiling_float_int(manaMax / 2.0F) - 1; i >= 0; --i)
        {
            int row = MathHelper.ceiling_float_int((i + 1) / 10.0F) - 1;
            int x = left - i % 10 * 8 - 9;
            int y = top - row * rowHeight;
            
            if (mana <= 2) y += this.rand.nextInt(2);
            if (i == recover) y -= 2;
            
            this.drawTexturedModalRect(x, y, BG_U, V, 9, 9);
            
            if (highlight)
            {
                if (i * 2 + 1 < this.lastPlayerMana)
                    this.drawTexturedModalRect(x, y, U + 54, V, 9, 9);
                else if (i * 2 + 1 == this.lastPlayerMana)
                    this.drawTexturedModalRect(x, y, U + 54 + 9, V, 9, 9);
            }
            
            if (i * 2 + 1 < mana)
                this.drawTexturedModalRect(x, y, U + 36, V, 9, 9);
            else if (i * 2 + 1 == mana)
                this.drawTexturedModalRect(x, y, U + 36 + 9, V, 9, 9);
        }
        
        GlStateManager.disableBlend();
        this.mc.mcProfiler.endSection();
    }
    
    protected void renderTalentExperience(int width, int height)
    {
        
    }
    
    protected void renderSkillbar(int width, int height)
    {
        this.mc.mcProfiler.startSection("skillBar");
        
        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        
        int left = width / 2 - 91;
        int top = height - 22;
        
        this.drawTexturedModalRect(left, top, 0, 0, 182, 22);

        if(this.spellAction != EnumSpellAction.NONE && this.skillIndex >= 0 && this.skillIndex < SkillInventoryPlayer.getHotbarSize())
        {
            this.drawTexturedModalRect(left - 1 + this.skillIndex * 20, top - 1, 0, 22, 24, 24);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        
        float cooldown = player.getCapability(CapabilityLoader.playerMagic, null).getCooldownTracker().getGlobalCooldownPercent(0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrender = tessellator.getWorldRenderer();

        for (int i = 0; i < SkillInventoryPlayer.getHotbarSize(); ++i)
        {
            int x = left + 3 + i * 20;
            int y = top + 3;
            this.renderHotbarSkill(i, x, y, player);
            if (cooldown > 0.0F)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                ClientProxy.getSkillRender().draw(worldrender, x, y + MathHelper.floor_float(16.0F * (1.0F - cooldown)), 16, MathHelper.ceiling_float_int(16.0F * cooldown), 191, 191, 191, 63);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                
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
        if (player.hasCapability(CapabilityLoader.playerMagic, null))
        {
            IPlayerMagic inventoryCap = player.getCapability(CapabilityLoader.playerMagic, null);
            inventory = (SkillInventoryPlayer) inventoryCap.getSkillInventory();
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
            
            ClientProxy.getSkillRender().renderSkillIntoGUI(skillstack, xPos, yPos);
            
            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            ClientProxy.getSkillRender().renderSkillOverlayIntoGUI(this.mc.fontRendererObj, skillstack, xPos, yPos);
        }
    }

    protected void renderSkillProgress(int width, int height)
    {
        if (!this.mc.playerController.isSpectator())
        {
            this.mc.mcProfiler.startSection("skillHighlight");

            if (this.spellAction != EnumSpellAction.NONE && this.remainingTicks > 0 && this.highlightSkillStack != null)
            {
                String text = I18n.format(this.spellAction.getUnlocalizedName(), this.highlightSkillStack.getDisplayName());
                boolean failed = false;
                float progress = 1.0F;
                if(this.spellAction == EnumSpellAction.PREPARE)
                {
                    if(this.highlightSkillStack.getTotalPrepare() > 0)
                        progress = (this.highlightSkillStack.getTotalPrepare() - this.spellingTicks) / this.highlightSkillStack.getTotalPrepare();
                }
                else if(this.spellAction == EnumSpellAction.SPELL)
                {
                    if(this.highlightSkillStack.getMaxDuration() > 0)
                        progress = (this.highlightSkillStack.getMaxDuration() - this.spellingTicks) / this.highlightSkillStack.getMaxDuration();
                }
                else if(this.isSpellFailed())
                {
                    failed = true;
                }
                else return;

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

                    this.bind(widgets);
                    this.drawTexturedModalRect(x - 53, y - 5, 0, 46, 106, 18);
                    this.drawTexturedModalRect(x - 50, y - 2, 0, 64 + (failed ? 12 : 0), (int) (x - 100 + 100 * progress), 12);

                    this.drawCenteredString(this.mc.fontRendererObj, text, x, y, WHITE | opacity << 24);
                    
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }

            this.mc.mcProfiler.endSection();
        }
    }

    private boolean isSpellFailed()
    {
        // TODO 自动生成的方法存根
        return false;
    }
    
    protected void renderSight(ScaledResolution resolution)
    {
        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();

        if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGun)
        {
            ItemGun item = (ItemGun) player.getHeldItem().getItem();
            int amount = item.getAmmoAmount(player.getHeldItem());
            String text = (amount <= 0 ? EnumChatFormatting.RED : "") + I18n.format("gui.gun.ammo", amount, item.getClip());
            this.drawString(this.mc.fontRendererObj, text, resolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(text) - 2,
                    resolution.getScaledHeight() - 12, WHITE);
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if(player.isUsingItem() && player.getItemInUse().getItem() instanceof ItemGun)
        {
            ItemGun item = (ItemGun) player.getItemInUse().getItem();
            item.renderSightOverlay(player.getItemInUse(), player, resolution, player.getItemInUseDuration());
        }
    }

    public void bind(ResourceLocation resource)
    {
        this.mc.getTextureManager().bindTexture(resource);
    }
    
    public GuiIngameForge getIngameGUI()
    {
        if (this.ingameGUIForge == null) this.ingameGUIForge = (GuiIngameForge) this.mc.ingameGUI;
        return this.ingameGUIForge;
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
}
