package errorcraft.textbuilders.text.provider.providers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import errorcraft.textbuilders.mixin.server.MinecraftServerAccessor;
import errorcraft.textbuilders.text.builder.TextBuilder;
import errorcraft.textbuilders.text.builder.TextBuilderManager;
import errorcraft.textbuilders.text.provider.TextProvider;
import errorcraft.textbuilders.text.provider.TextProviderType;
import errorcraft.textbuilders.text.provider.TextProviderTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuilderTextProvider implements TextProvider {
	private final Logger LOGGER = LogManager.getLogger();
	private final Identifier resourceLocation;

	public BuilderTextProvider(Identifier resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public TextProviderType getType() {
		return TextProviderTypes.BUILDER;
	}

	@Override
	public String apply(ServerCommandSource serverCommandSource) {
		MinecraftServerAccessor serverAccessor = (MinecraftServerAccessor)(serverCommandSource.getServer());
		TextBuilderManager textBuilderManager = serverAccessor.getTextBuilderManager();
		TextBuilder textBuilder = textBuilderManager.get(this.resourceLocation);
		if (textBuilder == null) {
			LOGGER.warn("Unknown text builder " + this.resourceLocation);
			return "";
		}

		LootContext.Builder lootContextBuilder = new LootContext.Builder(serverCommandSource.getWorld())
				.parameter(LootContextParameters.ORIGIN, serverCommandSource.getPosition())
				.optionalParameter(LootContextParameters.THIS_ENTITY, serverCommandSource.getEntity());
		StringBuilder stringBuilder = new StringBuilder();
		textBuilder.accept(stringBuilder, lootContextBuilder.build(LootContextTypes.COMMAND));
		return stringBuilder.toString();
	}

	public static class Serialiser implements TextProvider.Serialiser<BuilderTextProvider> {
		@Override
		public void toJson(JsonObject json, BuilderTextProvider object, JsonSerializationContext context) {
			json.addProperty("builder", object.resourceLocation.toString());
		}

		@Override
		public BuilderTextProvider fromJson(JsonObject json, JsonDeserializationContext context) {
			Identifier resourceLocation = new Identifier(JsonHelper.getString(json, "builder"));
			return new BuilderTextProvider(resourceLocation);
		}
	}
}
