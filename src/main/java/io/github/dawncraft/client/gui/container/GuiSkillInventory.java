package io.github.dawncraft.client.gui.container;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.gui.GuiUtils;

public class GuiSkillInventory extends GuiSkillContainer
{
    private InventoryEffectRenderer effectRender;

    public GuiSkillInventory(EntityPlayer player)
    {
        super(player.getCapability(CapabilityLoader.playerMagic, null).getSkillInventoryContainer());
        this.allowUserInput = true;
    }
    
    @Override
    public void initGui()
    {
        this.buttonList.clear();

        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiSkillInventoryCreative(this.mc.thePlayer));
        }
        else
        {
            super.initGui();
        }
    }
    
    @Override
    public void updateScreen()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiSkillInventoryCreative(this.mc.thePlayer));
        }
    }
    
    @Override
    protected void drawGuiSkillContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(skillInventoryBackground);
        int x = this.guiLeft;
        int y = this.guiTop;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        
        this.drawTexturedModalRect(x + 12, y + 36, 176, 0, 7, 7);
        this.drawTexturedModalRect(x + 12, y + 45, 176, 7, 7, 7);
        this.drawTexturedModalRect(x + 12, y + 54, 176, 14, 7, 7);
    }
    
    @Override
    protected void drawGuiSkillContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.learning"), 68, 16, 0x404040);

        EntityPlayer player = this.mc.thePlayer;
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
        GuiUtils.drawCentreString(this.fontRendererObj, player.getName(), 34, 24, 0x404040);
        this.fontRendererObj.drawString(player.experienceLevel + "", 21, 36, 0x404040);
        this.fontRendererObj.drawString(player.getHealth() + "/" + player.getMaxHealth(), 21, 45, 0x404040);
        this.fontRendererObj.drawString(playerMagic.getMana() + "/" + playerMagic.getMaxMana(), 21, 54, 0x404040);
    }
}
