package io.github.dawncraft.item;

import java.util.List;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.tileentity.TileEntityMagnetDoor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnetDoor extends ItemDoor
{
    public ItemMagnetDoor()
    {
        super(BlockLoader.magnetDoor);
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(super.onItemUse(itemStack, player, world, pos, side, hitX, hitY, hitZ))
        {
            if(!world.isRemote)
            {
                Block block = world.getBlockState(pos).getBlock();
                if (!block.isReplaceable(world, pos)) pos = pos.offset(side);
                TileEntity tileentity = world.getTileEntity(pos);
                if (tileentity instanceof TileEntityMagnetDoor)
                {
                    TileEntityMagnetDoor tileentitydoor = (TileEntityMagnetDoor) tileentity;
                    if(itemStack.hasTagCompound())
                    {
                        NBTTagCompound nbt = itemStack.getTagCompound();
                        if (nbt.hasKey("UUID", 8))
                        {
                            if (nbt.hasKey("Owner", 8))
                            {
                                tileentitydoor.setName(nbt.getString("Owner"));
                            }
                            
                            tileentitydoor.setUUID(nbt.getString("UUID"));
                        }
                        else if (nbt.hasKey("Owner", 8))
                        {
                            String name = nbt.getString("Owner");
                            EntityPlayerMP ep = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(name);
                            tileentitydoor.setName(name);
                            tileentitydoor.setUUID(EntityPlayer.getUUID(ep.getGameProfile()).toString());
                        }
                    }
                    else
                    {
                        tileentitydoor.setName(player.getName());
                        tileentitydoor.setUUID(player.getUniqueID().toString());
                    }
                }
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            String name = nbt.getString("Owner");
            if(!StringUtils.isNullOrEmpty(name))
            {
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("item.magnetDoor.desc", name));
            }
        }
    }
}
