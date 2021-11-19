package pcus.rconchecker.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pcus.rconchecker.RconChecker;

import java.io.File;
import java.io.IOException;

public class c_cmd {
	private String[] lsnt = {
			"/////******** Open Source Plugin By Pluto ********/////",
			"/////******** https://www.facebook.com/plganimation ********/////"
	};

	private File conf;
	private static FileConfiguration customConfig;

	public FileConfiguration getConfig() {
		return this.customConfig;
	}

	public void loadConfigu() {
		conf = new File(RconChecker.getPlugin().getDataFolder(), "/rconCommands.yml");
		if (!conf.exists()) {
			conf.getParentFile().mkdirs();
			RconChecker.getPlugin().saveResource("rconCommands.yml", false);
			RconChecker.console.sendMessage(RconChecker.color(" &7haven't rconCommands Configuration file, &acreate new."));
		}

		customConfig = new YamlConfiguration();
		try {
			customConfig.load(conf);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public void save(){
		conf = new File(RconChecker.getPlugin().getDataFolder(), "/rconCommands.yml");
		try {
			customConfig.options().copyDefaults(true);
			customConfig.save(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
