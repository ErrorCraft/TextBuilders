package errorcraft.textbuilders.text.builder.builders;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import errorcraft.textbuilders.text.builder.TextBuilder;
import errorcraft.textbuilders.text.builder.TextBuilderType;
import errorcraft.textbuilders.text.builder.TextBuilderTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;

public class LiteralTextBuilder implements TextBuilder {
	private final String literal;

	public LiteralTextBuilder(String literal) {
		this.literal = literal;
	}

	@Override
	public TextBuilderType getType() {
		return TextBuilderTypes.LITERAL;
	}

	@Override
	public String apply(LootContext lootContext) {
		return this.literal;
	}

	public static class Serialiser implements TextBuilder.Serialiser<LiteralTextBuilder> {
		@Override
		public void toJson(JsonObject json, LiteralTextBuilder object, JsonSerializationContext context) {
			json.addProperty("literal", object.literal);
		}

		@Override
		public LiteralTextBuilder fromJson(JsonObject json, JsonDeserializationContext context) {
			String literal = JsonHelper.getString(json, "literal");
			return new LiteralTextBuilder(literal);
		}
	}
}
