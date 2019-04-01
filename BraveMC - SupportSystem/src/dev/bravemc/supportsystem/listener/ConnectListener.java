package dev.bravemc.supportsystem.listener;

import dev.bravemc.supportsystem.SupportSystem;
import dev.bravemc.supportsystem.utils.UUIDFetcher;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectListener implements Listener {

	@EventHandler
	public void onConnect(ServerConnectEvent e) {
		if (e.getPlayer().hasPermission("system.support")) {
			if (!SupportSystem.getInstance().getStats().isExisting(UUIDFetcher.getUUID(e.getPlayer().getName()))) {
				SupportSystem.getInstance().getStats().createUser(UUIDFetcher.getUUID(e.getPlayer().getName()));
			}
		}
	}
}
