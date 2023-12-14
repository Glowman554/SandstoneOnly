package de.glowman554.sand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.sand.listener.BlockBreakListener;
import de.glowman554.sand.listener.BlockPlaceListener;

public class SandMain extends JavaPlugin
{
	private static SandMain instance;

	public SandMain()
	{
		instance = this;
	}

	private FileConfiguration config = getConfig();

	private HashMap<String, Region[]> worlds = new HashMap<>();

	private Region loadRegion(String id)
	{
		int startX = config.getInt("regions." + id + ".startX");
		int startZ = config.getInt("regions." + id + ".startZ");
		int endX = config.getInt("regions." + id + ".endX");
		int endZ = config.getInt("regions." + id + ".endZ");

		return new Region(startX, startZ, endX, endZ);
	}

	private void loadWorlds()
	{
		ConfigurationSection section = config.getConfigurationSection("worlds");
		for (String key : section.getKeys(false))
		{
			String world = key;
			worlds.put(world, ((List<String>) section.getList(key)).stream().map(this::loadRegion).toArray(Region[]::new));
		}
	}

	@Override
	public void onLoad()
	{
		config.addDefault("regions.region1.startX", 0);
		config.addDefault("regions.region1.startZ", 0);
		config.addDefault("regions.region1.endX", 100);
		config.addDefault("regions.region1.endZ", 100);

		config.addDefault("regions.region2.startX", 0);
		config.addDefault("regions.region2.startZ", 0);
		config.addDefault("regions.region2.endX", -100);
		config.addDefault("regions.region2.endZ", -100);

		config.addDefault("worlds.world", new String[] {"region1", "region2"});

		config.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable()
	{
		loadWorlds();

		for (String world : worlds.keySet())
		{
			getLogger().log(Level.INFO, world + ": " + String.join("; ", Arrays.stream(worlds.get(world)).map(Region::toString).toArray(String[]::new)));
		}

		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
	}

	public static SandMain getInstance()
	{
		return instance;
	}

	public HashMap<String, Region[]> getWorlds()
	{
		return worlds;
	}
}
