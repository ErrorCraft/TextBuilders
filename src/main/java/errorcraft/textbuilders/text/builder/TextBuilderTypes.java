package errorcraft.textbuilders.text.builder;

import errorcraft.textbuilders.mixin.registry.RegistryAccessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class TextBuilderTypes {
	public static final RegistryKey<Registry<TextBuilderType>> TEXT_BUILDER_TYPE_KEY = RegistryAccessor.createRegistryKey("text_builder_type");
	public static final Registry<TextBuilderType> TEXT_BUILDER_TYPE = RegistryAccessor.create(TEXT_BUILDER_TYPE_KEY, () -> null);
}
