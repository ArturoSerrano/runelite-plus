package net.runelite.client.plugins.vorkathfireball;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;
public class VorkathFireballOverlay extends Overlay
{
    private final Client client;
    private final VorkathFireballConfig config;
    private final VorkathFireballPlugin plugin;
    private static final Color COLOR_FULL_DMG = Color.RED;
    private static final Color COLOR_NO_DMG = Color.GREEN;
    @Inject
    private VorkathFireballOverlay(Client client, VorkathFireballConfig config, VorkathFireballPlugin plugin)
    {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.LOW);
    }
    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isInArea())
        {
            return null;
        }
        if (config.highlightFirebomb() && plugin.getFirebombLocation() != null)
        {
            LocalPoint loc = plugin.getFirebombLocation();
            renderTile(graphics, loc, COLOR_FULL_DMG);
        }
        if (config.highlightFirebombSafe() && plugin.getFirebombLocation() != null)
        {
            LocalPoint loc = plugin.getFirebombLocation();
            renderTileArea(graphics, loc, COLOR_NO_DMG);
        }
        return null;
    }
    private void renderTileArea(final Graphics2D graphics, final LocalPoint dest, final Color color)
    {
        if (dest == null)
        {
            return;
        }
        WorldArea area = client.getLocalPlayer().getWorldArea();
        if (area == null)
        {
            return;
        }
        for (int dx = -2; dx <= 2; dx++)
        {
            for (int dy = -2; dy <= 2; dy++)
            {
                if ((Math.abs(dx) == 1 || dx == 0) && (Math.abs(dy) == 1 || dy == 0))
                {
                    continue;
                }
                LocalPoint lp = new LocalPoint(
                        dest.getX() + dx * Perspective.LOCAL_TILE_SIZE + dx * Perspective.LOCAL_TILE_SIZE * (area.getWidth() - 1) / 2,
                        dest.getY() + dy * Perspective.LOCAL_TILE_SIZE + dy * Perspective.LOCAL_TILE_SIZE * (area.getWidth() - 1) / 2);
                Polygon poly = Perspective.getCanvasTilePoly(client, lp);
                if (poly == null)
                {
                    return;
                }
                OverlayUtil.renderPolygon(graphics, poly, color);
            }
        }
    }
    private void renderTile(final Graphics2D graphics, final LocalPoint dest, final Color color)
    {
        if (dest == null)
        {
            return;
        }
        final Polygon poly = Perspective.getCanvasTilePoly(client, dest);
        if (poly == null)
        {
            return;
        }
        OverlayUtil.renderPolygon(graphics, poly, color);
    }
}
