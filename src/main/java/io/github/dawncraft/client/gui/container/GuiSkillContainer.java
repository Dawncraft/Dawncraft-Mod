package io.github.dawncraft.client.gui.container;

import java.io.IOException;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.client.renderer.skill.RenderSkill;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.container.SkillSlot;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.network.MessageClickSkillWindow;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.SkillStack;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class GuiSkillContainer extends GuiContainer
{
    /** The location of the skill inventory background texture */
    protected static final ResourceLocation skillInventoryBackground = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/skill_inventory/skill_inventory.png");
    /** Holds a instance of RenderSkill, used to draw the achievement icons on screen (is based on SkillStack) */
    protected RenderSkill skillRender;
    /** A list of the players inventory slots */
    public SkillContainer skillContainer;
    /** holds the slot currently hovered */
    private SkillSlot theSlot;

    public GuiSkillContainer(SkillContainer container)
    {
        super(container);
        this.skillContainer = container;

        this.skillRender = ClientProxy.getInstance().getSkillRender();
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        SkillStack skillStack = inventoryPlayer.getSkillStack();
        
        if (skillStack == null && this.theSlot != null && this.theSlot.hasStack())
        {
            SkillStack skillStack2 = this.theSlot.getStack();
            GuiUtils.renderSkillToolTip(this, skillStack2, mouseX, mouseY);
        }
        
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.drawGuiSkillContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    protected abstract void drawGuiSkillContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY);

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int left = this.guiLeft;
        int top = this.guiTop;

        RenderHelper.enableGUIStandardItemLighting();

        this.theSlot = null;
        for (int i = 0; i < this.skillContainer.inventorySkillSlots.size(); ++i)
        {
            SkillSlot slot = this.skillContainer.inventorySkillSlots.get(i);
            this.drawSkillSlot(slot);

            if (this.isMouseOverSkillSlot(slot, mouseX, mouseY) && slot.canBeHovered())
            {
                this.theSlot = slot;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                int x = slot.xDisplayPosition;
                int y = slot.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(x, y, x + 16, y + 16, -0x7f000001, -0x7f000001);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
        
        RenderHelper.disableStandardItemLighting();
        this.drawGuiSkillContainerForegroundLayer(mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();

        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        SkillStack skillStack = inventoryPlayer.getSkillStack();

        if (skillStack != null)
        {
            this.drawSkillStack(skillStack, mouseX - left - 8, mouseY - top - 8);
        }
    }
    
    protected void drawGuiSkillContainerForegroundLayer(int mouseX, int mouseY) {}

    private boolean isMouseOverSkillSlot(SkillSlot slot, int mouseX, int mouseY)
    {
        return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
    }

    private void drawSkillSlot(SkillSlot slot)
    {
        int x = slot.xDisplayPosition;
        int y = slot.yDisplayPosition;
        SkillStack skillStack = slot.getStack();
        
        this.zLevel = 100.0F;
        this.skillRender.zLevel = 100.0F;
        
        if (skillStack == null)
        {
            TextureAtlasSprite textureatlassprite = slot.getBackgroundSprite();
            
            if (textureatlassprite != null)
            {
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(slot.getBackgroundLocation());
                this.drawTexturedModalRect(x, y, textureatlassprite, 16, 16);
                GlStateManager.enableLighting();
            }
        }
        else
        {
            GlStateManager.enableDepth();
            this.skillRender.renderSkillAndEffectIntoGUI(skillStack, x, y);
            this.skillRender.renderSkillOverlayIntoGUI(this.fontRendererObj, skillStack, x, y);
        }
        
        this.skillRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    private void drawSkillStack(SkillStack stack, int x, int y)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.skillRender.zLevel = 200.0F;
        this.skillRender.renderSkillAndEffectIntoGUI(stack, x, y);
        this.skillRender.renderSkillOverlayIntoGUI(this.fontRendererObj, stack, x, y);
        this.zLevel = 0.0F;
        this.skillRender.zLevel = 0.0F;
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        if (inventoryPlayer.getSkillStack() == null)
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        
        if (this.mc.thePlayer.inventory.getItemStack() == null)
        {
            SkillSlot slot = this.getSkillSlotAtPosition(mouseX, mouseY);
            if (mouseButton == 0 || mouseButton == 1 || mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
            {
                int left = this.guiLeft;
                int top = this.guiTop;
                int slotId = -1;
                
                if (slot != null)
                {
                    slotId = slot.slotNumber;
                }
                
                if (slotId != -1)
                {
                    if (mouseButton == 0 || mouseButton == 1)
                    {
                        this.handleClick(slot, slotId, mouseButton, !isShiftKeyDown() ? 0 : 1);
                    }
                    else
                    {
                        this.handleClick(slot, slotId, mouseButton, 3);
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick)
    {
        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        if (inventoryPlayer.getSkillStack() == null)
        {
            super.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastClick);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
    {
        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        if (inventoryPlayer.getSkillStack() == null)
        {
            super.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    private SkillSlot getSkillSlotAtPosition(int x, int y)
    {
        for (int i = 0; i < this.skillContainer.inventorySkillSlots.size(); ++i)
        {
            SkillSlot slot = (SkillSlot) this.skillContainer.inventorySkillSlots.get(i);
            if (this.isMouseOverSkillSlot(slot, x, y))
            {
                return slot;
            }
        }
        return null;
    }

    public SkillSlot getSkillSlotUnderMouse()
    {
        return this.theSlot;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        
        if (this.theSlot != null && this.theSlot.hasStack())
        {
            if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode())
            {
                this.handleClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
            }
        }
    }

    @Override
    protected boolean checkHotbarKeys(int keyCode)
    {
        boolean flag = super.checkHotbarKeys(keyCode);
        
        SkillInventoryPlayer inventoryPlayer = this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
        if (inventoryPlayer.getSkillStack() == null && this.theSlot != null)
        {
            for (int i = 0; i < 9; ++i)
            {
                if (keyCode == this.mc.gameSettings.keyBindsHotbar[i].getKeyCode())
                {
                    this.handleClick(this.theSlot, this.theSlot.slotNumber, i, 2);
                    return true;
                }
            }
        }
        
        return flag;
    }
    
    protected void handleClick(SkillSlot slot, int slotId, int clickedButton, int clickType)
    {
        if (slot != null)
        {
            slotId = slot.slotNumber;
        }

        this.windowClick(this.skillContainer.windowId, slotId, clickedButton, clickType, this.mc.thePlayer);
    }

    public SkillStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer player)
    {
        short uid = player.openContainer.getNextTransactionID(player.inventory);
        SkillStack skillStack = ((SkillContainer) player.openContainer).skillSlotClick(slotId, mouseButtonClicked, mode, player);
        NetworkLoader.instance.sendToServer(new MessageClickSkillWindow(windowId, slotId, mouseButtonClicked, mode, skillStack, uid));
        return skillStack;
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
