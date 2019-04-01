package dev.bravemc.supportsystem.listener;

import dev.bravemc.supportsystem.SupportSystem;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	@EventHandler
	public void onChatSupport(ChatEvent e) {
		final ProxiedPlayer pp = (ProxiedPlayer) e.getSender();

		if (!e.getMessage().startsWith("/")) {

			if (SupportSystem.getInstance().getSupportManager().getInSupport().containsKey(pp)) {
				e.setCancelled(true);

				ProxiedPlayer helper = SupportSystem.getInstance().getSupportManager().getInSupport().get(pp);

				pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7Du" + " §8» §7" + e.getMessage()));

				helper.sendMessage(TextComponent
						.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7" + pp.getName() + " §8» §7" + e.getMessage()));

			}

			if (SupportSystem.getInstance().getSupportManager().getInSupport().containsValue(pp)) {
				e.setCancelled(true);

				final ProxiedPlayer ppn = SupportSystem.getInstance().getKey(SupportSystem.getInstance().getSupportManager().getInSupport(),
						pp);

				pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7Du" + " §8» §7" + e.getMessage()));

				ppn.sendMessage(TextComponent
						.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7" + pp.getName() + " §8» §7" + e.getMessage()));
			}
		}
	}
}
