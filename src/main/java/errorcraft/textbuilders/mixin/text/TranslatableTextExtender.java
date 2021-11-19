package errorcraft.textbuilders.mixin.text;

import errorcraft.textbuilders.text.provider.TextProvider;
import errorcraft.textbuilders.text.provider.TextProviderAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TranslatableText.class)
public class TranslatableTextExtender implements TextProviderAccess {
	@Final
	@Shadow
	private String key;
	private TextProvider textProvider = null;

	@Override
	public void setTextProvider(TextProvider textProvider) {
		this.textProvider = textProvider;
	}

	@Inject(method = "parse(Lnet/minecraft/server/command/ServerCommandSource; Lnet/minecraft/entity/Entity; I)Lnet/minecraft/text/MutableText;", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void applyTextProvider(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth, CallbackInfoReturnable<MutableText> info, Object[] objects) {
		if (this.textProvider == null) {
			info.setReturnValue(new TranslatableText(this.key, objects));
			return;
		}
		if (source == null) {
			info.setReturnValue(new TranslatableText("", objects));
			return;
		}
		info.setReturnValue(new TranslatableText(this.textProvider.apply(source), objects));
	}
}
