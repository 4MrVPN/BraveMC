package dev.bravemc.bungeelog.listener;

import dev.bravemc.bungeelog.BungeeLog;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(ChatEvent e) {
		final ProxiedPlayer pp = (ProxiedPlayer) e.getSender();

		BungeeLog.getSQLLogger().addLog(e.getMessage(), pp.getName());

	}

}
