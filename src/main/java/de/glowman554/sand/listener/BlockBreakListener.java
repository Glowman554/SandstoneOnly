package de.glowman554.sand.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.glowman554.sand.Region;

public class BlockBreakListener implements Listener
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			return;
		}

		if (event.getBlock().getType() == Material.SANDSTONE)
		{
			return;
		}

		event.setCancelled(Region.check(event.getBlock().getLocation()));
	}

}
