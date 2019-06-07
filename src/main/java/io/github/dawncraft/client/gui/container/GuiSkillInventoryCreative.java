package io.github.dawncraft.client.gui.container;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import com.google.common.collect.Lists;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.skill.SkillStack;

public class GuiSkillInventoryCreative extends GuiSkillContainer
{
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
        
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType)
    {
        super.handleMouseClick(slotIn, slotId, clickedButton, clickType);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        Keyboard.enableRepeatEvents(false);
    }

    static class SkillContainerCreative extends SkillContainer
    {
        public List<SkillStack> skillList = Lists.<SkillStack>newArrayList();

        public SkillContainerCreative(EntityPlayer player)
        {
            InventoryPlayer inventoryPlayer = player.inventory;

            this.scrollTo(0.0F);
        }
        
        @Override
        public boolean canInteractWith(EntityPlayer playerIn)
        {
            return true;
        }

        public void scrollTo(float f)
        {
            
        }
    }
}
