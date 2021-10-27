package errorcraft.textbuilders.mixin.resource;

import errorcraft.textbuilders.access.resource.ServerResourceManagerExtenderAccess;
import errorcraft.textbuilders.text.builder.TextBuilderManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerExtender implements ServerResourceManagerExtenderAccess {
	private TextBuilderManager textBuilderManager;

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/util/registry/DynamicRegistryManager; Lnet/minecraft/server/command/CommandManager/RegistrationEnvironment; I)V")
	private void injectTextBuilderManagerIntoConstructor(DynamicRegistryManager registryManager, RegistrationEnvironment commandEnvironment, int functionPermissionLevel, CallbackInfo info) {
		this.textBuilderManager = new TextBuilderManager();
		((ServerResourceManagerAccessor)this).getResourceManager().registerReloader(this.textBuilderManager);
	}

	@Override
	public TextBuilderManager getTextBuilderManager() {
		return this.textBuilderManager;
	}
}
