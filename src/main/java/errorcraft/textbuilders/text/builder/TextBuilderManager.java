package errorcraft.textbuilders.text.builder;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class TextBuilderManager extends JsonDataLoader {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = Deserialisers.createTextBuilderSerialiser().create();
	private Map<Identifier, TextBuilder> textBuilders = ImmutableMap.of();

	public TextBuilderManager() {
		super(GSON, "text_builders");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		ImmutableMap.Builder<Identifier, TextBuilder> builder = ImmutableMap.builder();
		for (Identifier resourceLocation : prepared.keySet()) {
			JsonElement json = prepared.get(resourceLocation);
			try {
				builder.put(resourceLocation, deserialise(json));
			} catch (Exception e) {
				LOGGER.error("Couldn't parse text builder {}", resourceLocation, e);
			}
		}
		this.textBuilders = builder.build();
	}

	private TextBuilder deserialise(JsonElement json) {
		if (json.isJsonArray()) {
			TextBuilder[] builders = GSON.fromJson(json, TextBuilder[].class);
			return new TextBuilderSequence(builders);
		}
		return GSON.fromJson(json, TextBuilder.class);
	}

	private static class TextBuilderSequence implements TextBuilder {
		private final TextBuilder[] builders;

		public TextBuilderSequence(TextBuilder[] builders) {
			this.builders = builders;
		}

		@Override
		public TextBuilderType getType() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String get() {
			StringBuilder stringBuilder = new StringBuilder();
			for(TextBuilder builder : this.builders) {
				stringBuilder.append(builder.get());
			}
			return stringBuilder.toString();
		}
	}
}
