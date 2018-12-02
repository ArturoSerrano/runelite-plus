package net.runelite.client.plugins.aoewarnings;

import java.time.Instant;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;

public class AoeProjectile
{
    private final Instant startTime;
    private final LocalPoint targetPoint;
    private final AoeProjectileInfo aoeProjectileInfo;

    public AoeProjectile(Instant startTime, LocalPoint targetPoint, AoeProjectileInfo aoeProjectileInfo)
    {
        this.startTime = startTime;
        this.targetPoint = targetPoint;
        this.aoeProjectileInfo = aoeProjectileInfo;
    }

    public Instant getStartTime()
    {
        return startTime;
    }

    public LocalPoint getTargetPoint()
    {
        return targetPoint;
    }

    public AoeProjectileInfo getAoeProjectileInfo()
    {
        return aoeProjectileInfo;
    }
}
