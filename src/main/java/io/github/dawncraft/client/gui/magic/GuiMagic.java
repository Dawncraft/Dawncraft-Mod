package io.github.dawncraft.client.gui.magic;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMagic extends GuiContainer
{
    private static final ResourceLocation creativeSkillTab = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/magic/tab_skill.png");
    private static int tabPage = 0;
    private float oldMouseX;
    private float oldMouseY;

    public GuiMagic(EntityPlayer player)
    {
        super();
        this.allowUserInput = true;
    }
    
    @Override
    public void initGui()
    {
        this.buttonList.clear();

        if (this.mc.playerController.isInCreativeMode())
        {
            this.xSize = 176 + 78/* + 38*/;// 312
        }

        super.initGui();
    }

    @Override
    public void updateScreen()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            this.xSize = 176 + 78/* + 38*/;// 312
        }
        else
        {
            this.xSize = 176;
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.oldMouseX = mouseX;
        this.oldMouseY = mouseY;
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        int x = this.guiLeft, y = this.guiTop, offset = 0;
        if(this.mc.playerController.isInCreativeMode())
        {
            // 绘制创造魔法栏
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(creativeSkillTab);
            // 绘制创造魔法栏标签(疑似有问题,禁用)
            /*
            int start = tabPage * 4 + 1;
            int end = Math.min(CreativeSkillTabs.creativeTabArray.length + 1, (tabPage + 1) * 4 + 1);
            CreativeSkillTabs[] tabs = Arrays.copyOfRange(CreativeSkillTabs.creativeTabArray, start, end);
            for (int i = 0; i < tabs.length; i++)
            {
                if (tabs[i] == null) continue;
                this.drawTexturedModalRect(x + 4, y + 17 + i * 24, 0, 166, 34, 23);
                this.drawString(this.fontRendererObj, I18n.format(tabs[i].getTranslatedTabLabel()), x + 4, y + 17 + i * 24 + 6, 4210752);
            }
            this.drawTexturedModalRect(x + 4, y + 17 + 4 * 24, 0, 166, 34, 23);
            this.drawString(this.fontRendererObj, I18n.format(CreativeTabsLoader.tabSearch.getTranslatedTabLabel()), x + 8, y + 17 + 4 * 24 + 6, 4210752);
            offset += 38;// 38
             */
            // 绘制创造魔法栏列表
            this.drawTexturedModalRect(x, y, 0, 0, 78, 165);// 未经检查!!!
            this.drawTexturedModalRect(x + offset + 59, y + 18, 0, 190, 12, 15);
            offset += 78;// 116
        }
        // 绘制魔法栏
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(magicBackground);
        this.drawTexturedModalRect(x + offset, y, 0, 0, 176, 166);
        // 绘制玩家
        GuiInventory.drawEntityOnScreen(x + offset + 23, y + 76, 20, x + offset + 23 - this.oldMouseX, y + 76 - 30 - this.oldMouseY, this.mc.thePlayer);
        // 绘制说明
        this.fontRendererObj.drawString(I18n.format("container.skill"), x + offset + 6, y + 5, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.talent"), x + offset + 50, y + 5, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.talent.desc", 0), x + offset + 90, y + 5, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.name", this.mc.thePlayer.getName()), x + offset + 6, y + 17, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.exp", this.mc.thePlayer.experienceLevel), x + offset + 6, y + 26, 4210752);
        // 绘制按钮
        // this.buttonList.add(new GuiButton(0, 7, 81, 21, 10, I18n.format("container.magic.more")));
        // 绘制天赋栏

        // 绘制信息栏
        this.fontRendererObj.drawString(I18n.format("container.magic.health", (int)this.mc.thePlayer.getHealth(), (int)this.mc.thePlayer.getMaxHealth()), x + offset + 6, y + 35, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.magic.mana", this.mc.thePlayer.getCapability(CapabilityLoader.playerMagic, null).getMana(), "20"), x + offset + 6, y + 44, 4210752);
    }
}
