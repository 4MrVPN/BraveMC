package dev.bravemc.serverinfo.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dev.bravemc.serverinfo.ServerInfo;

public class ServerStats {

	public void addJoin(String groupName) {

		try {
			if (isExisting(groupName) == true) {
				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Taeglich = ?, Woechentlich = ?, Monatlich = ?, Jaehrlich = ?, Gesamt = ? WHERE Gruppenname = '"
								+ groupName + "'");

				if (getDailyEnd(groupName) <= System.currentTimeMillis()) {
					resetDaily(groupName);
				}

				if (getDailyEnd(groupName) == 0) {
					setDailyEnd(groupName, 86400000L);
				}

				if (getWeeklyEnd(groupName) <= System.currentTimeMillis()) {
					resetWeekly(groupName);
				}

				if (getWeeklyEnd(groupName) == 0) {
					setWeeklyEnd(groupName, 604800000L);
				}

				if (getWeeklyEnd(groupName) <= System.currentTimeMillis()) {
					resetMonthly(groupName);
				}

				if (getMonthlyEnd(groupName) == 0) {
					setMonthlyEnd(groupName, 2592000000L);
				}

				if (getYearlyEnd(groupName) <= System.currentTimeMillis()) {
					resetYearly(groupName);
				}

				if (getYearlyEnd(groupName) == 0) {
					setYearlyEnd(groupName, 31536000000L);
				}

				ps.setInt(1, getDailyJoin(groupName) + 1);
				ps.setInt(2, getWeeklyJoin(groupName) + 1);
				ps.setInt(3, getMonthlyJoin(groupName) + 1);
				ps.setInt(4, getYearlyJoin(groupName) + 1);
				ps.setInt(5, getEverJoin(groupName) + 1);

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);

				addJoin(groupName);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Integer getDailyJoin(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Taeglich FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return rs.getInt("Taeglich");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Long getDailyEnd(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Taeglich_End FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return Long.valueOf(rs.getLong("Taeglich_End"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;

	}

	public void setDailyEnd(String groupName, long millis) {
		try {
			if (isExisting(groupName) == true) {

				long current = System.currentTimeMillis();
				long end = current + millis;

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Taeglich_End = " + end + " WHERE Gruppenname = '" + groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);

				setDailyEnd(groupName, millis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void resetDaily(String groupName) {
		try {
			if (isExisting(groupName) == true) {

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection()
						.prepareStatement("UPDATE serverStats SET Taeglich = 0, Taeglich_End = 0 WHERE Gruppenname = '"
								+ groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);
				resetDaily(groupName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Integer getWeeklyJoin(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Woechentlich FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return rs.getInt("Woechentlich");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Long getWeeklyEnd(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Woechentlich_End FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return Long.valueOf(rs.getLong("Woechentlich_End"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;

	}

	public void setWeeklyEnd(String groupName, long millis) {
		try {
			if (isExisting(groupName) == true) {

				long current = System.currentTimeMillis();
				long end = current + millis;

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection()
						.prepareStatement("UPDATE serverStats SET Woechentlich_End = " + end + " WHERE Gruppenname = '"
								+ groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);

				setDailyEnd(groupName, millis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void resetWeekly(String groupName) {
		try {
			if (isExisting(groupName) == true) {

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Woechentlich = 0, Woechentlich_End = 0 WHERE Gruppenname = '"
								+ groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);
				resetWeekly(groupName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Integer getMonthlyJoin(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Monatlich FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return rs.getInt("Monatlich");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Long getMonthlyEnd(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Monatlich_End FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return Long.valueOf(rs.getLong("Monatlich_End"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;

	}

	public void setMonthlyEnd(String groupName, long millis) {
		try {
			if (isExisting(groupName) == true) {

				long current = System.currentTimeMillis();
				long end = current + millis;

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Monatlich_End = " + end + " WHERE Gruppenname = '" + groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);

				setDailyEnd(groupName, millis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void resetMonthly(String groupName) {
		try {
			if (isExisting(groupName) == true) {

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Monatlich = 0, Monatlich_End = 0 WHERE Gruppenname = '" + groupName
								+ "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);
				resetMonthly(groupName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Integer getYearlyJoin(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Jaehrlich FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return rs.getInt("Jaehrlich");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Long getYearlyEnd(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Jaehrlich_End FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return Long.valueOf(rs.getLong("Jaehrlich_End"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;

	}

	public void setYearlyEnd(String groupName, long millis) {
		try {
			if (isExisting(groupName) == true) {

				long current = System.currentTimeMillis();
				long end = current + millis;

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Jaehrlich_End = " + end + " WHERE Gruppenname = '" + groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);

				setDailyEnd(groupName, millis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void resetYearly(String groupName) {
		try {
			if (isExisting(groupName) == true) {

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
						"UPDATE serverStats SET Jaehrlich = 0, Jaehrlich_End = 0 WHERE Gruppenname = '" + groupName
								+ "'");

				ServerInfo.getInstance().getMySQL().update(ps);

			} else {
				createGroup(groupName);
				resetYearly(groupName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Integer getEverJoin(String groupName) {
		try {
			ResultSet rs = ServerInfo.getInstance().getMySQL()
					.query("SELECT Gesamt FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			if (rs.next()) {
				return rs.getInt("Gesamt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public void resetGroup(String groupName) {
		try {
			if (isExisting(groupName) == true) {

				PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection()
						.prepareStatement("DELETE FROM serverStats WHERE Gruppenname = '" + groupName + "'");

				ServerInfo.getInstance().getMySQL().update(ps);
			} else {
				createGroup(groupName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createGroup(String groupName) {
		try {
			if (isExisting(groupName))
				return;

			PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection().prepareStatement(
					"INSERT INTO serverStats (Gruppenname, Taeglich, Taeglich_End, Woechentlich, Woechentlich_End, Monatlich, Monatlich_End, Jaehrlich, Jaehrlich_End, Gesamt) VALUES ('"
							+ groupName + "',0,0,0,0,0,0,0,0,0)");

			ServerInfo.getInstance().getMySQL().update(ps);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isExisting(String groupName) {
		try {
			PreparedStatement ps = ServerInfo.getInstance().getMySQL().getConnection()
					.prepareStatement("SELECT * FROM serverStats WHERE Gruppenname = '" + groupName + "'");

			ResultSet rs = ps.executeQuery();

			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}