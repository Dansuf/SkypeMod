package net.golui.skypemod.asm;

import java.util.Arrays;
import net.golui.skypemod.ConnectionHandler;
import net.golui.skypemod.NextTabHandler;
import net.golui.skypemod.PrevTabHandler;
import net.golui.skypemod.SkypeCommunicator;
import net.golui.skypemod.SkypeDatabase;
import net.golui.skypemod.gui.GuiTabs;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@NetworkMod(serverSideRequired=false, clientSideRequired=true)
public class SkypeModModContainer extends DummyModContainer {
	
    public SkypeModModContainer() 
    {
            super(new ModMetadata());
            /* ModMetadata is the same as mcmod.info */
            ModMetadata myMeta = super.getMetadata();
            myMeta.authorList = Arrays.asList(new String[] { "Golui", "Dan Sufho"});
            myMeta.description = "Skype plugin for minecraft";
            myMeta.modId = "GoluiSkypeMod";
            myMeta.version = "Dev";
            myMeta.name = "Skype Mod";
            myMeta.url = "http://golui.net/";
    }
    
    @Instance("GoluiSkypeMod")
    public static SkypeModModContainer instance;
    public static GuiTabs tabs;
    
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
    	bus.register(this);
    	return true;
    }
    /* 
     * Use this in place of @Init, @Preinit, @Postinit in the file.
     */
    
    @Subscribe
    public void load(FMLInitializationEvent event) {
    	SkypeDatabase.newInstance();
    	SkypeCommunicator.setCommunicator();
    	NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler());
    	KeyBindingRegistry.registerKeyBinding(new NextTabHandler());
    	KeyBindingRegistry.registerKeyBinding(new PrevTabHandler());
    	tabs = new GuiTabs();
    	SkypeCommunicator.getCommunicator().init();
    }
    
    
}
