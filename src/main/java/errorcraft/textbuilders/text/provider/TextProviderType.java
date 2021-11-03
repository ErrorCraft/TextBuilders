package errorcraft.textbuilders.text.provider;

import net.minecraft.util.JsonSerializableType;
import net.minecraft.util.JsonSerializer;

public class TextProviderType extends JsonSerializableType<TextProvider> {
	public TextProviderType(JsonSerializer<? extends TextProvider> jsonSerializer) {
		super(jsonSerializer);
	}
}
