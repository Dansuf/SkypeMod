package net.golui.skypemod.gui;

import net.golui.skypemod.asm.SkypeModModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiTab extends GuiButton {
	
	public final static byte NORMAL = 0;
	public final static byte ACTIVE = 1;
	public final static byte NEW_MESSAGES = 2;
	
	private byte state = 0;

	public GuiTab(int par1, int par2, String par3Str, Minecraft par4)
    {	
        super(par1, par2, 0, 50, 10, par3Str);
    }
	
	public void setState(byte state)
	{
		this.state = state;
	}
	
	public void setXPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}
	
	public void drawButton(Minecraft par1Minecraft, int par2, int par3, int width, int opacity)
    {
        if (this.drawButton)
        {
        	int color = 0;
        	this.yPosition = -par1Minecraft.ingameGUI.getChatGUI().func_96133_g() -10;
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            //this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + width && par3 < this.yPosition + this.height;
            //int k = this.getHoverState(this.field_82253_i);
            
            switch(this.state)
            {
            	case NORMAL:
            		opacity /= 2;
            		break;
            	case NEW_MESSAGES:
            		color = 0xffba70;
            		break;
            }
            
            drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + this.height, (opacity/2 << 24) + color);
            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, /*46 + k * 20*/ textH, width / 2, this.height);
            //this.drawTexturedModalRect(this.xPosition + width / 2, this.yPosition, 200 - width / 2, /*46 + k * 20*/ textH, width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            
            /*
            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }
            */
            
            GL11.glEnable(GL11.GL_BLEND);
            String label = fontrenderer.trimStringToWidth(this.displayString,width-4);
            fontrenderer.drawStringWithShadow(label, this.xPosition + this.width/2 - fontrenderer.getStringWidth(label)/2, this.yPosition + 1, 16777215 + (opacity << 24));
            //this.drawCenteredString(fontrenderer, fontrenderer.trimStringToWidth(this.displayString,width-4), this.xPosition + width / 2, this.yPosition + (this.height - 8) / 2, l);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

}
