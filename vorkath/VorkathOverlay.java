package net.runelite.client.plugins.vorkath;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
class VorkathOverlay extends Overlay
{
    private static final int INFOBOX_DIMENSION_SIZE = 52;
    private final VorkathPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();
    private BufferedImage mergedImage;
    @Inject
    private SpriteManager spriteManager;
    @Inject
    private VorkathOverlay(final VorkathPlugin plugin)
    {
        this.plugin = plugin;
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        panelComponent.setOrientation(PanelComponent.Orientation.HORIZONTAL);
        panelComponent.setBackgroundColor(null);
        panelComponent.setBorder(new Rectangle());
        panelComponent.setPreferredSize(new Dimension(INFOBOX_DIMENSION_SIZE, INFOBOX_DIMENSION_SIZE));
    }
    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isInArea())
        {
            return null;
        }
        panelComponent.getChildren().clear();
        if (plugin.getVorkathState() == VorkathState.TICKER_FIRE_PHASE)
        {
            drawTickerPhasePanel();
        }
        else
        {
            drawDefaultPanel();
        }
        return panelComponent.render(graphics);
    }
    private void drawDefaultPanel()
    {
        final VorkathState specialAttack = plugin.getNextSpecialAttack();
        final BufferedImage poisonImage = spriteManager.getSprite(SpriteID.HITSPLAT_GREEN_POISON, 0);
        final BufferedImage freezeImage = spriteManager.getSprite(SpriteID.SPELL_ICE_BARRAGE, 0);
        switch (specialAttack)
        {
            case IDLE:
                if (mergedImage == null && poisonImage != null && freezeImage != null)
                {
                    mergedImage = new BufferedImage(poisonImage.getWidth() + freezeImage.getWidth() + 3, poisonImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    final Graphics graphics = mergedImage.getGraphics();
                    graphics.drawImage(poisonImage, 0, 0, null);
                    graphics.drawImage(freezeImage, poisonImage.getWidth() + 3, 0, null);
                    graphics.dispose();
                }
                panelComponent.getChildren().add(new VorkathPhaseInfoBox(mergedImage, plugin.getAttacksTillSpecial()));
                break;
            case TICKER_FIRE_PHASE:
                panelComponent.getChildren().add(new VorkathPhaseInfoBox(poisonImage, plugin.getAttacksTillSpecial()));
                break;
            case FREEZE_PHASE:
                panelComponent.getChildren().add(new VorkathPhaseInfoBox(freezeImage, plugin.getAttacksTillSpecial()));
                break;
        }
    }
    private void drawTickerPhasePanel()
    {
        final BufferedImage poisonImage = spriteManager.getSprite(SpriteID.HITSPLAT_GREEN_POISON, 0);
        panelComponent.getChildren().add(
                new VorkathPhaseInfoBox(poisonImage, plugin.getAmountOfPoisonBoltsLeft()));
    }
}