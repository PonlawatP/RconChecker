package pcus.rconchecker.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pcus.rconchecker.RconChecker;
import pcus.rconchecker.configs.c_cmd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class e_onjoin implements Listener {
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};
	@EventHandler
	public void onjoin(PlayerJoinEvent e){
		Bukkit.getScheduler().runTaskLaterAsynchronously(RconChecker.getPlugin(), () -> {
			List<String> cmds = new c_cmd().getConfig().getStringList("RconCommands");
			List<String> cmdsH = new c_cmd().getConfig().getStringList("RconHistory");
			List<String> cmds_forthisplayer = new ArrayList<>();
			LocalDateTime DateRaw = LocalDateTime.now();
			DateTimeFormatter Date = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
			String formattedDate = DateRaw.format(Date);

			for(String s : cmds){
				for(String st : s.split(" ")){
					if(e.getPlayer().getName().toLowerCase().equalsIgnoreCase(st.toLowerCase())){
						cmds_forthisplayer.add(s);
						continue;
					}
				}
			}

			if(cmds_forthisplayer.size() > 0) {
				e.getPlayer().sendMessage(RconChecker.color(RconChecker.msgs.get(0).replaceAll("<amount>", String.valueOf(cmds_forthisplayer.size())).replaceAll("<cooldown>", String.valueOf(RconChecker.cd_runafter))));

				Bukkit.getScheduler().runTaskLaterAsynchronously(RconChecker.getPlugin(), () -> {
					if(Bukkit.getPlayerExact(e.getPlayer().getName()) != null){
						for (String st : cmds_forthisplayer) {
							cmds.remove(st);

							Bukkit.getScheduler().runTask(RconChecker.getPlugin(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), st));
							if(RconChecker.save_history) cmdsH.add(formattedDate + " -> '" + st + "'");

						}

						if(RconChecker.save_history) new c_cmd().getConfig().set("RconHistory", cmdsH);
						new c_cmd().getConfig().set("RconCommands", cmds);
						new c_cmd().save();
					}
				}, 20 * RconChecker.cd_runafter);
			} else {
				if(!RconChecker.msgs.get(1).equalsIgnoreCase("")) e.getPlayer().sendMessage(RconChecker.color(RconChecker.msgs.get(1)));
			}
		}, 20 * RconChecker.cd_showmsg);
	}
}
