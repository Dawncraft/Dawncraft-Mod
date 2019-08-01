package io.github.dawncraft.client.gui;

import java.lang.reflect.Field;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ItemGun;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.DawnEnumHelperClient;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.potion.PotionInit;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * The ingame gui patch of Dawncraft Mod.
 * <br>In fact, it is a render event, not a gui.</br>
 *
 * @author QingChenW
 */
public class GuiIngameDawn extends Gui
{
    public static final int WHITE = 0xFFFFFF;
    public static final ResourceLocation ICONS = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/icons.png");
    public static final ResourceLocation WIDGETS = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/widgets.png");

    public static final ElementType SIGHT = DawnEnumHelperClient.addGameOverlayElementType("SIGHT");
    public static final ElementType SKILLHOTBAR = DawnEnumHelperClient.addGameOverlayElementType("SKILLHOTBAR");
    public static final ElementType ACTIONBAR = DawnEnumHelperClient.addGameOverlayElementType("ACTIONBAR");
    public static final ElementType MANA = DawnEnumHelperClient.addGameOverlayElementType("MANA");
    public static final ElementType DRINK = DawnEnumHelperClient.addGameOverlayElementType("DRINK");

    // Whether render the optical sight
    public static boolean renderSight = true;
    public static boolean renderSkillHotbar = true;
    public static boolean renderActionBar = true;
    public static boolean renderWeaponTip = true;
    public static boolean renderMana = true;
    public static boolean renderDrink = false;

    protected final Random rand = new Random();
    protected final Minecraft mc;
    protected GuiIngameForge ingameGUIForge;

    /** Whether player is in spelling mode */
    public boolean spellMode = false;
    /** An animation timer for switching spell mode */
    public int modeTimer;
    /** The index of the SkillStack that is currently being highlighted */
    public int skillIndex = -1;

    /** The content of the action bar */
    public String actionName = "";
    /** The foreground color of the action bar */
    public int actionForegroundColor;
    /** The time that the action bar is displayed */
    public int actionDisplayTime;
    /** The current tick of the action */
    public int actionTickCurrent;
    /** The max tick of the action */
    public int actionTickMax;

    /** Player's mana point */
    public int playerMana = 0;
    /** Player's last mana point */
    public int lastPlayerMana = 0;
    /** Used with updateCounter to make the star bar flash */
    public long manaUpdateCounter = 0L;
    /** The last recorded system time used for mana counter */
    protected long lastSystemTime = 0L;

    public GuiIngameDawn()
    {
        this.mc = Minecraft.getMinecraft();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        if (event.phase == Phase.START)
        {
            this.mc.profiler.startSection("gui");
            if (!this.mc.isGamePaused())
            {
                this.updateTick();
            }
            this.mc.profiler.endSection();
        }
    }

    @SubscribeEvent
    public void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event)
    {
        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        float partialTicks = event.getPartialTicks();

        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if (!this.mc.playerController.isSpectator())
            {
                if (event.getType() == ElementType.CROSSHAIRS)
                {
                    if (renderSight) this.renderSight(event.getResolution(), partialTicks);
                }
                if (event.getType() == ElementType.HOTBAR)
                {
                    if (renderSkillHotbar && this.spellMode)
                    {
                        event.setCanceled(true);
                        this.renderSkillHotbar(width, height, partialTicks);
                    }
                }
            }
            if (this.mc.playerController.shouldDrawHUD())
            {
                if (event.getType() == ElementType.HEALTH)
                {
                    if (renderDrink) this.renderDrink(width, height);
                }
                if (event.getType() == ElementType.FOOD)
                {
                    if (renderMana) this.renderMana(width, height);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPostRenderGameOverlay(RenderGameOverlayEvent.Post event)
    {
        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        float partialTicks = event.getPartialTicks();

        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            if (event.getType() == ElementType.TEXT)
            {
                if (renderActionBar) this.renderActionBar(width, height, partialTicks);
                if (renderWeaponTip) this.renderWeaponTip(width, height);
            }
        }
    }

    @SubscribeEvent
    public void onRenderGameText(RenderGameOverlayEvent.Text event)
    {
        if (event.getLeft().isEmpty())
        {
            EntityPlayer player = Minecraft.getMinecraft().player;
            event.getLeft().add(0, String.format("Welcome to play Dawncraft Mod, %s!", player.getName()));
            event.getLeft().add(1, String.format("Dawncraft Mod's version is %s!", Dawncraft.VERSION));
            event.getLeft().add(2, String.format("These words will be removed in the future!"));
        }
    }

    protected void updateTick()
    {
        if (this.modeTimer > 0)
        {
            --this.modeTimer;
        }

        if (this.actionDisplayTime > 0)
        {
            --this.actionDisplayTime;
        }

        if (this.mc.player != null)
        {
            EntityPlayer player = this.mc.player;
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);

            if (player.isHandActive())
            {
                EnumAction action = player.getActiveItemStack().getItemUseAction();
                if (action == EnumAction.EAT)
                {
                    this.setActionMessage(I18n.format("gui.item.eat", player.getActiveItemStack().getDisplayName()), this.getIngameGUI().getFontRenderer().getColorCode('a'));
                }
                else if (action == EnumAction.DRINK)
                {
                    this.setActionMessage(I18n.format("gui.item.drink", player.getActiveItemStack().getDisplayName()), this.getIngameGUI().getFontRenderer().getColorCode('a'));
                }
                else if (action == EnumAction.BOW)
                {
                    this.setActionMessage(I18n.format("gui.item.bow", player.getActiveItemStack().getDisplayName()), this.getIngameGUI().getFontRenderer().getColorCode('a'));
                }
                else if (action == ItemInit.RELOAD)
                {
                    this.setActionMessage(I18n.format("gui.item.reload", player.getActiveItemStack().getDisplayName()), this.getIngameGUI().getFontRenderer().getColorCode('a'));
                }
                else if (action == ItemInit.SHOOT)
                {
                    this.setActionMessage(I18n.format("gui.item.shoot", player.getActiveItemStack().getDisplayName()), this.getIngameGUI().getFontRenderer().getColorCode('a'));
                }
            }
            else
            {
                EnumSpellAction action = playerMagic.getSpellAction();

                if (action != EnumSpellAction.NONE)
                {
                    this.setActionTick(playerMagic.getSkillInSpellDuration());
                }
            }
        }
    }

    public void switchMode()
    {
        this.spellMode = !this.spellMode;
        this.modeTimer = 20;
    }

    public void setSpellIndex(int index)
    {
        this.skillIndex = index;
    }

    public void setActionMessage(String message, int color)
    {
        this.setAction(message, 0, color);
    }

    public void setActionMessage(ITextComponent textComponent, int color)
    {
        this.setAction(textComponent.getUnformattedText(), 0, color);
    }

    public void setAction(String name, int max, int color)
    {
        this.actionName = name;
        this.actionTickMax = max;
        this.actionForegroundColor = color;
        this.setActionTick(0);
    }

    public void setActionTick(int current)
    {
        this.actionTickCurrent = current;
        this.actionDisplayTime = 60;
    }

    public float getActionProgress(float partialTicks)
    {
        float progress = this.actionTickMax > 0 ? (this.actionTickCurrent + partialTicks) / this.actionTickMax : 1.0F;
        return MathHelper.clamp(progress, 0.0F, 1.0F);
    }

    protected void renderSight(ScaledResolution resolution, float partialTicks)
    {
        if (this.pre(SIGHT)) return;
        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof ItemGun)
        {
            ItemGun item = (ItemGun) player.getActiveItemStack().getItem();

            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            item.renderSightOverlay(player.getActiveItemStack(), player, resolution, partialTicks);
            GlStateManager.disableBlend();
        }
        this.post(SIGHT);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bind(Gui.ICONS);
    }

    protected void renderSkillHotbar(int width, int height, float partialTicks)
    {
        this.bind(WIDGETS);
        if (this.pre(SKILLHOTBAR)) return;
        this.mc.profiler.startSection("skillBar");

        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);

        int left = width / 2 - 91;
        int top = height - 22;

        this.drawTexturedModalRect(left, top, 0, 0, 182, 22);
        if (this.skillIndex >= 0 && this.skillIndex < SkillInventoryPlayer.getHotbarSize())
        {
            this.drawTexturedModalRect(left - 1 + this.skillIndex * 20, top - 1, 0, 22, 24, 24);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        float cooldown = playerMagic.getCooldownTracker().getGlobalCooldownPercent(partialTicks);

        for (int i = 0; i < SkillInventoryPlayer.getHotbarSize(); ++i)
        {
            int x = left + 3 + i * 20;
            int y = top + 3;
            this.renderHotbarSkill(i, x, y, partialTicks, player);
            if (cooldown > 0.0F)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.colorMask(true, true, true, false);
                GuiUtils.drawRect(x, y + MathHelper.floor(16.0F * (1.0F - cooldown)), 16, MathHelper.ceil(16.0F * cooldown), 255, 255, 255, 191);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();

        this.mc.profiler.endSection();
        this.post(SKILLHOTBAR);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bind(Gui.ICONS);
    }

    protected void renderHotbarSkill(int index, int xPos, int yPos, float partialTicks, EntityPlayer player)
    {
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        SkillStack skillStack = playerMagic.getSkillInventory().getSkillStackInSlot(index);

        if (skillStack != null)
        {
            float f = skillStack.animationsToGo - partialTicks;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate(xPos + 8, yPos + 12, 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
            }

            ClientProxy.getInstance().getSkillRender().renderSkillIntoGUI(skillStack, xPos, yPos);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            ClientProxy.getInstance().getSkillRender().renderSkillOverlayIntoGUI(this.getIngameGUI().getFontRenderer(), skillStack, xPos, yPos);
        }
    }

    protected void renderMana(int width, int height)
    {
        this.bind(ICONS);
        if (this.pre(MANA)) return;
        this.mc.profiler.startSection("mana");
        GlStateManager.enableBlend();

        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        int mana = MathHelper.ceil(playerMagic.getMana());
        boolean highlight = this.manaUpdateCounter > this.getIngameGUI().getUpdateCounter() && (this.manaUpdateCounter - this.getIngameGUI().getUpdateCounter()) / 3L % 2L == 1L;

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

        float maxMana = playerMagic.getMaxMana();
        int manaRows = MathHelper.ceil(maxMana / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (manaRows - 2), 3);

        this.rand.setSeed(this.getIngameGUI().getUpdateCounter() * 312871);

        int left = width / 2 + 91;
        int top = height - getRightHeight();
        addRightHeight(manaRows * rowHeight);
        if (rowHeight < 10) addRightHeight(10 - rowHeight);

        final int BG_U = highlight ? 9 : 0;
        final int V = 9 + 9;
        int U = 0;
        if (player.isPotionActive(PotionInit.SILENT)) U += 36;

        int recover = player.isPotionActive(PotionInit.RECOVER) ? this.getIngameGUI().getUpdateCounter() % 25 : -1;

        for (int i = MathHelper.ceil(maxMana / 2.0F) - 1; i >= 0; --i)
        {
            int row = MathHelper.ceil((i + 1) / 10.0F) - 1;
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
        this.mc.profiler.endSection();
        this.post(MANA);
        this.bind(Gui.ICONS);
    }

    public void renderDrink(int width, int height)
    {
        this.bind(ICONS);
        if (this.pre(DRINK)) return;
        this.mc.profiler.startSection("drink");

        this.mc.profiler.endSection();
        this.post(DRINK);
        this.bind(Gui.ICONS);
    }

    protected void renderActionBar(int width, int height, float partialTicks)
    {
        if (this.actionDisplayTime > 0)
        {
            this.bind(WIDGETS);
            if (this.pre(ACTIONBAR)) return;
            this.mc.profiler.startSection("actionBar");

            float progress = this.getActionProgress(partialTicks);
            float ticks = this.actionDisplayTime - partialTicks;
            int opacity = (int) (ticks * 256.0F / 20.0F);
            if (opacity > 255) opacity = 255;

            if (opacity > 0)
            {
                int x = width / 2;
                int y = height - 59;
                if (!this.mc.playerController.shouldDrawHUD()) y += 14;

                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                this.drawTexturedModalRect(x - 53, y - 5, 0, 46, 106, 18);
                GlStateManager.disableTexture2D();
                GuiUtils.drawRectWithRGB(x - 50, y - 2, (int) (100 * progress), 12, this.actionForegroundColor);
                GlStateManager.enableTexture2D();
                this.drawCenteredString(this.getIngameGUI().getFontRenderer(), this.actionName, x, y, WHITE | opacity << 24);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.profiler.endSection();
            this.post(ACTIONBAR);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.bind(Gui.ICONS);
        }
    }

    protected void renderWeaponTip(int width, int height)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();

        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemGun)
        {
            ItemGun item = (ItemGun) player.getHeldItemMainhand().getItem();
            int amount = item.getAmmoAmount(player.getHeldItemMainhand());
            String text = (amount <= 0 ? TextFormatting.RED : "") + I18n.format("gui.gun.ammo", amount, item.getClip());
            this.drawString(this.getIngameGUI().getFontRenderer(), text, width - this.getIngameGUI().getFontRenderer().getStringWidth(text) - 2, height - 12, WHITE);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bind(Gui.ICONS);
    }

    public void bind(ResourceLocation resource)
    {
        this.mc.getTextureManager().bindTexture(resource);
    }

    public RenderGameOverlayEvent getParentEvent()
    {
        try
        {
            Field field = ReflectionHelper.findField(GuiIngameForge.class, "eventParent");
            return (RenderGameOverlayEvent) field.get(this.getIngameGUI());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean pre(ElementType type)
    {
        return MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(this.getParentEvent(), type));
    }

    public void post(ElementType type)
    {
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(this.getParentEvent(), type));
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
