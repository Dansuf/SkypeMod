package net.golui.skypemod.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchClassLoader;

@TransformerExclusions({"net.golui.skypemod.asm"})
public class SkypeModLoadingPlugin implements IFMLLoadingPlugin {
	
	public static RelaunchClassLoader cl;

	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]
				{"net.golui.skypemod.asm.SendMessageTransformer",
				 "net.golui.skypemod.asm.GuiInGameTransformer",
				 "net.golui.skypemod.asm.MinecraftTransformer",
				 "net.golui.skypemod.asm.GameWindowListenerTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return  "net.golui.skypemod.asm.SkypeModModContainer";
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		 cl = (RelaunchClassLoader) data.get("classLoader");
		
	}

}
