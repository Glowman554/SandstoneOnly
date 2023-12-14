package de.glowman554.sand;

import org.bukkit.Location;

public class Region
{
	private final int startX;
	private final int startZ;
	private final int endX;
	private final int endZ;

	public Region(int startX, int startZ, int endX, int endZ)
	{
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
	}

	public boolean checkBounds(int x, int z)
	{
		return x >= startX && x <= endX && z >= startZ && z <= endZ;
	}

	@Override
	public String toString()
	{
		return String.format("%d,%d -> %d,%d", startX, startZ, endX, endZ);
	}

	public static boolean check(Location location)
	{
		if (SandMain.getInstance().getWorlds().containsKey(location.getWorld().getName()))
		{
			Region[] regions = SandMain.getInstance().getWorlds().get(location.getWorld().getName());

			for (Region region : regions)
			{
				if (region.checkBounds(location.getBlockX(), location.getBlockZ()))
				{
					return true;
				}
			}
		}

		return false;
	}
}
