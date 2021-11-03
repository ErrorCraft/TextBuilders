package errorcraft.textbuilders.text.provider.providers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import errorcraft.textbuilders.text.provider.TextProvider;
import errorcraft.textbuilders.text.provider.TextProviderType;
import errorcraft.textbuilders.text.provider.TextProviderTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;

public class StringTextProvider implements TextProvider {
	private final String string;

	public StringTextProvider(String string) {
		this.string = string;
	}

	@Override
	public TextProviderType getType() {
		return TextProviderTypes.STRING;
	}

	@Override
	public String apply(LootContext lootContext) {
		return this.string;
	}

	public static class Serialiser implements TextProvider.Serialiser<StringTextProvider> {
		@Override
		public void toJson(JsonObject json, StringTextProvider object, JsonSerializationContext context) {
			json.addProperty("string", object.string);
		}

		@Override
		public StringTextProvider fromJson(JsonObject json, JsonDeserializationContext context) {
			String string = JsonHelper.getString(json, "string");
			return new StringTextProvider(string);
		}
	}
}
