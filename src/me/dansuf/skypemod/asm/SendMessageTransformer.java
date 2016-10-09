package net.golui.skypemod.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class SendMessageTransformer implements IClassTransformer, Opcodes {

    @Override
    public byte[] transform(String name,String transformedName, byte[] bytes){
    	
    	if(!name.equals("net.minecraft.client.entity.EntityClientPlayerMP")) //"net.minecraft.client.entity.EntityClientPlayerMP", "bdw"
    		return bytes;
    	
    	
    	 ClassReader cr=new ClassReader(bytes);
         ClassNode classNode=new ClassNode();
         cr.accept(classNode, 0);
       
         //Let's move through all the methods
         
         for(MethodNode methodNode: classNode.methods){
        	 
            if(methodNode.name.equals("sendChatMessage") && methodNode.desc.equals("(Ljava/lang/String;)V")) { //"sendChatMessage", "d"
           
                LabelNode ln = null;
            	LabelNode ln1 = null;
            	AbstractInsnNode returnInsn = null;
                
                for(int i = 0; i < methodNode.instructions.size(); i++)
                {
                	
                AbstractInsnNode instruction = methodNode.instructions.get(i);
                
               	 if(ln != null)
               	 {
               		 if(ln1==null)
               		 {
               			 if(instruction instanceof LabelNode)
               			 {
               				 ln1 = (LabelNode) instruction;
               			 }
               		 } else
               		 {
               		  if(instruction.getOpcode()==Opcodes.IRETURN
                              ||instruction.getOpcode()==Opcodes.RETURN
                              ||instruction.getOpcode()==Opcodes.ARETURN
                              ||instruction.getOpcode()==Opcodes.LRETURN
                              ||instruction.getOpcode()==Opcodes.DRETURN)
               		  {
               			  returnInsn = instruction;
               			  break;
               		  }
               		 }
               			 
               				 
               	 } else
               		if(instruction instanceof LabelNode)
                  		 ln = (LabelNode) instruction;
                }
                
                LabelNode ln0 = new LabelNode();
                
            	 
                 
                 InsnList list=new InsnList();
                 list.add(new VarInsnNode(ALOAD, 1));
                 list.add(new VarInsnNode(ALOAD, 0));
                 list.add(new MethodInsnNode(INVOKESTATIC, "net/golui/skypemod/Helper", "sendChatMessage", "(Ljava/lang/String;Lnet/minecraft/client/entity/EntityClientPlayerMP;)Z")); //"(Ljava/lang/String;Lnet/minecraft/client/entity/EntityClientPlayerMP;)Z"
                 list.add(new JumpInsnNode(IFEQ, ln1));
                 list.add(ln0);
                 
                 InsnList list1 = new InsnList();
                 list1.add(new FrameNode(F_SAME, 0, null, 0, null));
                 
                 
                 methodNode.instructions.insert(ln, list);
                 methodNode.instructions.insertBefore(returnInsn, list1);
               
             }
         }
		
         //We are done now. so dump the class
         
         ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
         classNode.accept(cw);
         bytes = cw.toByteArray();
        return bytes;
    }

}
