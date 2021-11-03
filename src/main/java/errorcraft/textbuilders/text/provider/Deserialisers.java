package errorcraft.textbuilders.text.provider;

import com.google.gson.GsonBuilder;

public class Deserialisers {
	public static GsonBuilder createTextProviderSerialiser() {
		return new GsonBuilder().registerTypeHierarchyAdapter(TextProvider.class, TextProviderTypes.createGsonAdapter());
	}
}
