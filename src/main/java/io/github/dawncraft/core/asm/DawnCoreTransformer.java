package io.github.dawncraft.core.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import io.github.dawncraft.core.DawnCoreSetuper;

import net.minecraft.launchwrapper.IClassTransformer;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class DawnCoreTransformer implements IClassTransformer
{
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
                String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, methodNode.name, methodNode.desc);
                String methodDesc = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
                if ((methodName.equals("registerAllBlocks") || methodName.equals("func_178119_d")) && methodDesc.equals("()V"))
                {
                    changed = true;
                    // 对BlockModelShapes.registerAllBlocks()进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // RETURN
                        if (insnNode.getOpcode() == Opcodes.RETURN)
                        {
                            System.out.println("=================================");
                            System.out.println("Modifing " + transformedName + "( " + name + " )");
                            System.out.println("The class name is: " + classNode.name);
                            System.out.println("The method name is: " + methodName + "( " + methodNode.name + " )");
                            System.out.println("The method desc is: " + methodDesc + "( " + methodNode.desc + " )");
                            System.out.println("=================================");
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 0));
                            methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "onRegisterAllBlocks", "(Lnet/minecraft/client/renderer/BlockModelShapes;)V", false));
                        }
                    }
                }
                else if ((methodName.equals("getTexture") || methodName.equals("func_178122_a")) && methodDesc.equals("(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
                {
                    changed = true;
                    // 对BlockModelShapes.getTexture(IBlockState)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // 第二个 IF_ACMPNE L4
                        if (insnNode.getOpcode() == Opcodes.IF_ACMPNE && insnNode.getPrevious().getOpcode() == Opcodes.GETSTATIC)
                        {
                            FieldInsnNode prevNode = (FieldInsnNode) insnNode.getPrevious();
                            String fieldInsnName = FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(prevNode.owner, prevNode.name, prevNode.desc);
                            String fieldInsnDesc = FMLDeobfuscatingRemapper.INSTANCE.mapDesc(prevNode.desc);
                            String fieldInsnOwner = FMLDeobfuscatingRemapper.INSTANCE.map(prevNode.owner);
                            // 上面是GETSTATIC net/minecraft/init/Blocks.barrier : Lnet/minecraft/block/Block;
                            if ((fieldInsnName.equals("barrier") || fieldInsnName.equals("field_180401_cv")) && fieldInsnOwner.equals("net/minecraft/init/Blocks"))
                            {
                                System.out.println("=================================");
                                System.out.println("Modifing " + transformedName + "( " + name + " )");
                                System.out.println("The class name is: " + classNode.name);
                                System.out.println("The method name is: " + methodName + "( " + methodNode.name + " )");
                                System.out.println("The method desc is: " + methodDesc + "( " + methodNode.desc + " )");
                                System.out.println("=================================");
                                System.out.println("The field name is: " + fieldInsnName + "( " + prevNode.name + " )");
                                System.out.println("The field desc is: " + fieldInsnDesc + "( " + prevNode.desc + " )");
                                System.out.println("The field owner is: " + fieldInsnOwner + "( " + prevNode.owner + " )");
                                System.out.println("=================================");
                                // 先加一个标签
                                LabelNode label = new LabelNode();
                                methodNode.instructions.add(label);
                                methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                                String fieldName2 = DawnCoreSetuper.isDeobfEnv ? "field_178128_c" : "modelManager";
                                methodNode.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/BlockModelShapes", fieldName2, "Lnet/minecraft/client/resources/model/ModelManager;"));
                                methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
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
        else if (transformedName.equals("net.minecraftforge.client.ForgeHooksClient"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, methodNode.name, methodNode.desc);
                String methodDesc = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
                if (methodName.equals("renderTileItem") && methodDesc.equals("(Lnet/minecraft/item/Item;I)V"))
                {
                    changed = true;
                    // 对ForgeHooksClient.renderTileItem(Item, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // ACONST_NULL
                        if (insnNode.getOpcode() == Opcodes.ACONST_NULL)
                        {
                            System.out.println("=================================");
                            System.out.println("Modifing " + transformedName + "( " + name + " )");
                            System.out.println("The class name is: " + classNode.name);
                            System.out.println("The method name is: " + methodName + "( " + methodNode.name + " )");
                            System.out.println("The method desc is: " + methodDesc + "( " + methodNode.desc + " )");
                            System.out.println("=================================");
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 0));
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ILOAD, 1));
                            methodNode.instructions.set(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "getTileentityForItem", "(Lnet/minecraft/item/Item;I)Lnet/minecraft/tileentity/TileEntity;", false));
                        }
                    }
                }
            }
        }
        else if (transformedName.equals("net.minecraft.client.gui.GuiScreen"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, methodNode.name, methodNode.desc);
                String methodDesc = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
                if ((methodName.equals("mouseClicked") || methodName.equals("func_73864_a")) && methodDesc.equals("(III)V"))
                {
                    changed = true;
                    // 对GuiScreen.mouseClicked(int, int, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // POP
                        if (insnNode.getOpcode() == Opcodes.POP && insnNode.getPrevious().getOpcode() == Opcodes.INVOKEVIRTUAL)
                        {
                            System.out.println("=================================");
                            System.out.println("Modifing " + transformedName + "( " + name + " )");
                            System.out.println("The class name is: " + classNode.name);
                            System.out.println("The method name is: " + methodName + "( " + methodNode.name + " )");
                            System.out.println("The method desc is: " + methodDesc + "( " + methodNode.desc + " )");
                            System.out.println("=================================");
                            methodNode.instructions.insert(insnNode, new InsnNode(Opcodes.RETURN));
                        }
                    }
                }
                else if ((methodName.equals("handleComponentHover") || methodName.equals("func_175272_a")) && methodDesc.equals("(Lnet/minecraft/util/ITextComponent;II)V"))
                {
                    changed = true;
                    // 对GuiScreen.handleComponentHover(ITextComponent, int, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // 下面是 INVOKESTATIC net/minecraft/client/renderer/GlStateManager.disableLighting()V
                        if (insnNode.getOpcode() == Opcodes.INVOKESTATIC)
                        {
                            MethodInsnNode currentNode = (MethodInsnNode) insnNode;
                            String methodInsnName = FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(currentNode.owner, currentNode.name, currentNode.desc);
                            String methodInsnDesc = FMLDeobfuscatingRemapper.INSTANCE.mapDesc(currentNode.desc);
                            String methodInsnOwner = FMLDeobfuscatingRemapper.INSTANCE.map(currentNode.owner);
                            if (methodInsnDesc.equals("()V") && methodInsnOwner.equals("net/minecraft/client/renderer/GlStateManager"))
                            {
                                System.out.println("=================================");
                                System.out.println("Modifing " + transformedName + "( " + name + " )");
                                System.out.println("The class name is: " + classNode.name);
                                System.out.println("The method name is: " + methodName + "( " + methodNode.name + " )");
                                System.out.println("The method desc is: " + methodDesc + "( " + methodNode.desc + " )");
                                System.out.println("=================================");
                                System.out.println("The currentNode name is: " + methodInsnName + "( " + currentNode.name + " )");
                                System.out.println("The currentNode desc is: " + methodInsnDesc + "( " + currentNode.desc + " )");
                                System.out.println("The currentNode owner is: " + methodInsnOwner + "( " + currentNode.owner + " )");
                                System.out.println("=================================");
                                methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 0));
                                methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 1));
                                methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ILOAD, 2));
                                methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ILOAD, 3));
                                methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/client/DawnClientHooks", "onTextComponentHovered", "(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/util/ITextComponent;II)V", false));
                            }
                        }
                    }
                }
            }
        }
        // 服务器
        /*
        if (transformedName.equals("net.minecraft.server.management.ServerConfigurationManager"))
        {
            for (MethodNode methodNode : classNode.methods)
            {
                String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, methodNode.name, methodNode.desc);
                String methodDesc = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
                if ((methodName.equals("transferEntityToWorld") || methodName.equals("func_82448_a")) && methodDesc.equals("(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;)V"))
                {
                    changed = true;
                    // 对net.minecraft.server.management.ServerConfigurationManager.transferEntityToWorld(Entity, int, WorldServer, WorldServer)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        // 第二个 ALOAD 4
                        if (insnNode.getOpcode() == Opcodes.ALOAD && insnNode.getNext().getOpcode() == Opcodes.INVOKEVIRTUAL)
                        {
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 1));
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ILOAD, 2));
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 3));
                            methodNode.instructions.set(insnNode.getNext(), new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/server/DawnServerHooks", "getTeleporter", "(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;)Lnet/minecraft/world/Teleporter;", false));
                        }
                    }
                }
                else if ((methodName.equals("transferPlayerToDimension") || methodName.equals("func_72356_a")) && methodDesc.equals("(Lnet/minecraft/entity/player/EntityPlayerMP;I)V"))
                {
                    changed = true;
                    // 对net.minecraft.server.management.ServerConfigurationManager.transferPlayerToDimension(EntityPlayerMP, int)进行操作
                    for (AbstractInsnNode insnNode : methodNode.instructions.toArray())
                    {
                        if (insnNode.getOpcode() == Opcodes.ALOAD && insnNode.getPrevious().getOpcode() == Opcodes.ILOAD)
                        {
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ALOAD, 1));
                            methodNode.instructions.insertBefore(insnNode, new VarInsnNode(Opcodes.ILOAD, 2));
                        }
                        else if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) insnNode).desc.equals("()Lnet/minecraft/world/Teleporter;"))
                        {
                            methodNode.instructions.set(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/server/DawnServerHooks", "getTeleporter", "(Lnet/minecraft/entity/player/EntityPlayerMP;ILnet/minecraft/world/WorldServer;)Lnet/minecraft/world/Teleporter;", false));
                        }
                    }
                }
            }
        }
         */
        // 改了就快覆盖回去,切记一定要返回字节码,哪怕是原封不动
        if (changed) return getBytecode(classNode);
        return basicClass;
        // 我这辈子都不想再碰这东西了啊啊啊
    }

    static public ClassNode getClassNode(byte[] bytes)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        return classNode;
    }
    
    static public byte[] getBytecode(ClassNode classNode)
    {
        // 让ClassWriter自行计算最大栈深度和栈映射帧等信息
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
