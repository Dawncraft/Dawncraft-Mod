package io.github.dawncraft.client.gui.container;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.container.ContainerMachineFurnace;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 *
 *
 * @author QingChenW
 */
public class GuiMachineFurnace extends GuiContainer
{
    private static final ResourceLocation MACHINE_FURNACE_GUI_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/machine_furnace.png");
    private final EntityPlayer entityPlayer;
    private final TileEntityMachineFurnace tileFurnace;

    public GuiMachineFurnace(EntityPlayer player, TileEntity tileEntity)
    {
        super(new ContainerMachineFurnace(player, tileEntity));
        this.entityPlayer = player;
        this.tileFurnace = (TileEntityMachineFurnace) tileEntity;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        this.mc.getTextureManager().bindTexture(MACHINE_FURNACE_GUI_TEXTURE);
        int x = (this.width - this.xSize) / 2, y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (this.tileFurnace.isWorking())
        {
            int pixels = this.getEnergyScaled(13);
            this.drawTexturedModalRect(x + 56, y + 36 + 12 - pixels, 176, 12 - pixels, 14, pixels);
        }

        int pixels = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(x + 79, y + 34, 176, 14, pixels + 1, 16);
    }

    private int getCookProgressScaled(int pixels)
    {
        int current = this.tileFurnace.cookTime;
        int total = this.tileFurnace.totalCookTime;
        return total != 0 && current != 0 ? current * pixels / total : 0;
    }

    private int getEnergyScaled(int pixels)
    {
        int energy = this.tileFurnace.getEnergyStored();

        if (energy == 0)
        {
            energy = 32;
        }

        return energy * pixels / this.tileFurnace.getMaxEnergyStored();
    }
}
