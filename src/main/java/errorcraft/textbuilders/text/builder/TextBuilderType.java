package errorcraft.textbuilders.text.builder;

import net.minecraft.util.JsonSerializableType;
import net.minecraft.util.JsonSerializer;

public class TextBuilderType extends JsonSerializableType<TextBuilder> {
	public TextBuilderType(JsonSerializer<? extends TextBuilder> jsonSerializer) {
		super(jsonSerializer);
	}
}
