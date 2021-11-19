package errorcraft.textbuilders.mixin.text;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import errorcraft.textbuilders.text.provider.TextProviderAccess;
import errorcraft.textbuilders.text.provider.Deserialisers;
import errorcraft.textbuilders.text.provider.TextProvider;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Stack;

@Mixin(Text.Serializer.class)
public class TextSerializerExtender {
	private static final Gson TEXT_PROVIDER_GSON = Deserialisers.createTextProviderSerialiser().create();
	private final Stack<TextProvider> textProviders = new Stack<>();

	@Inject(at = @At("HEAD"), method = "deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;")
	private void pushTextProvider(CallbackInfoReturnable<MutableText> info) {
		textProviders.push(null);
	}

	@Redirect(
			method = "deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;",
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lcom/google/gson/JsonElement;getAsJsonObject()Lcom/google/gson/JsonObject;"),
					to = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text$Serializer;deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;")
			),
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/JsonHelper;getString(Lcom/google/gson/JsonObject; Ljava/lang/String;)Ljava/lang/String;"
			)
	)
	private String readTextProvider(JsonObject object, String element) {
		JsonElement text = object.get(element);
		if (text.isJsonPrimitive()) {
			return text.getAsString();
		}

		this.textProviders.set(this.textProviders.size() - 1, getTextProvider(text));
		return "";
	}

	@Inject(at = @At("RETURN"), method = "deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;")
	private void injectTextProvider(CallbackInfoReturnable<MutableText> info) {
		TextProvider lastElement = this.textProviders.pop();
		if (lastElement == null) {
			return;
		}
		if (info.getReturnValue() instanceof TextProviderAccess text) {
			text.setTextProvider(lastElement);
		}
	}

	private TextProvider getTextProvider(JsonElement json) {
		if (!json.isJsonObject()) {
			throw new JsonParseException("Don't know how to turn " + json + " into a Component");
		}
		return TEXT_PROVIDER_GSON.fromJson(json, TextProvider.class);
	}
}
