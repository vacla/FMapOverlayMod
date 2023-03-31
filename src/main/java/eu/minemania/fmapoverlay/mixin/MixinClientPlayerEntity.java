package eu.minemania.fmapoverlay.mixin;

import com.mojang.brigadier.StringReader;
import eu.minemania.fmapoverlay.command.ClientCommandManager;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity
{
    @Inject(method = "sendCommand(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    private void onSendCommand(String message, Text preview, CallbackInfo ci)
    {
        StringReader reader = new StringReader(message);
        int cursor = reader.getCursor();
        String commandName = reader.canRead() ? reader.readUnquotedString() : "";
        reader.setCursor(cursor);
        if (ClientCommandManager.isClientSideCommand(commandName))
        {
            ClientCommandManager.executeCommand(reader, message);
            ci.cancel();
        }
    }
}