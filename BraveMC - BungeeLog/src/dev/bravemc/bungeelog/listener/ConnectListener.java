package dev.bravemc.bungeelog.listener;

import dev.bravemc.bungeelog.BungeeLog;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectListener implements Listener {

	@EventHandler
	public void onConnect(ServerConnectEvent e) {
		final ProxiedPlayer pp = e.getPlayer();

		BungeeLog.getMySQL().createTable(pp.getName());
	}

}
