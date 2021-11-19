package pcus.rconchecker;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.event.server.RemoteServerCommandEvent;
import pcus.rconchecker.Events.e_RemoteCommands;

import java.lang.reflect.Field;

public class cmd_rcon implements CommandExecutor {
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1 && args[0].toLowerCase().equalsIgnoreCase("reload")){
			RconChecker.saveConfigs(true);
			RconChecker.loadConfig();

			String c_normal; if(RconChecker.tog_rconcheck) c_normal = "&a"; else c_normal = "&c";
			String c_cmd; if(RconChecker.tog_cmd) c_cmd = "&a"; else c_cmd = "&c";

			sender.sendMessage(RconChecker.color("&areload complete! &7("+c_normal+"normal"+"&7, "+c_cmd+"cmd&7)"));
		} else {
			sender.sendMessage(RconChecker.color("&cfor Rcon sender only!"));
		}

		if(!RconChecker.tog_cmd) return true;
		Bukkit.getScheduler().runTaskAsynchronously(RconChecker.getPlugin(), () -> {
			if (sender instanceof RemoteConsoleCommandSender) {
				String cmd = String.join(" ", args);

				if (RconChecker.logs && RconChecker.debug)
					RconChecker.getPlugin().getLogger().info("rconCmd command recieve -> " + cmd + " (" + args.length + ")");
				else
					RconChecker.getPlugin().getLogger().info("rconCmd command recieve -> " + cmd);

				if (new cls_checkPlayer().check(true, cmd)) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
				}
			}
		});
		return false;
	}
}
