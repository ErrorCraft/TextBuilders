package errorcraft.textbuilders.text.builder.builders;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import errorcraft.textbuilders.text.builder.TextBuilder;
import errorcraft.textbuilders.text.builder.TextBuilderType;
import errorcraft.textbuilders.text.builder.TextBuilderTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.JsonHelper;

public class RepetitionTextBuilder implements TextBuilder {
	private final LootNumberProvider countProvider;
	private final TextBuilder stringProvider;

	public RepetitionTextBuilder(LootNumberProvider countProvider, TextBuilder stringProvider) {
		this.countProvider = countProvider;
		this.stringProvider = stringProvider;
	}

	@Override
	public TextBuilderType getType() {
		return TextBuilderTypes.REPETITION;
	}

	@Override
	public String apply(LootContext lootContext) {
		String string = stringProvider.apply(lootContext);
		StringBuilder stringBuilder = new StringBuilder();
		for (int count = countProvider.nextInt(lootContext); count > 0; count--) {
			stringBuilder.append(string);
		}
		return stringBuilder.toString();
	}

	public static class Serialiser implements TextBuilder.Serialiser<RepetitionTextBuilder> {
		@Override
		public void toJson(JsonObject json, RepetitionTextBuilder object, JsonSerializationContext context) {
			json.add("count", context.serialize(object.countProvider));
			json.add("string", context.serialize(object.stringProvider));
		}

		@Override
		public RepetitionTextBuilder fromJson(JsonObject json, JsonDeserializationContext context) {
			LootNumberProvider countProvider = JsonHelper.deserialize(json, "count", context, LootNumberProvider.class);
			TextBuilder stringProvider = JsonHelper.deserialize(json, "string", context, TextBuilder.class);
			return new RepetitionTextBuilder(countProvider, stringProvider);
		}
	}
}
