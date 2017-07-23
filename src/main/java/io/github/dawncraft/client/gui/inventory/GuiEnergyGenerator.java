package io.github.dawncraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.container.ContainerEnergyGenerator;
import io.github.dawncraft.tileentity.TileEntityEnergyGenerator;

/**
 *
 * @author QingChenW
 */
@SideOnly(Side.CLIENT)
public class GuiEnergyGenerator extends GuiContainer
{
    private static final ResourceLocation heatGeneratorGuiTextures = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/container/heat_generator.png");

    private final EntityPlayer entityPlayer;
    public final TileEntityEnergyGenerator tileGenerator;
    
    public GuiEnergyGenerator(EntityPlayer player, TileEntity tileEntity)
    {
        super(new ContainerEnergyGenerator(player, tileEntity));
        this.entityPlayer = player;
        this.tileGenerator = (TileEntityEnergyGenerator) tileEntity;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = this.tileGenerator.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.entityPlayer.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        
        this.fontRendererObj.drawStringWithShadow(this.tileGenerator.electricity + "A/12800A", 104, 64, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(heatGeneratorGuiTextures);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        
        int pixels;

        if (this.tileGenerator.isWorking())
        {
            pixels = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(offsetX + 55, offsetY + 29 + 12 - pixels, 176, 12 - pixels, 14, pixels + 1);
        }

        pixels = this.getElectricScaled(32);
        this.drawTexturedModalRect(offsetX + 105, offsetY + 30 + 31 - pixels, 176, 14 + 31 - pixels, 14, pixels + 1);
    }

    private int getBurnLeftScaled(int pixels)
    {
        int j = this.tileGenerator.currentItemBurnTime;
        
        if (j == 0)
        {
            j = 200;
        }
        
        return this.tileGenerator.generatorBurnTime * pixels / j;
    }
    
    private int getElectricScaled(int pixels)
    {
        int j = this.tileGenerator.electricity;
        int k = this.tileGenerator.Max_Electricity;
        if (j > k)
        {
            j = k;
        }
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }
}
