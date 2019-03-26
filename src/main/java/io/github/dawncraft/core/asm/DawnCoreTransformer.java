package io.github.dawncraft.core.asm;

import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import io.github.dawncraft.core.DawnCoreSetuper;
import net.minecraft.launchwrapper.IClassTransformer;

public class DawnCoreTransformer implements IClassTransformer
{
    private static final List[] METHODNAMES =
        {
                Arrays.asList("registerAllBlocks", "func_178119_d", "d"),
                Arrays.asList("getTexture", "func_178122_a", "a"),
                Arrays.asList("renderTileItem"),// emmm,Forge没有混淆
                Arrays.asList("mouseClicked", "func_73864_a", "a"),
                Arrays.asList("transferEntityToWorld", "func_82448_a", "a"),
                Arrays.asList("transferPlayerToDimension", "func_72356_a", "a")
        };
    private static final List[] FIELDNAMES =
        {
                Arrays.asList("modelManager", "field_178128_c", "c"),
                Arrays.asList("barrier", "field_180401_cv", "cv")
        };
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        // 陈独秀请开始你的表演
        boolean changed = false;
        ClassNode classNode = getClassNode(basicClass);
        // 客户端
        if (transformedName.equals("net.minecraft.client.renderer.BlockModelShapes"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (METHODNAMES[0].contains(methodNode.name) && methodNode.desc.equals("()V"))
                {
                    changed = true;
                    // 对BlockModelShapes.registerAllBlocks()进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // RETURN
                        if (insnNode.getOpcode() == Opcodes.RETURN)
                        {
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 0));
                            methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "onRegisterAllBlocks", "(Lnet/minecraft/client/renderer/BlockModelShapes;)V", false));
                        }
                    }
                }
                else if (METHODNAMES[1].contains(methodNode.name) && methodNode.desc.equals("(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
                {
                    changed = true;
                    // 对BlockModelShapes.getTexture(IBlockState)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // 第二个 IF_ACMPNE L4
                        if (insnNode.getOpcode() == Opcodes.IF_ACMPNE && insnNode.getPrevious().getOpcode() == Opcodes.GETSTATIC)
                        {
                            FieldInsnNode prevNode = (FieldInsnNode) insnNode.getPrevious();
                            // 上面是GETSTATIC net/minecraft/init/Blocks.barrier : Lnet/minecraft/block/Block;
                            if (prevNode.owner.equals("net/minecraft/init/Blocks") && FIELDNAMES[1].contains(prevNode.name))
                            {
                                // 先加一个标签
                                LabelNode label = new LabelNode();
                                methodNode.instructions.add(label);
                                methodNode.instructions.add(new IntInsnNode(Opcodes.ALOAD, 0));
                                String fieldName = DawnCoreSetuper.isDeobfEnv ? (String) FIELDNAMES[0].get(1) : (String) FIELDNAMES[0].get(0);
                                methodNode.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/BlockModelShapes", fieldName, "Lnet/minecraft/client/resources/model/ModelManager;"));
                                methodNode.instructions.add(new IntInsnNode(Opcodes.ALOAD, 2));
                                methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "getBlockParticle", "(Lnet/minecraft/client/resources/model/ModelManager;Lnet/minecraft/block/Block;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", false));
                                methodNode.instructions.add(new InsnNode(Opcodes.ARETURN));
                                // 把这个字节码改为跳转到上面加的标签
                                methodNode.instructions.set(insnNode, new JumpInsnNode(Opcodes.IF_ACMPNE, label));
                            }
                        }
                    }
                }
            }
        }
        if (transformedName.equals("net.minecraftforge.client.ForgeHooksClient"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (METHODNAMES[2].contains(methodNode.name) && methodNode.desc.equals("(Lnet/minecraft/item/Item;I)V"))
                {
                    changed = true;
                    // 对ForgeHooksClient.renderTileItem(Item, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // ACONST_NULL
                        if (insnNode.getOpcode() == Opcodes.ACONST_NULL)
                        {
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ALOAD, 0));
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ILOAD, 1));
                            methodNode.instructions.set(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "getTileentityForItem", "(Lnet/minecraft/item/Item;I)Lnet/minecraft/tileentity/TileEntity;", false));
                        }
                    }
                }
            }
        }
        if (transformedName.equals("net.minecraft.client.gui.GuiScreen"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (METHODNAMES[3].contains(methodNode.name) && methodNode.desc.equals("(III)V"))
                {
                    changed = true;
                    // 对GuiScreen.mouseClicked(int, int, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // POP
                        if (insnNode.getOpcode() == Opcodes.POP && insnNode.getPrevious().getOpcode() == Opcodes.INVOKEVIRTUAL)
                        {
                            methodNode.instructions.insert(insnNode, new InsnNode(Opcodes.RETURN));
                        }
                    }
                }
            }
        }
        // 服务器
        if (transformedName.equals("net.minecraft.server.management.ServerConfigurationManager"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (METHODNAMES[4].contains(methodNode.name) && methodNode.desc.equals("(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;)V"))
                {
                    changed = true;
                    // 对net.minecraft.server.management.ServerConfigurationManager.transferEntityToWorld(Entity, int, WorldServer, WorldServer)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // 第二个  ALOAD 4
                        if (insnNode.getOpcode() == Opcodes.ALOAD && insnNode.getNext().getOpcode() == Opcodes.INVOKEVIRTUAL)
                        {
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ALOAD, 1));
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ILOAD, 2));
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ALOAD, 3));
                            methodNode.instructions.set(insnNode.getNext(), new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/server/DawnServerHooks", "getTeleporter", "(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;)Lnet/minecraft/world/Teleporter;", false));
                        }
                    }
                }
                else if (METHODNAMES[5].contains(methodNode.name) && methodNode.desc.equals("(Lnet/minecraft/entity/player/EntityPlayerMP;I)V"))
                {
                    changed = true;
                    // 对net.minecraft.server.management.ServerConfigurationManager.transferPlayerToDimension(EntityPlayerMP, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        if (insnNode.getOpcode() == Opcodes.ALOAD && insnNode.getPrevious().getOpcode() == Opcodes.ILOAD)
                        {
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ALOAD, 1));
                            methodNode.instructions.insertBefore(insnNode, new IntInsnNode(Opcodes.ILOAD, 2));
                        }
                        else if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) insnNode).desc.equals("()Lnet/minecraft/world/Teleporter;"))
                        {
                            methodNode.instructions.set(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/server/DawnServerHooks", "getTeleporter", "(Lnet/minecraft/entity/player/EntityPlayerMP;ILnet/minecraft/world/WorldServer;)Lnet/minecraft/world/Teleporter;", false));
                        }
                    }
                }
            }
        }
        // 改了就快覆盖回去,切记一定要返回字节码,哪怕是原封不动
        if (changed) return getBytecode(classNode);
        return basicClass;
        // 我这辈子都不想再碰这东西了啊啊啊
    }

    static public ClassNode getClassNode(byte[] bytes)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
        return classNode;
    }
    
    static public byte[] getBytecode(ClassNode classNode)
    {
        // 让ClassWriter自行计算最大栈深度和栈映射帧等信息
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
