package io.github.dawncraft.client.gui.container;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;

public class GuiSkillInventory extends GuiSkillContainer
{
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
    protected void drawGuiSkillContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(skillInventoryBackground);
        int x = this.guiLeft;
        int y = this.guiTop;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiSkillContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.skillInventory"), 12, 16, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.learning"), 86, 16, 4210752);
        EntityPlayer player = this.mc.thePlayer;
        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
        this.fontRendererObj.drawString(I18n.format("container.magic.name", player.getName()), 12, 32, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.exp", player.experienceLevel), 12, 45, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.health", player.getHealth(), player.getMaxHealth()), 12, 54, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.mana", playerMagic.getMana(), playerMagic.getMaxMana()), 12, 63, 4210752);
    }
}
