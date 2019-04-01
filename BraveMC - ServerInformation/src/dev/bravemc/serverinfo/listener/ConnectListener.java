package dev.bravemc.serverinfo.listener;

import dev.bravemc.serverinfo.ServerInfo;

import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ConnectListener implements Listener {

	@EventHandler
	public void onConnect(ServerConnectEvent e) {
		String sName = e.getTarget().getName();
		String[] splitted = sName.split("-");
		ServerInfo.getInstance().getServerStats().addJoin(splitted[0]);

	}
}
