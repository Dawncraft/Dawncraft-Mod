package io.github.dawncraft.api.item;

import java.lang.reflect.Field;
import java.util.Set;

import com.google.common.collect.Sets;

import io.github.dawncraft.api.block.BlockFurniture;
import io.github.dawncraft.api.block.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * A new tool that is used to destroy {@link BlockMachine} and {@link BlockFurniture}
 *
 * @author QingChenW
 */
public class ItemHammer extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();
    
    public ItemHammer(ToolMaterial material)
    {
        super(2.0F, material, EFFECTIVE_ON);
        try
        {
            Field field = ReflectionHelper.findField(ItemTool.class, "toolClass");
            EnumHelper.setFailsafeFieldValue(field, this, "hammer");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean canHarvestBlock(Block block)
    {
        return block instanceof BlockMachine ? this.toolMaterial.getHarvestLevel() >= 1 : block instanceof BlockFurniture;
    }
}
