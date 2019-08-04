package io.github.dawncraft.client.gui.container;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.container.ContainerEnergyGenerator;
import io.github.dawncraft.tileentity.TileEntityEnergyGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 *
 * @author QingChenW
 */
@SideOnly(Side.CLIENT)
public class GuiEnergyGenerator extends GuiContainer
{
    private static final ResourceLocation HEAT_GENERATOR_GUI_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/heat_generator.png");

    private final EntityPlayer entityPlayer;
    private final TileEntityEnergyGenerator tileGenerator;

    public GuiEnergyGenerator(EntityPlayer player, TileEntity tileEntity)
    {
        super(new ContainerEnergyGenerator(player, tileEntity));
        this.entityPlayer = player;
        this.tileGenerator = (TileEntityEnergyGenerator) tileEntity;
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
        String name = this.tileGenerator.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        this.fontRenderer.drawString(this.entityPlayer.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 0x404040);

        this.fontRenderer.drawStringWithShadow(this.tileGenerator.getEnergyStored() + "J/12800J", 104, 64, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(HEAT_GENERATOR_GUI_TEXTURE);
        int x = (this.width - this.xSize) / 2, y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if (this.tileGenerator.isWorking())
        {
            int pixels = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(x + 55, y + 29 + 12 - pixels, 176, 12 - pixels, 14, pixels + 1);
        }

        int pixels = this.getElectricScaled(32);
        this.drawTexturedModalRect(x + 105, y + 30 + 31 - pixels, 176, 14 + 31 - pixels, 14, pixels + 1);
    }

    private int getBurnLeftScaled(int pixels)
    {
        int maxBurnTime = this.tileGenerator.currentItemBurnTime;

        if (maxBurnTime == 0)
        {
            maxBurnTime = 200;
        }

        return this.tileGenerator.generatorBurnTime * pixels / maxBurnTime;
    }

    private int getElectricScaled(int pixels)
    {
        int current = this.tileGenerator.getEnergyStored();
        int max = this.tileGenerator.getMaxEnergyStored();
        if (current > max)
        {
            current = max;
        }
        return max != 0 && current != 0 ? current * pixels / max : 0;
    }
}
