package net.runelite.client.plugins.vorkath;
import java.awt.Color;
import java.awt.Image;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;
class VorkathPhaseInfoBox extends InfoBoxComponent
{
    VorkathPhaseInfoBox(Image image, int attacksTillSpecialCount)
    {
        final String specialAttackCounter = attacksTillSpecialCount > 0 ? String.valueOf(attacksTillSpecialCount) : "NOW!";
        setImage(image);
        setText(specialAttackCounter);
        if (attacksTillSpecialCount == 0)
        {
            setColor(Color.RED);
            setText("NOW!");
        }
        else
        {
            setColor(attacksTillSpecialCount > 3 ? Color.WHITE : Color.YELLOW);
            setText(String.valueOf(attacksTillSpecialCount));
        }
    }
}