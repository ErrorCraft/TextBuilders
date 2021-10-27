package errorcraft.textbuilders.text.builder;

import com.google.gson.GsonBuilder;

public class Deserialisers {
	private Deserialisers() {}

	public static GsonBuilder createTextBuilderSerialiser() {
		return new GsonBuilder().registerTypeHierarchyAdapter(TextBuilder.class, TextBuilderTypes.createGsonAdapter());
	}
}
