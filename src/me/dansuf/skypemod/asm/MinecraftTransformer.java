package net.golui.skypemod.asm;

import net.golui.skypemod.Helper;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class MinecraftTransformer implements IClassTransformer, Opcodes {

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		
		if(!name.equals("net.minecraft.client.Minecraft"))  //"net.minecraft.client.Minecraft"
			return bytes;
		
		ClassReader cr=new ClassReader(bytes);
        ClassNode classNode=new ClassNode();
        cr.accept(classNode, 0);
        
        for(MethodNode methodNode: classNode.methods){
        	
        	if(methodNode.name.equals("shutdownMinecraftApplet") && methodNode.desc.equals("()V"))  //"shutdownMinecraftApplet", "e"
        	{
        		LineNumberNode ln = null;
        		for(int i = 0; i < methodNode.instructions.size(); i++)
        		{
        			if(methodNode.instructions.get(i) instanceof LineNumberNode)
        			{
        				ln = (LineNumberNode) methodNode.instructions.get(i);
        				break;
        			}
        		}
        		MethodInsnNode instruction = new MethodInsnNode(INVOKESTATIC,"net/golui/skypemod/Helper", "shutdown", "()V");
        		methodNode.instructions.insert(ln, instruction);
        	}
        }
		
        ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
        classNode.accept(cw);
        bytes = cw.toByteArray();
        return bytes;
	}

}
