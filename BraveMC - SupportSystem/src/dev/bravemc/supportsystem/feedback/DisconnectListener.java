package dev.bravemc.supportsystem.feedback;

import java.util.UUID;

import dev.bravemc.supportsystem.SupportSystem;
import dev.bravemc.supportsystem.utils.UUIDFetcher;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener {

	@EventHandler
	public void onDisconnectByFeedback(ServerDisconnectEvent e) {
		final ProxiedPlayer pp = e.getPlayer();
		final UUID ppu = UUIDFetcher.getUUID(pp.getName());

		if (SupportSystem.getInstance().getSupportManager().isHelping(pp) && pp.hasPermission("system.support")) {
			ProxiedPlayer needsHelp = SupportSystem.getInstance()
					.getKey(SupportSystem.getInstance().getSupportManager().getInSupport(), pp);

			SupportSystem.getInstance().getSupportManager().removeInSupport(SupportSystem.getInstance()
					.getKey(SupportSystem.getInstance().getSupportManager().getInSupport(), pp));

			needsHelp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
					+ "§7Tut uns leid, der Helfer ist leider Offline gegangen!"));

			return;
		}

		if (SupportSystem.getInstance().getSupportManager().mustFeedback(ppu)) {
			SupportSystem.getInstance().getStats()
					.addNoteValue(SupportSystem.getInstance().getSupportManager().getMustFeedback().get(ppu), 1);
			SupportSystem.getInstance().getSupportManager().getMustFeedback().remove(ppu);
			return;
		}

		if (SupportSystem.getInstance().getSupportManager().getInSupport().containsKey(pp)
				&& !pp.hasPermission("system.support")) {

			final ProxiedPlayer helper = SupportSystem.getInstance().getSupportManager().getInSupport().get(pp);

			SupportSystem.getInstance().getSupportManager().removeInSupport(pp);

			if (helper != null) {
				helper.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
						+ "§7Der §9Support §7wurde geschlossen, da §9" + pp.getName() + " §7Offline gegangen ist!"));
				SupportSystem.getInstance().getStats().addSupport(UUIDFetcher.getUUID(helper.getName()), 1);
			}

		}
	}
}
