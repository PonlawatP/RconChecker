package pcus.rconchecker;

import org.bukkit.Bukkit;
import pcus.rconchecker.configs.c_cmd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class cls_checkPlayer{
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};
	public Boolean check(Boolean from_cmd, String command){
		boolean fnl;
		int st_lnght = 1;
		int st_player = 0;
		for (String sa : command.split(" ")) {
			String ss = sa;
			if(ss.startsWith("&")) ss = sa.substring(2);
			String st = ss;
			if(Bukkit.getPlayerExact(st) == null){
				if(RconChecker.debug) RconChecker.getPlugin().getLogger().info(st_lnght + " : - " + st);
				st_lnght++;
			} else {
				if(RconChecker.debug) RconChecker.getPlugin().getLogger().info(st_lnght + " : '" + st + "' has player");
				st_lnght++;
				st_player++;
				break;
			}
		}
		if(((!from_cmd && RconChecker.tog_rconcheck && !RconChecker.tog_cmd) || (from_cmd && RconChecker.tog_rconcheck && RconChecker.tog_cmd) || (!RconChecker.tog_rconcheck && RconChecker.tog_cmd)) && st_player == 0){
			if(RconChecker.debug) RconChecker.getPlugin().getLogger().info("Not have player in the server");
			if(RconChecker.debug) RconChecker.getPlugin().getLogger().info("added command in to conf database");

			List<String> cmds = new c_cmd().getConfig().getStringList("RconCommands");

			cmds.add(command);

			new c_cmd().getConfig().set("RconCommands", cmds);
			fnl = false;
		} else {
			if(RconChecker.debug) RconChecker.getPlugin().getLogger().info("run Rcon complete!");

			List<String> cmdsH = new c_cmd().getConfig().getStringList("RconHistory");
			LocalDateTime DateRaw = LocalDateTime.now();
			DateTimeFormatter Date = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
			String formattedDate = DateRaw.format(Date);
			if(RconChecker.save_history) {
				cmdsH.add(formattedDate + " -> '" + command + "'");
				new c_cmd().getConfig().set("RconHistory", cmdsH);
			}
			fnl = true;
		}

		RconChecker.saveConfigs(false);
		return fnl;
	}
}
