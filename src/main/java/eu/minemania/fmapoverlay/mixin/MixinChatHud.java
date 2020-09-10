package eu.minemania.fmapoverlay.mixin;

import eu.minemania.fmapoverlay.render.OverlayRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatHud.class)
public abstract class MixinChatHud
{
    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"), argsOnly = true)
    private Text getChatLines(Text componentln)
    {
        if (componentln.getString().matches("_+\\.\\[.*\\(-?[0-9]+.*"))
        {
            OverlayRenderer.clearLines();
            OverlayRenderer.addLine(componentln.getString());
        }
        else if (OverlayRenderer.getSize() > 0 && OverlayRenderer.getSize() < 19)
        {
            OverlayRenderer.addLine(componentln.getString());
        }
        return componentln;
    }
}