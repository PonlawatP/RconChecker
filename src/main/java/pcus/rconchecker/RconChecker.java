package pcus.rconchecker;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pcus.rconchecker.Events.e_RemoteCommands;
import pcus.rconchecker.Events.e_onjoin;
import pcus.rconchecker.configs.c_cmd;
import pcus.rconchecker.configs.c_version;

import java.util.ArrayList;
import java.util.List;

public final class RconChecker extends JavaPlugin {
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};
	private static RconChecker pl;
	public static CommandSender console = Bukkit.getServer().getConsoleSender();
	public static Boolean tog_rconcheck;
	public static Boolean logs;
	public static Boolean debug;
	public static Boolean save_history;
	public static Boolean tog_cmd;
	public static Boolean tog_update;
	public static int cd_showmsg;
	public static int cd_runafter;
	public static String pl_version;
	public static String git_version;
	public static String git_desc;
	public static String git_link;
	public static List<String> msgs = new ArrayList<>();

	@Override
	public void onEnable() {
		// Plugin startup logic
		pl = this;
		new c_cmd().loadConfigu();

		Bukkit.getScheduler().runTaskLater(this, () -> {
			loadConfig();

			Bukkit.getPluginManager().registerEvents(new e_RemoteCommands(), this);
			Bukkit.getPluginManager().registerEvents(new e_onjoin(), this);
			getCommand("rcon").setExecutor(new cmd_rcon());

		}, 10L);

			Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
				if(tog_update) {
					console.sendMessage(color("&7Checking update..."));
					c_version.load();
					git_version = c_version.getDescription(0);
					git_desc = c_version.getDescription(1);
					git_link = c_version.getDescription(2);

					if (!git_version.equalsIgnoreCase(pl_version)) {
						console.sendMessage(color("&eThis plugin have update!"));
						console.sendMessage(color("&b" + git_version));
						console.sendMessage(color("&7" + git_desc));
						console.sendMessage(color("&adownload at&7:&r " + git_link));
					} else {
						console.sendMessage(color("&aThis plugin is up to date!"));
					}
				}
			}, 30L);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		if (tog_rconcheck) saveConfigs(true);
	}

	public static RconChecker getPlugin(){
		return pl;
	}

	public static String color(String msg) {
		String coloredMsg = "";
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '&')
				coloredMsg += '§';
			else
				coloredMsg += msg.charAt(i);
		}
		return coloredMsg;
	}

	public static void saveConfigs(Boolean pre_load){
		if(pre_load) new c_cmd().loadConfigu();
		new c_cmd().save();
	}

	public static void loadConfig(){
		pl_version = new c_cmd().getConfig().getString("RconChecker.version");
		tog_update = new c_cmd().getConfig().getBoolean("RconChecker.update");
		tog_rconcheck = new c_cmd().getConfig().getBoolean("RconChecker.enable.normal");
		tog_cmd = new c_cmd().getConfig().getBoolean("RconChecker.enable.cmd");
		logs = new c_cmd().getConfig().getBoolean("RconChecker.logs.enable");
		save_history = new c_cmd().getConfig().getBoolean("RconChecker.logs.history");
		debug = new c_cmd().getConfig().getBoolean("RconChecker.logs.debug");
		cd_showmsg = new c_cmd().getConfig().getInt("RconChecker.timer.show-msg");
		cd_runafter = new c_cmd().getConfig().getInt("RconChecker.timer.run-after");
		msgs.add(0, color(new c_cmd().getConfig().getString("RconChecker.msg-has-stock")));
		msgs.add(1, color(new c_cmd().getConfig().getString("RconChecker.msg-no-stock")));
	}
}
