package dev.bravemc.supportsystem.commands;

import dev.bravemc.supportsystem.SupportSystem;
import dev.bravemc.supportsystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SupportCMD extends Command {

	public SupportCMD() {
		super("support");

	}

	@Override
	public void execute(CommandSender s, String[] a) {
		if (!(s instanceof ProxiedPlayer))
			return;

		final ProxiedPlayer pp = (ProxiedPlayer) s;
		if (a.length == 0) {
			if (pp.hasPermission("system.support")) {
				pp.sendMessage(TextComponent.fromLegacyText("§7Du kannst keine §9Support§7-§9Anfrage §7erstellen."));

				if (pp.hasPermission("system.support.accept")) {
					pp.sendMessage(
							TextComponent.fromLegacyText("§7/support accept (Spieler) §8» §7Nehme ein Support an."));
				}
				if (pp.hasPermission("system.support.delete")) {
					pp.sendMessage(TextComponent
							.fromLegacyText("§7/support delete (Teamler) §8» §7Lösche Stats eines Teamler."));
				}

				if (pp.hasPermission("system.support.clear")) {
					pp.sendMessage(
							TextComponent.fromLegacyText("§7/support clear §8» §7Lösche alle Support-Anfragen."));
				}

				if (pp.hasPermission("system.support.list")) {
					pp.sendMessage(
							TextComponent.fromLegacyText("§7/support list §8» §7Liste alle Support-Anfragen auf."));
				}

				if (pp.hasPermission("system.support.stats")) {
					pp.sendMessage(
							TextComponent.fromLegacyText("§7/support stats §8» §7Zeige deine Support-Stats an."));
				}

				if (pp.hasPermission("system.support.stats.other")) {
					pp.sendMessage(TextComponent.fromLegacyText(
							"§7/support stats (Teamler) §8» §7Zeige die Support-Stats eines Teamlers an."));
				}

				return;
			}

			if (SupportSystem.getInstance().getSupportManager().getMustFeedback().containsKey(pp.getUniqueId())) {
				pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
						+ "§7Du musst erst das §9Supportgespräch §7von zuvor bewerten."));
				return;
			}
			if (SupportSystem.getInstance().getSupportManager().getNeedSupport().contains(pp)) {
				pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
						+ "§7Du bist bereits in der §9Support§7-§9Warteschlange§7."));
				return;
			}

			if (SupportSystem.getInstance().getSupportManager().getInSupport().containsKey(pp)) {
				pp.sendMessage(TextComponent.fromLegacyText(
						SupportSystem.getInstance().getPrefix() + "§7Du bekommst bereits §9Support§7."));
				return;
			}

			SupportSystem.getInstance().getSupportManager().addNeedSupport(pp);
			pp.sendMessage(TextComponent.fromLegacyText(
					SupportSystem.getInstance().getPrefix() + "§7Du hast die §9Support§7-§9Warteschlange §7betreten."));

			for (ProxiedPlayer pps : SupportSystem.getInstance().getSupportManager().getLoggedIn()) {
				if (pps.hasPermission("system.support")) {

					pps.sendMessage(TextComponent.fromLegacyText("§7§m--------------------------"));
					pps.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§9" + pp.getName() + " §7benötigt §9Hilfe§7."));
					pps.sendMessage(TextComponent.fromLegacyText("§7§m--------------------------"));
				}

			}
		} else if (a.length == 1) {

			if (a[0].equalsIgnoreCase("clear")) {
				if (!pp.hasPermission("system.support.accept")) {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));
					return;
				}

				SupportSystem.getInstance().getSupportManager().getNeedSupport().clear();
				pp.sendMessage(TextComponent.fromLegacyText(
						SupportSystem.getInstance().getPrefix() + "§7Du hast die §9Support§7-§9Anfragen §7gelöscht."));

			} else if (a[0].equalsIgnoreCase("list")) {
				if (!pp.hasPermission("system.support.list")) {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));
					return;
				}

				if (SupportSystem.getInstance().getSupportManager().getNeedSupport().size() < 1) {
					pp.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§7Es gibt keine §9Support§7-§9Anfragen§7."));
					return;
				}

				pp.sendMessage(TextComponent.fromLegacyText(
						SupportSystem.getInstance().getPrefix() + "§7Liste der offenen §9Support§7-§9Anfragen§7:"));
				for (ProxiedPlayer ppn : SupportSystem.getInstance().getSupportManager().getNeedSupport()) {
					pp.sendMessage(TextComponent.fromLegacyText("§8» §7" + ppn.getName()));
				}

			} else if (a[0].equalsIgnoreCase("close")) {
				if (!pp.hasPermission("system.support.accept")) {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));
					return;
				}

				if (!SupportSystem.getInstance().getSupportManager().isHelping(pp)) {
					pp.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§7Du bist in keinem §9Support§7."));
					return;
				}

				ProxiedPlayer ppn = SupportSystem.getInstance()
						.getKey(SupportSystem.getInstance().getSupportManager().getInSupport(), pp);

				SupportSystem.getInstance().getSupportManager().removeInSupport(ppn);

				pp.sendMessage(TextComponent.fromLegacyText(
						SupportSystem.getInstance().getPrefix() + "§7Du hast den §9Support §7geschlossen!"));
				ppn.sendMessage(TextComponent.fromLegacyText(
						SupportSystem.getInstance().getPrefix() + "§7Der §9Support §7wurde geschlossen!"));

				SupportSystem.getInstance().getStats().addSupport(UUIDFetcher.getUUID(pp.getName()), 1);

				SupportSystem.getInstance().getSupportManager().getMustFeedback()
						.put(UUIDFetcher.getUUID(ppn.getName()), UUIDFetcher.getUUID(pp.getName()));

				ppn.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
						+ "§aSchreibe eine Zahl von §71-6 §ain den Chat, um den Support zu bewerten."));
			} else if (a[0].equalsIgnoreCase("stats")) {
				if (pp.hasPermission("system.support.stats")) {

					pp.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§7Stats von §9" + pp.getName() + "§7:"));
					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Support-Anzahl: §9"
							+ SupportSystem.getInstance().getStats().getSupports(UUIDFetcher.getUUID(pp.getName()))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Support-Durchschnitt: §9" + SupportSystem
							.getInstance().getStats().getDurchschnitt(UUIDFetcher.getUUID(pp.getName()))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Gesamte Feedbacks: §9"
							+ SupportSystem.getInstance().getStats().getFeedbacks(UUIDFetcher.getUUID(pp.getName()))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-1: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 1)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-2: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 2)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-3: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 3)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-4: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 4)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-5: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 5)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-6: §9" + SupportSystem.getInstance()
							.getStats().getNoteValue(UUIDFetcher.getUUID(pp.getName()), 6)));

				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}

			} else if (a[0].equalsIgnoreCase("login")) {
				if (pp.hasPermission("system.support.login")) {
					if (SupportSystem.getInstance().getSupportManager().getLoggedIn().contains(pp)) {
						pp.sendMessage(TextComponent.fromLegacyText(
								SupportSystem.getInstance().getPrefix() + "§7Du bist bereits §aeingeloggt§7."));
						return;
					}
					SupportSystem.getInstance().getSupportManager().getLoggedIn().add(pp);
					pp.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§7Du hast dich §aeingeloggt§7."));
				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}
			} else if (a[0].equalsIgnoreCase("logout")) {
				if (pp.hasPermission("system.support.login")) {
					if (!SupportSystem.getInstance().getSupportManager().getLoggedIn().contains(pp)) {
						pp.sendMessage(TextComponent.fromLegacyText(
								SupportSystem.getInstance().getPrefix() + "§7Du bist bereits §causgeloggt§7."));
						return;
					}
					SupportSystem.getInstance().getSupportManager().getLoggedIn().remove(pp);
					pp.sendMessage(TextComponent.fromLegacyText(
							SupportSystem.getInstance().getPrefix() + "§7Du hast dich §causgeloggt§7."));
				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}
			}
		} else if (a.length == 2) {

			if (a[0].equalsIgnoreCase("accept")) {
				if (pp.hasPermission("system.support.accept")) {

					final ProxiedPlayer ppn = ProxyServer.getInstance().getPlayer(a[1]);

					if (ppn == null) {
						pp.sendMessage(TextComponent.fromLegacyText(
								SupportSystem.getInstance().getPrefix() + "§7Dieser Spieler ist nicht online."));
						return;
					}
					if (SupportSystem.getInstance().getSupportManager().isHelping(pp)) {
						pp.sendMessage(TextComponent.fromLegacyText(
								SupportSystem.getInstance().getPrefix() + "§7Du bist bereits in einem §9Support§7."));
						return;
					}

					if (!SupportSystem.getInstance().getSupportManager().getNeedSupport().contains(ppn)) {
						pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
								+ "§7Dieser Spieler ist nicht in der §9Warteschlange§7."));
						return;
					}

					SupportSystem.getInstance().getSupportManager().removeNeedSupport(ppn);
					SupportSystem.getInstance().getSupportManager().addInSupport(ppn, pp);

					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
							+ "§7Du bist nun mit §9" + ppn.getName() + " §7im §9Support§7."));

					ppn.sendMessage(TextComponent.fromLegacyText("§7§m----------------------------"));
					ppn.sendMessage(TextComponent.fromLegacyText("§7Du bist nun im §9Support§7."));
					ppn.sendMessage(TextComponent.fromLegacyText("§7Helfer: §9" + pp.getName()));
					ppn.sendMessage(TextComponent.fromLegacyText("§7§m----------------------------"));

				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}

			} else if (a[0].equalsIgnoreCase("stats")) {
				if (pp.hasPermission("system.support.stats.other")) {

					pp.sendMessage(TextComponent
							.fromLegacyText(SupportSystem.getInstance().getPrefix() + "§7Stats von §9" + a[1] + "§7:"));
					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Support-Anzahl: §9"
							+ SupportSystem.getInstance().getStats().getSupports(UUIDFetcher.getUUID(a[1]))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Support-Durchschnitt: §9"
							+ SupportSystem.getInstance().getStats().getDurchschnitt(UUIDFetcher.getUUID(a[1]))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Gesamte Feedbacks: §9"
							+ SupportSystem.getInstance().getStats().getFeedbacks(UUIDFetcher.getUUID(a[1]))));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-1: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 1)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-2: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 2)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-3: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 3)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-4: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 4)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-5: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 5)));

					pp.sendMessage(TextComponent.fromLegacyText("§8» §7Note-6: §9"
							+ SupportSystem.getInstance().getStats().getNoteValue(UUIDFetcher.getUUID(a[1]), 6)));

				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}

			} else if (a[0].equalsIgnoreCase("delete")) {
				if (pp.hasPermission("system.support.delete")) {

					SupportSystem.getInstance().getStats().resetUser(UUIDFetcher.getUUID(a[1]));

					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getPrefix()
							+ "§7Die §9Stats §7von §9" + a[1] + " §7wurden gelöscht!"));
				} else {
					pp.sendMessage(TextComponent.fromLegacyText(SupportSystem.getInstance().getNoPermission()));

				}

			}

		}
		return;
	}

}
