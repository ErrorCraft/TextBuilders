package errorcraft.textbuilders.text.builder;

import net.minecraft.util.JsonSerializer;

import java.util.function.Supplier;

public interface TextBuilder extends Supplier<String> {
	TextBuilderType getType();

	interface Serialiser<T extends TextBuilder> extends JsonSerializer<T> {}
}
