package errorcraft.textbuilders.text.builder;

import com.google.gson.GsonBuilder;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.score.LootScoreProvider;
import net.minecraft.loot.provider.score.LootScoreProviderTypes;

public class Deserialisers {
	private Deserialisers() {}

	public static GsonBuilder createTextBuilderSerialiser() {
		return new GsonBuilder()
				.registerTypeHierarchyAdapter(TextBuilder.class, TextBuilderTypes.createGsonAdapter())
				.registerTypeHierarchyAdapter(LootScoreProvider.class, LootScoreProviderTypes.createGsonSerializer())
				.registerTypeHierarchyAdapter(LootContext.EntityTarget.class, new LootContext.EntityTarget.Serializer());
	}
}
