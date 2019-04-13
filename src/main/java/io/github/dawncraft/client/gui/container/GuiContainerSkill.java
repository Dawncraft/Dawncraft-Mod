package io.github.dawncraft.client.gui.container;

import java.io.IOException;

import io.github.dawncraft.Dawncraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiContainerSkill extends GuiScreen
{
    protected static final ResourceLocation skillInventoryBackground = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/inventory/skill.png");
    protected int xSize = 176;
    protected int ySize = 166;
    protected int guiLeft;
    protected int guiTop;
    
    public GuiContainerSkill()
    {
        // TODO 魔法GUI容器客户端
    }
    
    @Override
    public void initGui()
    {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            this.mc.thePlayer.closeScreen();
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (this.mc.thePlayer != null)
        {
            // 未实现
        }
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
