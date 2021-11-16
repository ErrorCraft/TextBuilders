package errorcraft.textbuilders.mixin.text;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import errorcraft.textbuilders.text.provider.TextProviderAccess;
import errorcraft.textbuilders.text.provider.TextProvider;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.ParsableText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiteralText.class)
public class LiteralTextExtender implements TextProviderAccess, ParsableText {
	@Final
	@Shadow
	private String string;
	private TextProvider textProvider = null;

	@Override
	public void setTextProvider(TextProvider textProvider) {
		this.textProvider = textProvider;
	}

	@Inject(method = "asString()Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
	public void asString(CallbackInfoReturnable<String> info) {
		if (this.textProvider == null) {
			info.setReturnValue(this.string);
			return;
		}
		info.setReturnValue("");
	}

	@Override
	public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
		if (this.textProvider == null) {
			return new LiteralText(this.string);
		}
		if (source == null) {
			return new LiteralText("");
		}
		String string = this.textProvider.apply(source);
		return new LiteralText(string);
	}
}
