package errorcraft.textbuilders.mixin.server;

import errorcraft.textbuilders.access.server.MinecraftServerExtenderAccess;
import errorcraft.textbuilders.mixin.resource.ServerResourceManagerAccessor;
import errorcraft.textbuilders.text.builder.TextBuilderManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftServer.class)
public class MinecraftServerExtender implements MinecraftServerExtenderAccess {
	@Override
	public TextBuilderManager getTextBuilderManager() {
		MinecraftServerAccessor thisAccessor = (MinecraftServerAccessor)this;
		ServerResourceManagerAccessor serverResourceManagerAccessor = (ServerResourceManagerAccessor)(thisAccessor.getServerResourceManager());
		return serverResourceManagerAccessor.getTextBuilderManager();
	}
}
