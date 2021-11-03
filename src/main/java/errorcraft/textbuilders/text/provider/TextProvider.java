package errorcraft.textbuilders.text.provider;

import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonSerializer;

import java.util.function.Function;

public interface TextProvider extends Function<LootContext, String> {
	TextProviderType getType();

	interface Serialiser<T extends TextProvider> extends JsonSerializer<T> {}
}
