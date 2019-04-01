package dev.bravemc.bungeelog.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dev.bravemc.bungeelog.BungeeLog;

public class SQLLogger {

	public void addLog(String s, String pName) {
		PreparedStatement ps;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(new Date());

		try {
			ps = BungeeLog.getMySQL().getConnection()
					.prepareStatement("INSERT INTO " + pName + " (Text, Uhrzeit) VALUES ('" + s + "', '" + time + "')");
			BungeeLog.getMySQL().update(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
