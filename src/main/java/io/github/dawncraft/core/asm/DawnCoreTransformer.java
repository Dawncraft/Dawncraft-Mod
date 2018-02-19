package io.github.dawncraft.core.asm;

import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class DawnCoreTransformer implements IClassTransformer
{
    private static final List[] METHODNAMES =
        {
                Arrays.asList("registerAllBlocks", "func_178119_d", "d"),
                Arrays.asList("getTexture", "func_178122_a", "a")
        };
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        // 陈独秀请开始你的表演
        if(transformedName.equals("net.minecraft.client.renderer.BlockModelShapes"))
        {
            ClassNode classNode = getClassNode(basicClass);
            boolean changed = false;
            for(MethodNode methodNode : classNode.methods)
            {
                if(METHODNAMES[0].contains(methodNode.name) && methodNode.desc.equals("()V"))
                {
                    changed = true;
                    // 对BlockModelShapes.registerAllBlocks()进行操作
                    methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/dawncraft/core/RendererHelper", "", ""));
                }
                else if(METHODNAMES[1].contains(methodNode.name) && methodNode.desc.equals("(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
                {
                    changed = true;
                    // 对BlockModelShapes.getTexture(IBlockState)进行操作
                    
                    
                    
                }
            }
            if(changed) return getBytecode(classNode);
        }
        return basicClass;
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
        //让ClassWriter自行计算最大栈深度和栈映射帧等信息
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
