package dev.bravemc.supportsystem.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import dev.bravemc.supportsystem.SupportSystem;

public class SupportStats {

	private SupportSystem plugin;

	public SupportStats(SupportSystem plugin) {
		this.plugin = plugin;
	}

	public double getDurchschnitt(UUID uuid) {
		try {
			ResultSet rs = plugin.getMySQL().query("SELECT Durchschnitt FROM supportStats WHERE UUID = '" + uuid + "'");

			if (rs.next()) {
				return rs.getDouble("Durchschnitt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(-1);
	}

	public Integer getSupports(UUID uuid) {
		try {
			ResultSet rs = plugin.getMySQL().query("SELECT Supports FROM supportStats WHERE UUID = '" + uuid + "'");

			if (rs.next()) {
				return rs.getInt("Supports");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(-1);
	}

	public void addSupport(UUID uuid, int supports) {

		int currentSupport = getSupports(uuid);
		try {
			if (isExisting(uuid)) {
				PreparedStatement ps = plugin.getMySQL().getConnection()
						.prepareStatement("UPDATE supportStats SET Supports = ? WHERE UUID = ?");

				ps.setInt(1, currentSupport + supports);
				ps.setString(2, uuid.toString());

				plugin.getMySQL().update(ps);

			} else {
				createUser(uuid);

				addSupport(uuid, supports);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateDurchschnitt(UUID uuid) {
		try {
			if (isExisting(uuid)) {
				PreparedStatement ps = plugin.getMySQL().getConnection()
						.prepareStatement("UPDATE supportStats SET Durchschnitt = ? WHERE UUID = ?");

				ps.setDouble(1,
						((getNoteValue(uuid, 1) * 1) + (getNoteValue(uuid, 2) * 2) + (getNoteValue(uuid, 3) * 3)
								+ (getNoteValue(uuid, 4) * 4) + (getNoteValue(uuid, 5) * 5)
								+ (getNoteValue(uuid, 6) * 6)) / getFeedbacks(uuid) * 100.0D / 100.0D);

				ps.setString(2, uuid.toString());

				plugin.getMySQL().update(ps);
			} else {
				createUser(uuid);

				updateDurchschnitt(uuid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateFeedbacks(UUID uuid) {
		try {
			if (isExisting(uuid)) {
				PreparedStatement ps = plugin.getMySQL().getConnection()
						.prepareStatement("UPDATE supportStats SET Gesamt = ? WHERE UUID = ?");

				ps.setDouble(1, getNoteValue(uuid, 1) + getNoteValue(uuid, 2) + getNoteValue(uuid, 3)
						+ getNoteValue(uuid, 4) + getNoteValue(uuid, 5) + getNoteValue(uuid, 6));

				ps.setString(2, uuid.toString());

				plugin.getMySQL().update(ps);
			} else {
				createUser(uuid);

				updateFeedbacks(uuid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer getFeedbacks(UUID uuid) {
		try {
			ResultSet rs = plugin.getMySQL().query("SELECT Gesamt FROM supportStats WHERE UUID = '" + uuid + "'");

			if (rs.next()) {
				return rs.getInt("Gesamt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(-1);
	}

	public void addNoteValue(UUID uuid, int note) {

		int currentNotes = getNoteValue(uuid, note);
		try {
			if (isExisting(uuid)) {
				PreparedStatement ps = plugin.getMySQL().getConnection()
						.prepareStatement("UPDATE supportStats SET " + intToText(note) + " = ? WHERE UUID = ?");

				ps.setInt(1, currentNotes + 1);

				ps.setString(2, uuid.toString());

				plugin.getMySQL().update(ps);

				updateFeedbacks(uuid);
				updateDurchschnitt(uuid);
			} else {
				createUser(uuid);

				addNoteValue(uuid, note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetUser(UUID uuid) {
		try {
			if (isExisting(uuid)) {

				PreparedStatement ps = plugin.getMySQL().getConnection()
						.prepareStatement("DELETE FROM supportStats WHERE UUID = '" + uuid + "'");

				plugin.getMySQL().update(ps);
			} else {
				createUser(uuid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createUser(UUID uuid) {
		try {
			if (isExisting(uuid))
				return;

			PreparedStatement ps = plugin.getMySQL().getConnection().prepareStatement(
					"INSERT INTO supportStats (UUID, Durchschnitt, Supports, Eins, Zwei, Drei, Vier, Fuenf, Sechs, Gesamt) VALUES ('"
							+ uuid + "',0,0,0,0,0,0,0,0,0)");

			plugin.getMySQL().update(ps);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer getNoteValue(UUID uuid, int note) {
		try {
			ResultSet rs = plugin.getMySQL()
					.query("SELECT " + intToText(note) + " FROM supportStats WHERE UUID = '" + uuid + "'");

			if (rs.next()) {
				return rs.getInt(intToText(note));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(-1);
	}

	public boolean isExisting(UUID uuid) {
		try {
			PreparedStatement ps = plugin.getMySQL().getConnection().prepareStatement(
					"SELECT Durchschnitt, Supports, Eins, Zwei, Drei, Vier, Fuenf, Sechs, Gesamt FROM supportStats WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();

			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String intToText(int i) {

		switch (i) {
		case 1:
			return "Eins";
		case 2:
			return "Zwei";
		case 3:
			return "Drei";
		case 4:
			return "Vier";
		case 5:
			return "Fuenf";
		case 6:
			return "Sechs";

		default:
			return "FEHLER";
		}
	}

}