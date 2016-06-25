package WdawningStudio.DawnW.science.entity;

import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityGerKing extends EntityMob implements IBossDisplayData
{
    public EntityGerKing(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
   
    @Override
    protected void dropFewItems(boolean arg1, int arg2)
    {
        this.dropItem(ItemLoader.gerHeart, 1);
    }
}
