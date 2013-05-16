package net.golui.skypemod.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class GuiInGameTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		
		if(!name.equals("net.minecraft.client.gui.GuiIngame")) //"net.minecraft.client.gui.GuiIngame", "aww"
    		return bytes;
		
		
		ClassReader cr=new ClassReader(bytes);
        ClassNode classNode=new ClassNode();
        cr.accept(classNode, 0);
        
        for(MethodNode methodNode: classNode.methods){
       	 
       	 
            if(methodNode.name.equals("<init>")) {
            	for(int i = 0; i < methodNode.instructions.size(); i++)
            	{
            		AbstractInsnNode instruction = methodNode.instructions.get(i);
            		if(instruction instanceof TypeInsnNode && ((TypeInsnNode) instruction).desc.equals("net/minecraft/client/gui/GuiNewChat")) //"net/minecraft/client/gui/GuiNewChat", "awh"
            		{
            			((TypeInsnNode)methodNode.instructions.get(i)).desc = "net/golui/skypemod/gui/GuiModifiedChat";
            			((MethodInsnNode)methodNode.instructions.get(i+3)).owner = "net/golui/skypemod/gui/GuiModifiedChat";
            			((MethodInsnNode)methodNode.instructions.get(i+3)).name = "<init>";
            			((MethodInsnNode)methodNode.instructions.get(i+3)).desc = "(Lnet/minecraft/client/Minecraft;)V";
            		}
            	}
            }
            
        }
		
        ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
        classNode.accept(cw);
        bytes = cw.toByteArray();
        
		return bytes;
	}

}
