package dev.bravemc.specsystem.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.dytanic.cloudnet.api.CloudAPI;
import dev.bravemc.specsystem.SpecSystem;

public class InteractListener implements Listener {

	@EventHandler
	public void onInteractSign(PlayerInteractEvent e) {

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {

				Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(1).equalsIgnoreCase("§7Zuschauen")) {

					String server = ChatColor.stripColor(s.getLine(2));

					if (CloudAPI.getInstance().getServerGroup(server).isMaintenance() == true) {
						e.getPlayer().sendMessage(SpecSystem.getPrefix() + "§c" + server + " §7ist in §cWartungen!");
						return;
					}

					if (CloudAPI.getInstance().getServerGroup(server) == null) {
						e.getPlayer().sendMessage(SpecSystem.getPrefix() + "§cEs ist ein Fehler aufgetreten!");
						e.getClickedBlock().setType(Material.AIR);
						return;
					}

					SpecSystem.getGUI().open(server, e.getPlayer());
				}
			}
		}
	}
}
