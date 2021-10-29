package errorcraft.textbuilders.text.builder;

import errorcraft.textbuilders.mixin.registry.RegistryAccessor;
import errorcraft.textbuilders.text.builder.builders.LiteralTextBuilder;
import errorcraft.textbuilders.text.builder.builders.RepetitionTextBuilder;
import errorcraft.textbuilders.text.builder.builders.ScoreTextBuilder;
import net.minecraft.util.JsonSerializing;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class TextBuilderTypes {
	public static final RegistryKey<Registry<TextBuilderType>> TEXT_BUILDER_TYPE_KEY = RegistryAccessor.createRegistryKey("text_builder_type");
	public static final Registry<TextBuilderType> TEXT_BUILDER_TYPE = RegistryAccessor.create(TEXT_BUILDER_TYPE_KEY, () -> null);

	public static final TextBuilderType LITERAL = register("literal", new LiteralTextBuilder.Serialiser());
	public static final TextBuilderType SCORE = register("score", new ScoreTextBuilder.Serialiser());
	public static final TextBuilderType REPETITION = register("repetition", new RepetitionTextBuilder.Serialiser());

	public static Object createGsonAdapter() {
		return JsonSerializing.createSerializerBuilder(TEXT_BUILDER_TYPE, "type", "type", TextBuilder::getType).build();
	}

	private static TextBuilderType register(String id, TextBuilder.Serialiser<? extends TextBuilder> serialiser) {
		return Registry.register(TEXT_BUILDER_TYPE, id, new TextBuilderType(serialiser));
	}
}
