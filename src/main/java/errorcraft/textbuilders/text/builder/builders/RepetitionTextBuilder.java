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
	public void accept(StringBuilder stringBuilder, LootContext lootContext) {
		StringBuilder stringProviderBuilder = new StringBuilder();
		this.stringProvider.accept(stringProviderBuilder, lootContext);
		stringBuilder.append(stringProviderBuilder.toString().repeat(Math.max(0, countProvider.nextInt(lootContext))));
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
