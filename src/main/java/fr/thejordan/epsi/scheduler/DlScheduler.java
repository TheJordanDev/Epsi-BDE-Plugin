package fr.thejordan.epsi.scheduler;

import fr.thejordan.epsi.Epsi;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class DlScheduler extends BukkitRunnable {

    @Getter
    private final String url;
    @Getter
    private final String path;
    @Getter
    private final Player player;

    public DlScheduler(String url, String path, Player player) {
        this.url = url;
        this.path = path;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
            java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
            java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            long downloadedFileSize = 0;
            int x = 0;
            while ((x = in.read(data, 0, 1024)) >= 0) {
                downloadedFileSize += x;
                final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) completeFileSize)) * 100d);
                player.sendActionBar(Component.text(currentProgress+"%"));
                bout.write(data, 0, x);
            }
            bout.close();
            in.close();
            if (this.url.endsWith(".jar") && this.path.startsWith("plugins/")) {
                Epsi.instance().getServer().reload();
            }
        } catch (Exception ignored) {

        }
    }
}
