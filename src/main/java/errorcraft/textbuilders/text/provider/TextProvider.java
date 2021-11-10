package errorcraft.textbuilders.text.provider;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.JsonSerializer;

import java.util.function.Function;

public interface TextProvider extends Function<ServerCommandSource, String> {
	TextProviderType getType();

	interface Serialiser<T extends TextProvider> extends JsonSerializer<T> {}
}
