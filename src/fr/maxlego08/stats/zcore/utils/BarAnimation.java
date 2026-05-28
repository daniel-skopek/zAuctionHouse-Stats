package fr.maxlego08.stats.zcore.utils;

import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;

public class BarAnimation {

    private final BossBar bossBar;
    private final double totalTime;
    private double remainingTime;

    /**
     * Creates a new smooth BossBar animation for a group of players.
     *
     * @param foliaLib The FoliaLib scheduler instance
     * @param players  The list of players to display the BossBar to.
     * @param text     The text to display on the BossBar.
     * @param seconds  The total duration of the animation in seconds.
     */
    public BarAnimation(FoliaLib foliaLib, List<Player> players, String text, int seconds, BarColor barColor, BarStyle barStyle) {
        this.bossBar = Bukkit.createBossBar(text, barColor, barStyle);
        this.totalTime = seconds * 20.0;
        this.remainingTime = totalTime;

        for (Player player : players) {
            this.bossBar.addPlayer(player);
        }

        this.bossBar.setVisible(true);

        foliaLib.getScheduler().runTimer(wrappedTask -> {
            double progress = remainingTime / totalTime;
            bossBar.setProgress(progress);

            if (remainingTime <= 0) {
                bossBar.removeAll();
                wrappedTask.cancel();
            }

            remainingTime -= 1;
        }, 0, 1);
    }
}
