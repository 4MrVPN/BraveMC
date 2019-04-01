package dev.bravemc.specsystem.listener;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.dytanic.cloudnet.api.CloudAPI;
import dev.bravemc.specsystem.SpecSystem;

public class SignPlaceListener implements Listener {

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		final Player p = e.getPlayer();
		if (!p.hasPermission("system.specsystem.create")) {
			if (e.getLine(0).equalsIgnoreCase("[spec]")) {
				e.getBlock().setType(Material.AIR);
				p.sendMessage(SpecSystem.getNoPermission());
				return;
			}
		}

		if (e.getLine(1).isEmpty()) {
			p.sendMessage(SpecSystem.getPrefix() + "§7Gebe eine §cServergruppe §7an.");
			e.getBlock().setType(Material.AIR);
			return;
		}

		if (CloudAPI.getInstance().getServerGroup(e.getLine(1)) == null) {
			p.sendMessage(SpecSystem.getPrefix() + "§7Diese §cServergruppe §7existiert nicht: §c" + e.getLine(1));
			e.getBlock().setType(Material.AIR);
			return;
		}

		String server = e.getLine(1);
		e.setLine(0, "§7§m-------");
		e.setLine(1, "§7Zuschauen");
		e.setLine(2, "§c" + server);
		e.setLine(3, "§7§m-------");

		Sign s = (Sign) e.getBlock().getState();

		s.update(true);
	}

}
