package errorcraft.textbuilders.text.builder;

import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonSerializer;

import java.util.function.BiConsumer;

public interface TextBuilder extends BiConsumer<StringBuilder, LootContext> {
	TextBuilderType getType();

	interface Serialiser<T extends TextBuilder> extends JsonSerializer<T> {}
}
