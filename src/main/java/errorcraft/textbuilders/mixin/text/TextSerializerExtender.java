package errorcraft.textbuilders.mixin.text;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import errorcraft.textbuilders.access.text.LiteralTextExtenderAccess;
import errorcraft.textbuilders.text.provider.Deserialisers;
import errorcraft.textbuilders.text.provider.TextProvider;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Text.Serializer.class)
public class TextSerializerExtender {
	private static final Gson TEXT_PROVIDER_GSON = Deserialisers.createTextProviderSerialiser().create();
	private TextProvider textProvider;

	@Redirect(method = "deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;getString(Lcom/google/gson/JsonObject; Ljava/lang/String;)Ljava/lang/String;", ordinal = 0))
	private String readTextObject(JsonObject object, String element) {
		JsonElement text = object.get(element);
		if (text.isJsonPrimitive()) {
			return text.getAsString();
		}

		this.textProvider = getTextProvider(text);
		return "";
	}

	@Redirect(method = "deserialize(Lcom/google/gson/JsonElement; Ljava/lang/reflect/Type; Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/text/MutableText;", at = @At(value = "NEW", target = "Lnet/minecraft/text/LiteralText;"))
	private LiteralText injectTextProvider(String string) {
		LiteralText literalText = new LiteralText(string);
		setTextProvider(literalText);
		return literalText;
	}

	private TextProvider getTextProvider(JsonElement json) {
		if (!json.isJsonObject()) {
			throw new JsonParseException("Don't know how to turn " + json + " into a Component");
		}
		return TEXT_PROVIDER_GSON.fromJson(json, TextProvider.class);
	}

	private void setTextProvider(LiteralText literalText) {
		if (this.textProvider == null) {
			return;
		}
		((LiteralTextExtenderAccess)literalText).setTextProvider(this.textProvider);
		this.textProvider = null;
	}
}
