package errorcraft.textbuilders.text.provider;

import errorcraft.textbuilders.mixin.registry.RegistryAccessor;
import errorcraft.textbuilders.text.provider.providers.StringTextProvider;
import net.minecraft.util.JsonSerializing;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class TextProviderTypes {
	public static final RegistryKey<Registry<TextProviderType>> TEXT_PROVIDER_TYPE_KEY = RegistryAccessor.createRegistryKey("text_provider_type");
	public static final Registry<TextProviderType> TEXT_PROVIDER_TYPE = RegistryAccessor.create(TEXT_PROVIDER_TYPE_KEY, () -> null);

	public static final TextProviderType STRING = register("string", new StringTextProvider.Serialiser());

	public static Object createGsonAdapter() {
		return JsonSerializing.createSerializerBuilder(TEXT_PROVIDER_TYPE, "type", "type", TextProvider::getType).build();
	}

	private static TextProviderType register(String key, TextProvider.Serialiser<? extends TextProvider> serialiser) {
		return Registry.register(TEXT_PROVIDER_TYPE, key, new TextProviderType(serialiser));
	}
}
