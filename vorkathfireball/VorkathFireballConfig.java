package net.runelite.client.plugins.vorkathfireball;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
@ConfigGroup("vorkathfireball")
public interface VorkathFireballConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "highlightFirebomb",
            name = "Highlight firebomb tile",
            description = "Highlights tile for the firebomb"
    )
    default boolean highlightFirebomb()
    {
        return true;
    }
    @ConfigItem(
            position = 2,
            keyName = "highlightFirebombSafe",
            name = "Highlight firebomb safe tiles",
            description = "Highlights surrounding safe tiles for the firebomb"
    )
    default boolean highlightFirebombSafe()
    {
        return false;
    }
    @ConfigItem(
            position = 3,
            keyName = "notifyFirebomb",
            name = "Notify on firebomb",
            description = "Sends a notification when varkath uses firebomb"
    )
    default boolean notifyOnFirebomb()
    {
        return false;
    }
}