package errorcraft.textbuilders.text.provider;

import errorcraft.textbuilders.mixin.registry.RegistryAccessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class TextProviderTypes {
	public static final RegistryKey<Registry<TextProviderType>> TEXT_PROVIDER_TYPE_KEY = RegistryAccessor.createRegistryKey("text_provider_type");
	public static final Registry<TextProviderType> TEXT_PROVIDER_TYPE = RegistryAccessor.create(TEXT_PROVIDER_TYPE_KEY, () -> null);
}
