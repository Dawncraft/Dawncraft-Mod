package io.github.dawncraft.client.gui.container;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import io.github.dawncraft.Dawncraft;

public class GuiMagicBook extends GuiScreen
{
    private static final ResourceLocation bookGuiTextures = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/magic/magic_book.png");
    private int updateCount;
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    //    private final EntityPlayer Player;
    //    private final ItemStack bookObj;
    private GuiButton buttonDone;
    
    public GuiMagicBook()//EntityPlayer player, ItemStack book)
    {
        /*        this.Player = player;
        this.bookObj = book;

        if (book.hasTagCompound())
        {
            NBTTagCompound nbttagcompound = book.getTagCompound();
        }*/
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        ++this.updateCount;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
        
        this.updateButtons();
    }

    @Override
    public void onGuiClosed()
    {
        
    }
    
    private void updateButtons()
    {
        
    }

    private void sendBookToServer(boolean publish) throws IOException
    {
        
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                this.mc.displayGuiScreen((GuiScreen)null);
                this.sendBookToServer(false);
            }
            this.updateButtons();
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(bookGuiTextures);
        int k = (this.width - this.bookImageWidth) / 2;
        byte b0 = 2;
        this.drawTexturedModalRect(k, b0, 0, 0, this.bookImageWidth, this.bookImageHeight);
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.magic.future"), this.width / 2, this.height / 2, 0x404040);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        
    }
}
