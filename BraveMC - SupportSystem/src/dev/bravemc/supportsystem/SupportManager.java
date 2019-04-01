package dev.bravemc.supportsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SupportManager {

	private ArrayList<ProxiedPlayer> needSupport = new ArrayList<>();
	private ArrayList<ProxiedPlayer> loggedIn = new ArrayList<>();
	private HashMap<ProxiedPlayer, ProxiedPlayer> inSupport = new HashMap<ProxiedPlayer, ProxiedPlayer>();
	private HashMap<UUID, UUID> mustFeedback = new HashMap<UUID, UUID>();

	public ArrayList<ProxiedPlayer> getNeedSupport() {
		return needSupport;
	}

	public HashMap<ProxiedPlayer, ProxiedPlayer> getInSupport() {
		return inSupport;
	}

	public void addNeedSupport(ProxiedPlayer ppn) {
		if (getNeedSupport().contains(ppn))
			return;

		getNeedSupport().add(ppn);
	}

	public void removeNeedSupport(ProxiedPlayer ppn) {
		if (!getNeedSupport().contains(ppn))
			return;

		getNeedSupport().remove(ppn);
	}

	public void removeInSupport(ProxiedPlayer ppn) {
		if (!getInSupport().containsKey(ppn))
			return;

		getInSupport().remove(ppn);
	}

	public void addInSupport(ProxiedPlayer ppn, ProxiedPlayer pph) {
		if (getInSupport().containsKey(ppn))
			return;

		getInSupport().put(ppn, pph);
	}

	public boolean isHelping(ProxiedPlayer pph) {
		return getInSupport().containsValue(pph);
	}

	public HashMap<UUID, UUID> getMustFeedback() {
		return mustFeedback;
	}

	public boolean mustFeedback(UUID pp) {
		return getMustFeedback().containsKey(pp);
	}

	public ArrayList<ProxiedPlayer> getLoggedIn() {
		return loggedIn;
	}
}
