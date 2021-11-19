package pcus.rconchecker.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.RemoteServerCommandEvent;
import pcus.rconchecker.RconChecker;
import pcus.rconchecker.cls_checkPlayer;

public class e_RemoteCommands implements Listener {
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};
	@EventHandler
	public void RemoteCommands(RemoteServerCommandEvent e) {
		if(!RconChecker.tog_rconcheck && RconChecker.tog_cmd && !e.getCommand().toLowerCase().contains("rcon ")){
			e.setCancelled(true);
			return;
		} else if(!RconChecker.tog_rconcheck){
			return;
		}
		Bukkit.getScheduler().runTaskAsynchronously(RconChecker.getPlugin(), () -> {
			final int st_max = e.getCommand().split(" ").length;

			if ((RconChecker.tog_rconcheck && RconChecker.tog_cmd && !e.getCommand().toLowerCase().contains("rcon ")) || (RconChecker.tog_rconcheck && !RconChecker.tog_cmd)) {
				if (RconChecker.logs && RconChecker.debug)
					RconChecker.getPlugin().getLogger().info("Rcon command recieve -> " + e.getCommand() + " (" + st_max + ")");
				else
					RconChecker.getPlugin().getLogger().info("Rcon command recieve -> " + e.getCommand());

				if (!new cls_checkPlayer().check(false, e.getCommand())) e.setCancelled(true);
			}
		});
	}
}
