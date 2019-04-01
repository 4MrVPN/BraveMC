package dev.bravemc.supportsystem.feedback;

import java.util.UUID;

import dev.bravemc.supportsystem.SupportSystem;
import dev.bravemc.supportsystem.utils.UUIDFetcher;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class FeedbackListener implements Listener {

	@EventHandler
	public void onChatByFeedback(ChatEvent e) {
		final ProxiedPlayer pp = (ProxiedPlayer) e.getSender();

		if (SupportSystem.getInstance().getSupportManager().mustFeedback(UUIDFetcher.getUUID(pp.getName()))) {

			if (!e.getMessage().startsWith("/")) {

				if (isNumeric(e.getMessage())) {

					if (Integer.parseInt(e.getMessage()) >= 1 && Integer.parseInt(e.getMessage()) <= 6) {

						UUID ppu = SupportSystem.getInstance().getSupportManager().getMustFeedback()
								.get(UUIDFetcher.getUUID(pp.getName()));

						pp.sendMessage(
								TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7Du hast §9"
										+ UUIDFetcher.getName(ppu) + " §7eine §a" + e.getMessage() + " §7gegeben."));

						SupportSystem.getInstance().getSupportManager().getMustFeedback().remove(pp.getUniqueId());
						SupportSystem.getInstance().getStats().addNoteValue(ppu, Integer.parseInt(e.getMessage()));
					} else {
						pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
								+ "§7Du kannst nur nach §cSchulnoten §7bewerten. (1-6)"));
					}
				}
				e.setCancelled(true);
			}

		}
	}

	public boolean isNumeric(String value) {
		try {
			int i = Integer.parseInt(value);
			return i < 100;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
