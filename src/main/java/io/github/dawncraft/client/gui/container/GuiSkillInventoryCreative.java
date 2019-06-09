package io.github.dawncraft.client.gui.container;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.creativetab.CreativeSkillTabs;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.container.ILearning;
import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.container.SkillInventoryBasic;
import io.github.dawncraft.container.SkillSlot;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.network.MessageCreativeSkillInventoryAction;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;

public class GuiSkillInventoryCreative extends GuiSkillContainer implements ILearning
{
    /** The location of the creative skill inventory tabs texture */
    private static final ResourceLocation creativeSkillInventoryTabs = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/skill_inventory/tabs.png");
    private GuiTextField searchField;
    private static int currentPage = 0;
    private int maxPages = 0;
    /** Currently selected creative inventory tab index. */
    private static int currentTab = CreativeTabsLoader.tabSkills.getIndex();
    /** True if the left mouse button was held down last time drawScreen was called. */
    private boolean wasClicking;
    /** True if the scrollbar is being dragged */
    private boolean isScrolling;
    /** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
    private float currentScroll;
    private boolean clearSearch;

    private static final SkillInventoryBasic basicSkillInventory = new SkillInventoryBasic("temp_skill_inventory", true, 45);
    /**
     * 存储上一个技能栏的slot
     */
    private List<SkillSlot> originalSlots;
    private SkillSlot destroySkillSlot;

    public GuiSkillInventoryCreative(EntityPlayer player)
    {
        super(new SkillContainerCreative(player));
        player.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        this.ySize = 136;
        this.xSize = 195;
    }

    @Override
    public void initGui()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
            this.searchField.setTextColor(0xffffff);
            this.searchField.setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            int tabCount = CreativeSkillTabs.creativeTabArray.length;
            if (tabCount > 12)
            {
                this.buttonList.add(new GuiButton(101, this.guiLeft, this.guiTop - 50, 20, 20, "<"));
                this.buttonList.add(new GuiButton(102, this.guiLeft + this.xSize - 20, this.guiTop - 50, 20, 20, ">"));
                this.maxPages = (tabCount - 2) / 10;
            }
            int i = currentTab;
            currentTab = -1;
            this.setCurrentCreativeSkillTab(CreativeSkillTabs.creativeTabArray[i]);
            IPlayerMagic playerMagic = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null);
            playerMagic.getSkillInventoryContainer().onLearnGuiOpened(this);
        }
        else
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
    
    @Override
    public void updateScreen()
    {
        if (!this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiSkillInventory(this.mc.thePlayer));
        }
    }
    
    @Override
    protected void drawGuiSkillContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        CreativeSkillTabs creativetab = CreativeSkillTabs.creativeTabArray[currentTab];
        
        int start = currentPage * 10;
        int end = Math.min(CreativeSkillTabs.creativeTabArray.length, (currentPage + 1) * 10 + 2);
        if (currentPage != 0) start += 2;
        
        for (CreativeSkillTabs creativetab2 : Arrays.copyOfRange(CreativeSkillTabs.creativeTabArray, start, end))
        {
            this.mc.getTextureManager().bindTexture(creativeSkillInventoryTabs);
            if (creativetab2 == null) continue;
            if (creativetab2.getIndex() != currentTab)
            {
                this.drawCreativeTab(creativetab2);
            }
        }
        
        if (currentPage != 0)
        {
            if (creativetab != CreativeTabsLoader.tabSearch)
            {
                this.mc.getTextureManager().bindTexture(creativeSkillInventoryTabs);
                this.drawCreativeTab(CreativeTabsLoader.tabSearch);
            }
            if (creativetab != CreativeTabsLoader.tabInventory)
            {
                this.mc.getTextureManager().bindTexture(creativeSkillInventoryTabs);
                this.drawCreativeTab(CreativeTabsLoader.tabInventory);
            }
        }
        
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Dawncraft.MODID + ":" +"textures/gui/container/skill_inventory/tab_" + creativetab.getBackgroundImageName()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.guiLeft + 175;
        int j = this.guiTop + 18;
        int k = j + 112;
        this.mc.getTextureManager().bindTexture(creativeSkillInventoryTabs);
        
        if (creativetab.shouldHideInventory())
        {
            this.drawTexturedModalRect(i, j + (k - j - 17) * this.currentScroll, 232 + (this.hasScrollBars() ? 0 : 12), 0, 12, 15);
        }
        
        if (creativetab == null || creativetab.getPage() != currentPage)
        {
            if (creativetab != CreativeTabsLoader.tabSearch && creativetab != CreativeTabsLoader.tabInventory)
            {
                return;
            }
        }
        
        this.drawCreativeTab(creativetab);
        
        if (creativetab == CreativeTabsLoader.tabInventory)
        {
            this.mc.getTextureManager().bindTexture(skillInventoryBackground);
            int x = this.guiLeft;
            int y = this.guiTop;
            this.drawTexturedModalRect(x + 12, y + 19, 176, 0, 7, 7);
            this.drawTexturedModalRect(x + 12, y + 28, 176, 7, 7, 7);
            this.drawTexturedModalRect(x + 12, y + 37, 176, 14, 7, 7);
        }
    }

    @Override
    protected void drawGuiSkillContainerForegroundLayer(int mouseX, int mouseY)
    {
        CreativeSkillTabs creativetab = CreativeSkillTabs.creativeTabArray[currentTab];

        if (creativetab != null && creativetab.shouldDrawTitle())
        {
            GlStateManager.disableBlend();
            this.fontRendererObj.drawString(I18n.format(creativetab.getTranslatedLabel()), 8, 6, 0x404040);
        }

        if (creativetab == CreativeTabsLoader.tabInventory)
        {
            EntityPlayer player = this.mc.thePlayer;
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
            GuiUtils.drawCentreString(this.fontRendererObj, player.getName(), 34, 8, 0x404040);
            this.fontRendererObj.drawString(player.experienceLevel + "", 21, 19, 0x404040);
            this.fontRendererObj.drawString(player.getHealth() + "/" + player.getMaxHealth(), 21, 28, 0x404040);
            this.fontRendererObj.drawString(playerMagic.getMana() + "/" + playerMagic.getMaxMana(), 21, 37, 0x404040);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        boolean flag = Mouse.isButtonDown(0);
        int i = this.guiLeft;
        int j = this.guiTop;
        int k = i + 175;
        int l = j + 18;
        int i1 = k + 14;
        int j1 = l + 112;

        if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
        {
            this.isScrolling = this.hasScrollBars();
        }

        if (!flag)
        {
            this.isScrolling = false;
        }

        this.wasClicking = flag;

        if (this.isScrolling)
        {
            this.currentScroll = (mouseY - l - 7.5F) / (j1 - l - 15.0F);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
            ((SkillContainerCreative) this.inventorySlots).scrollTo(this.currentScroll);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
        int start = currentPage * 10;
        int end = Math.min(CreativeSkillTabs.creativeTabArray.length, (currentPage + 1) * 10 + 2);
        if (currentPage != 0) start += 2;
        boolean rendered = false;

        for (CreativeSkillTabs creativetab : Arrays.copyOfRange(CreativeSkillTabs.creativeTabArray, start, end))
        {
            if (creativetab == null) continue;
            if (this.renderCreativeTabHoveringText(creativetab, mouseX, mouseY))
            {
                rendered = true;
                break;
            }
        }

        if (!rendered && this.renderCreativeTabHoveringText(CreativeTabsLoader.tabSearch, mouseX, mouseY))
        {
            this.renderCreativeTabHoveringText(CreativeTabsLoader.tabInventory, mouseX, mouseY);
        }

        if (this.destroySkillSlot != null && currentTab == CreativeTabsLoader.tabInventory.getIndex() && this.isPointInRegion(this.destroySkillSlot.xDisplayPosition, this.destroySkillSlot.yDisplayPosition, 16, 16, mouseX, mouseY))
        {
            this.drawCreativeTabHoveringText(I18n.format("skillInventory.binSlot"), mouseX, mouseY);
        }

        if (this.maxPages != 0)
        {
            String page = String.format("%d / %d", currentPage + 1, this.maxPages + 1);
            GlStateManager.disableLighting();
            this.zLevel = 300.0F;
            this.skillRender.zLevel = 300.0F;
            GuiUtils.drawCentreString(this.fontRendererObj, page, this.guiLeft + this.xSize / 2, this.guiTop - 44, -1);
            this.skillRender.zLevel = 0.0F;
            this.zLevel = 0.0F;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
    }
    
    protected void drawCreativeTab(CreativeSkillTabs tab)
    {
        boolean flag = tab.getIndex() == currentTab;
        boolean flag1 = tab.isOnTopRow();
        int i = tab.getColumn();
        int j = i * 28;
        int k = 0;
        int l = this.guiLeft + 28 * i;
        int i1 = this.guiTop;
        int j1 = 32;

        if (flag)
        {
            k += 32;
        }

        if (i == 5)
        {
            l = this.guiLeft + this.xSize - 28;
        }
        else if (i > 0)
        {
            l += i;
        }

        if (flag1)
        {
            i1 = i1 - 28;
        }
        else
        {
            k += 64;
            i1 = i1 + this.ySize - 4;
        }

        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        this.drawTexturedModalRect(l, i1, j, k, 28, j1);
        this.zLevel = 100.0F;
        this.skillRender.zLevel = 100.0F;
        l = l + 6;
        i1 = i1 + 8 + (flag1 ? 1 : -1);
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        if (tab.getIcon() == null)
        {
            SkillStack skillStack = tab.getIconSkillStack();
            this.skillRender.renderSkillAndEffectIntoGUI(skillStack, l, i1);
            this.skillRender.renderSkillOverlayIntoGUI(this.fontRendererObj, skillStack, l, i1);
        }
        else
        {
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.skillRender.drawSprite(l, i1, tab.getIcon(), 16, 16);
            GlStateManager.disableAlpha();
            GlStateManager.disableRescaleNormal();
        }
        GlStateManager.disableLighting();
        this.skillRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }
    
    protected boolean renderCreativeTabHoveringText(CreativeSkillTabs tab, int mouseX, int mouseY)
    {
        int i = tab.getColumn();
        int j = 28 * i;
        int k = 0;

        if (i == 5)
        {
            j = this.xSize - 28 + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (tab.isOnTopRow())
        {
            k = k - 32;
        }
        else
        {
            k = k + this.ySize;
        }

        if (this.isPointInRegion(j + 3, k + 3, 23, 27, mouseX, mouseY))
        {
            this.drawCreativeTabHoveringText(I18n.format(tab.getTranslatedLabel()), mouseX, mouseY);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    protected void renderToolTip(SkillStack stack, int x, int y)
    {
        
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = MathHelper.clamp_int(Mouse.getEventDWheel(), -1, 1);
        if (i != 0 && this.hasScrollBars())
        {
            int j = ((SkillContainerCreative) this.inventorySlots).skillList.size() / 9 - 5;
            this.currentScroll = MathHelper.clamp_float(this.currentScroll - i / j, 0.0F, 1.0F);
            ((SkillContainerCreative) this.inventorySlots).scrollTo(this.currentScroll);
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
            int i = mouseX - this.guiLeft;
            int j = mouseY - this.guiTop;

            for (CreativeSkillTabs creativetab : CreativeSkillTabs.creativeTabArray)
            {
                if (creativetab != null && this.isMouseOverTab(creativetab, i, j))
                    return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            int i = mouseX - this.guiLeft;
            int j = mouseY - this.guiTop;
            
            for (CreativeSkillTabs creativetabs : CreativeSkillTabs.creativeTabArray)
            {
                if (creativetabs != null && this.isMouseOverTab(creativetabs, i, j))
                {
                    this.setCurrentCreativeSkillTab(creativetabs);
                    return;
                }
            }
        }
        
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }

        if (button.id == 101)
        {
            currentPage = Math.max(currentPage - 1, 0);
        }
        else if (button.id == 102)
        {
            currentPage = Math.min(currentPage + 1, this.maxPages);
        }
    }

    @Override
    protected void handleClick(SkillSlot slot, int slotId, int clickedButton, int clickType)
    {
        this.clearSearch = true;
        IPlayerMagic playerMagic = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null);
        boolean move = clickType == 1;
        
        if (slot != null)
        {
            if (slot == this.destroySkillSlot && move)
            {
                for (int i = 0; i < playerMagic.getSkillInventoryContainer().getSkillStacks().size(); i++)
                {
                    this.sendSlotContents(null, i, null);
                }
            }
            else if (currentTab == CreativeTabsLoader.tabInventory.getIndex())
            {
                if (slot == this.destroySkillSlot)
                {
                    playerMagic.getSkillInventory().setSkillStack(null);
                }
                else
                {
                    playerMagic.getSkillInventoryContainer().skillSlotClick(slot == null ? slotId : ((CreativeSlot) slot).slot.slotNumber, clickedButton, clickType, this.mc.thePlayer);
                    playerMagic.getSkillInventoryContainer().detectAndSendChanges();
                }
            }
            else if (slot.inventory == basicSkillInventory)
            {
                SkillInventoryPlayer inventoryPlayer = playerMagic.getSkillInventory();
                SkillStack skillStack = inventoryPlayer.getSkillStack();
                SkillStack skillStack2 = slot.getStack();
                
                if (clickType == 2)
                {
                    if (skillStack2 != null && clickedButton >= 0 && clickedButton < 9)
                    {
                        SkillStack skillStack3 = skillStack2.copy();
                        skillStack3.skillLevel = skillStack3.getMaxLevel();
                        inventoryPlayer.setSkillInventorySlot(clickedButton, skillStack3);
                        playerMagic.getSkillInventoryContainer().detectAndSendChanges();
                    }
                    return;
                }
                else if (clickType == 3)
                {
                    if (inventoryPlayer.getSkillStack() == null && slot.hasStack())
                    {
                        SkillStack skillStack3 = skillStack2.copy();
                        skillStack3.skillLevel = skillStack3.getMaxLevel();
                        inventoryPlayer.setSkillStack(skillStack3);
                    }
                    return;
                }
                else if (skillStack2 != null && skillStack != null && skillStack.getSkill() == skillStack2.getSkill())
                {
                    if (clickedButton == 0)
                    {
                        if (move)
                        {
                            skillStack.skillLevel = skillStack.getMaxLevel();
                        }
                        else if (skillStack.skillLevel < skillStack.getMaxLevel())
                        {
                            ++skillStack.skillLevel;
                        }
                    }
                    else if (clickedButton == 1)
                    {
                        if (skillStack.skillLevel > 1)
                        {
                            --skillStack.skillLevel;
                        }
                        else
                        {
                            inventoryPlayer.setSkillStack(null);
                        }
                    }
                }
                else if (skillStack2 != null && skillStack == null)
                {
                    SkillStack skillStack3 = skillStack2.copy();
                    if (move)
                    {
                        skillStack3.skillLevel = skillStack3.getMaxLevel();
                    }
                    inventoryPlayer.setSkillStack(skillStack3);
                }
                else
                {
                    inventoryPlayer.setSkillStack(null);
                }
            }
            else
            {
                SkillContainerCreative containerCreative = (SkillContainerCreative) this.inventorySlots;

                containerCreative.skillSlotClick(slot == null ? slotId : slot.slotNumber, clickedButton, clickType, this.mc.thePlayer);
                
                if (slot != null)
                {
                    SkillStack skillStack3 = containerCreative.getSkillSlot(slot.slotNumber).getStack();
                    this.sendSlotContents(null, slot.slotNumber - containerCreative.inventorySkillSlots.size() + 9, skillStack3);
                }
            }
        }
    }
    
    protected boolean isMouseOverTab(CreativeSkillTabs tab, int mouseX, int mouseY)
    {
        if (tab.getPage() != currentPage)
        {
            if (tab != CreativeTabsLoader.tabSearch && tab != CreativeTabsLoader.tabInventory)
            {
                return false;
            }
        }

        int i = tab.getColumn();
        int j = 28 * i;
        int k = 0;

        if (i == 5)
        {
            j = this.xSize - 28 * (6 - i) + 2;
        }
        else if (i > 0)
        {
            j += i;
        }

        if (tab.isOnTopRow())
        {
            k = k - 32;
        }
        else
        {
            k = k + this.ySize;
        }

        return mouseX >= j && mouseX <= j + 28 && mouseY >= k && mouseY <= k + 32;
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (!CreativeSkillTabs.creativeTabArray[currentTab].hasSearchBar())
        {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat))
            {
                this.setCurrentCreativeSkillTab(CreativeTabsLoader.tabSearch);
            }
            else
            {
                super.keyTyped(typedChar, keyCode);
            }
        }
        else
        {
            if (this.clearSearch)
            {
                this.clearSearch = false;
                this.searchField.setText("");
            }

            if (!this.checkHotbarKeys(keyCode))
            {
                if (this.searchField.textboxKeyTyped(typedChar, keyCode))
                {
                    this.updateCreativeSearch();
                }
                else
                {
                    super.keyTyped(typedChar, keyCode);
                }
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        IPlayerMagic playerMagic = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null);
        playerMagic.getSkillInventoryContainer().removeLearner(this);
        
        Keyboard.enableRepeatEvents(false);
    }

    private void setCurrentCreativeSkillTab(CreativeSkillTabs tab)
    {
        if (tab == null) return;
        int tabIndex = currentTab;
        currentTab = tab.getIndex();
        IPlayerMagic playerMagic = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null);
        SkillContainerCreative containerCreative = (SkillContainerCreative) this.inventorySlots;
        containerCreative.skillList.clear();
        List<Skill> skillList = Lists.newArrayList();
        tab.displayAllSkills(skillList);
        for (Skill skill : skillList)
        {
            containerCreative.skillList.add(new SkillStack(skill));
        }
        
        if (tab == CreativeTabsLoader.tabInventory)
        {
            SkillContainer container = playerMagic.getSkillInventoryContainer();
            
            if (this.originalSlots == null)
            {
                this.originalSlots = containerCreative.inventorySkillSlots;
            }
            
            containerCreative.inventorySkillSlots = Lists.<SkillSlot>newArrayList();
            
            for (int i = 0; i < container.inventorySkillSlots.size(); ++i)
            {
                SkillSlot slot = new CreativeSlot(container.inventorySkillSlots.get(i), i);
                containerCreative.inventorySkillSlots.add(slot);
                
                if (i >= 0 && i < 36)
                {
                    int j = i % 9;
                    int k = i / 9;
                    slot.xDisplayPosition = 9 + j * 18;
                    
                    if (i < 9)
                    {
                        slot.yDisplayPosition = 112;
                    }
                    else
                    {
                        slot.yDisplayPosition = 36 + k * 18;
                    }
                }
                else if (i >= 36 && i <= 38)
                {
                    slot.xDisplayPosition = -2000;
                    slot.yDisplayPosition = -2000;
                }
            }
            
            this.destroySkillSlot = new SkillSlot(basicSkillInventory, 0, 173, 112);
            containerCreative.inventorySkillSlots.add(this.destroySkillSlot);
        }
        else if (tabIndex == CreativeTabsLoader.tabInventory.getIndex())
        {
            containerCreative.inventorySkillSlots = this.originalSlots;
            this.originalSlots = null;
        }
        
        if (this.searchField != null)
        {
            if (tab.hasSearchBar())
            {
                this.searchField.setText("");
                this.searchField.width = tab.getSearchbarWidth();
                this.searchField.xPosition = this.guiLeft + 82 + 89 - this.searchField.width;
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.updateCreativeSearch();
            }
            else
            {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }
        
        this.currentScroll = 0.0F;
        containerCreative.scrollTo(this.currentScroll);
    }
    
    private boolean hasScrollBars()
    {
        if (CreativeSkillTabs.creativeTabArray[currentTab] == null) return false;
        return currentTab != CreativeTabsLoader.tabInventory.getIndex() && CreativeSkillTabs.creativeTabArray[currentTab].shouldHideInventory() && ((SkillContainerCreative) this.inventorySlots).canScroll();
    }
    
    private void updateCreativeSearch()
    {
        SkillContainerCreative containerCreative = (SkillContainerCreative) this.inventorySlots;
        containerCreative.skillList.clear();
        
        CreativeSkillTabs tab = CreativeSkillTabs.creativeTabArray[currentTab];
        if (tab.hasSearchBar() && tab != CreativeTabsLoader.tabSearch)
        {
            List<Skill> skillList = Lists.newArrayList();
            tab.displayAllSkills(skillList);
            for (Skill skill : skillList)
            {
                containerCreative.skillList.add(new SkillStack(skill));
            }
            this.updateFilteredSkills(containerCreative);
            return;
        }
        
        for (Skill skill : Skill.skillRegistry)
        {
            if (skill != null && skill.getCreativeTab() != null)
            {
                containerCreative.skillList.add(new SkillStack(skill));
            }
        }
        this.updateFilteredSkills(containerCreative);
    }
    
    private void updateFilteredSkills(SkillContainerCreative containerCreative)
    {
        Iterator<SkillStack> iterator = containerCreative.skillList.iterator();
        String name = this.searchField.getText().toLowerCase();
        
        while (iterator.hasNext())
        {
            SkillStack skillStack = iterator.next();
            boolean flag = false;
            
            for (String s : skillStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips))
            {
                if (EnumChatFormatting.getTextWithoutFormattingCodes(s).toLowerCase().contains(name))
                {
                    flag = true;
                    break;
                }
            }
            
            if (!flag)
            {
                iterator.remove();
            }
        }
        
        this.currentScroll = 0.0F;
        containerCreative.scrollTo(this.currentScroll);
    }
    
    public int getSelectedTabIndex()
    {
        /** Currently selected creative skill inventory tab index. */
        return currentTab;
    }

    @SideOnly(Side.CLIENT)
    static class SkillContainerCreative extends SkillContainer
    {
        public List<SkillStack> skillList = Lists.<SkillStack>newArrayList();

        public SkillContainerCreative(EntityPlayer player)
        {
            SkillInventoryPlayer inventoryPlayer = player.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
            
            for (int i = 0; i < 5; ++i)
            {
                for (int j = 0; j < 9; ++j)
                {
                    this.addSkillSlotToContainer(new SkillSlot(basicSkillInventory, i * 9 + j, 9 + j * 18, 18 + i * 18));
                }
            }

            for (int i = 0; i < 9; ++i)
            {
                this.addSkillSlotToContainer(new SkillSlot(inventoryPlayer, i, 9 + i * 18, 112));
            }

            this.scrollTo(0.0F);
        }
        
        @Override
        public boolean canInteractWith(EntityPlayer playerIn)
        {
            return true;
        }

        public boolean canScroll()
        {
            return this.skillList.size() > 45;
        }

        public void scrollTo(float f)
        {
            int i = (this.skillList.size() + 9 - 1) / 9 - 5;
            int j = (int)(f * i + 0.5D);

            if (j < 0)
            {
                j = 0;
            }

            for (int k = 0; k < 5; ++k)
            {
                for (int l = 0; l < 9; ++l)
                {
                    int index = l + (k + j) * 9;

                    if (index >= 0 && index < this.skillList.size())
                    {
                        basicSkillInventory.setSkillInventorySlot(l + k * 9, this.skillList.get(index));
                    }
                    else
                    {
                        basicSkillInventory.setSkillInventorySlot(l + k * 9, null);
                    }
                }
            }
        }

        @Override
        public SkillStack transferSkillStackInSlot(EntityPlayer player, int index)
        {
            if (index >= this.inventorySkillSlots.size() - 9 && index < this.inventorySkillSlots.size())
            {
                SkillSlot slot = this.inventorySkillSlots.get(index);
                
                if (slot != null)
                {
                    slot.removeStack();
                }
            }
            
            return null;
        }
        
        @Override
        protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer player) {}
    }

    @SideOnly(Side.CLIENT)
    static class CreativeSlot extends SkillSlot
    {
        private final SkillSlot slot;
        
        public CreativeSlot(SkillSlot slot, int index)
        {
            super(slot.inventory, index, 0, 0);
            this.slot = slot;
        }
        
        @Override
        public int getSlotIndex()
        {
            return this.slot.getSlotIndex();
        }

        @Override
        public boolean hasStack()
        {
            return this.slot.hasStack();
        }
        
        @Override
        public SkillStack getStack()
        {
            return this.slot.getStack();
        }

        @Override
        public boolean isHere(ISkillInventory inventory, int slot)
        {
            return this.slot.isHere(inventory, slot);
        }

        @Override
        public boolean isSkillValid(SkillStack stack)
        {
            return this.slot.isSkillValid(stack);
        }
        
        @Override
        public void putStack(SkillStack stack)
        {
            this.slot.putStack(stack);
        }
        
        @Override
        public SkillStack removeStack()
        {
            return this.slot.removeStack();
        }
        
        @Override
        public boolean canTakeStack(EntityPlayer player)
        {
            return this.slot.canTakeStack(player);
        }

        @Override
        public void onPickupFromSlot(EntityPlayer player, SkillStack stack)
        {
            this.slot.onPickupFromSlot(player, stack);
        }
        
        @Override
        public void onSlotChanged()
        {
            this.slot.onSlotChanged();
        }
    }

    @Override
    public void updateLearningInventory(SkillContainer containerToSend, List<SkillStack> skillsList) {}

    @Override
    public void sendSlotContents(SkillContainer containerToSend, int slotId, SkillStack skillStack)
    {
        if (this.mc.playerController.getCurrentGameType().isCreative())
        {
            NetworkLoader.instance.sendToServer(new MessageCreativeSkillInventoryAction(slotId, skillStack));
        }
    }
}
