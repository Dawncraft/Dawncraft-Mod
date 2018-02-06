package io.github.dawncraft.api.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSwordBase extends ItemSword
{
    private boolean temp = false;
    
    public ItemSwordBase(Item.ToolMaterial material)
    {
        super(material);
    }

    // 完完全全地测试用,没用就删了吧
    private static int ticker = 0;// 计时器
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(this.temp)
        {
            if(ticker > 10 * 20)// 到10秒
            {
                ticker = 0;
                if(entityIn instanceof EntityPlayer)// 转换成玩家,实体好像没有添加效果的方法
                {
                    EntityPlayer player = (EntityPlayer) entityIn;
                    player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200, 1));// 示范所以加了个隐身
                    // 其他东西
                }
                return;
            }
            ticker++;
        }
    }
}
