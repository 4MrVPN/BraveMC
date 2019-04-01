package dev.bravemc.serverinfo.commands;

import dev.bravemc.serverinfo.ServerInfo;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class SInfoCMD extends Command {

	public SInfoCMD() {
		super("sinfo");
	}

	@Override
	public void execute(CommandSender s, String[] a) {
		if (a.length == 1) {

			if (!s.hasPermission("system.sinfo")) {
				s.sendMessage(TextComponent.fromLegacyText(ServerInfo.getNoPermission()));
				return;
			}
			String groupName = a[0];

			s.sendMessage(
					TextComponent.fromLegacyText(ServerInfo.getPrefix() + "§7Statistiken von §c" + groupName + "§7:"));

			s.sendMessage(TextComponent.fromLegacyText(
					"§8» §7Täglich: §c" + ServerInfo.getInstance().getServerStats().getDailyJoin(groupName)));

			s.sendMessage(TextComponent.fromLegacyText(
					"§8» §7Wöchentlich: §c" + ServerInfo.getInstance().getServerStats().getWeeklyJoin(groupName)));

			s.sendMessage(TextComponent.fromLegacyText(
					"§8» §7Monatlich: §c" + ServerInfo.getInstance().getServerStats().getMonthlyJoin(groupName)));

			s.sendMessage(TextComponent.fromLegacyText(
					"§8» §7Jährlich: §c" + ServerInfo.getInstance().getServerStats().getYearlyJoin(groupName)));

			s.sendMessage(TextComponent.fromLegacyText(
					"§8» §7Gesamt: §c" + ServerInfo.getInstance().getServerStats().getEverJoin(groupName)));

		} else if (a.length == 2) {
			if (a[1].equalsIgnoreCase("reset")) {
				if (!s.hasPermission("system.sinfo.reset")) {
					s.sendMessage(TextComponent.fromLegacyText(ServerInfo.getNoPermission()));
					return;
				}

				String groupName = a[0];

				ServerInfo.getInstance().getServerStats().resetGroup(groupName);
				s.sendMessage(TextComponent.fromLegacyText(
						ServerInfo.getPrefix() + "§7Du hast die §cStatistiken §7von §c" + groupName + " §7gelöscht."));

				return;
			}

		} else {

			if(s.hasPermission("system.sinfo") || s.hasPermission("system.sinfo.reset")) {
				s.sendMessage(
						TextComponent.fromLegacyText(ServerInfo.getPrefix() + "§7Hilfe:"));
			}
			if (s.hasPermission("system.sinfo")) {
				s.sendMessage(TextComponent.fromLegacyText(
						"§8» §7/sinfo (Servergruppe) §8» §7Zeige die Statistiken der Servergruppe an."));
			}

			if (s.hasPermission("system.sinfo.reset")) {
				s.sendMessage(TextComponent.fromLegacyText(
						"§8» §7/sinfo (Servergruppe) reset §8» §7Setze die Statistiken der Servergruppe zurück."));
			}
		}

	}

}
