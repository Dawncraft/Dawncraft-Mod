package io.github.dawncraft.client.gui.container;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.container.ContainerMachineFurnace;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;


public class GuiMachineFurnace extends GuiContainer
{
    private static final ResourceLocation machineFurnaceGuiTextures = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/iron_furnace.png");

    private final EntityPlayer entityPlayer;
    public final TileEntityMachineFurnace tileFurnace;

    public GuiMachineFurnace(EntityPlayer player, TileEntity tileEntity)
    {
        super(new ContainerMachineFurnace(player, tileEntity));
        this.entityPlayer = player;
        this.tileFurnace = (TileEntityMachineFurnace) tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        this.fontRenderer.drawString(this.entityPlayer.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(machineFurnaceGuiTextures);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int pixels;

        if (this.tileFurnace.isWorking())
        {
            pixels = this.getElectricScaled(13);
            this.drawTexturedModalRect(offsetX + 56, offsetY + 36 + 12 - pixels, 176, 12 - pixels, 14, pixels);
        }

        pixels = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(offsetX + 79, offsetY + 34, 176, 14, pixels + 1, 16);
    }

    private int getCookProgressScaled(int pixels)
    {
        int j = this.tileFurnace.cookTime;
        int k = this.tileFurnace.totalCookTime;
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }

    private int getElectricScaled(int pixels)
    {
        int j = this.tileFurnace.electricity;

        if (j == 0)
        {
            j = 32;
        }

        return j * pixels / this.tileFurnace.Max_Electricity;
    }
}
