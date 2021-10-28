package errorcraft.textbuilders.text.builder;

import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonSerializer;

import java.util.function.Function;

public interface TextBuilder extends Function<LootContext, String> {
	TextBuilderType getType();

	interface Serialiser<T extends TextBuilder> extends JsonSerializer<T> {}
}
