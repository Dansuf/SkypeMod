package net.golui.skypemod;

import java.util.EnumSet;

import net.golui.skypemod.asm.SkypeModModContainer;
import net.golui.skypemod.gui.GuiModifiedChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class NextTabHandler extends KeyHandler
{
	
	public NextTabHandler() {
		super(new KeyBinding[]{new KeyBinding("Next Tab", Keyboard.KEY_RIGHT)}, new boolean[]{false});
	}

	@Override
	public String getLabel() {
		return "Next Tab keyBindings";
	}
	
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if(tickEnd)
		{
		((GuiModifiedChat)Minecraft.getMinecraft().ingameGUI.getChatGUI()).nextTab();
		SkypeModModContainer.tabs.showTabs();
		}
	}
}   
