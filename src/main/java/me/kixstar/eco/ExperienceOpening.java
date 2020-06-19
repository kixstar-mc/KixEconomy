package me.kixstar.eco;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ExperienceOpening {

    private float progress = 0.0f;
    private ScheduledExecutorService executor;
    private Consumer<Player> callback;
    private Player player;
    private ScheduledFuture<?> timeout;
    private boolean stopped = false;

    public ExperienceOpening(ScheduledExecutorService executor, Player player) {
        this.executor = executor;
        this.player = player;
    }

    private void reset() {
        this.stopped = true;
        this.player.sendExperienceChange(this.player.getExp());
    }

    private void decrement() {
        if (this.stopped) return;
        this.timeout = this.executor.schedule(this::decrement, 300, TimeUnit.MILLISECONDS);
        this.progress -= 0.05f;
        if (this.progress <= 0.0f) {
            this.stopped = true;
            this.player.sendExperienceChange(this.player.getExp(), this.player.getLevel());
        } else {
            this.player.sendExperienceChange(this.progress, (int)(this.progress * 100));
        }
    }

    public void increment() {
        if (this.stopped) return;
        if (this.timeout != null) this.timeout.cancel(true);
        this.progress += 0.05f;
        if (this.progress >= 1.0f) {
            this.stopped = true;
            this.player.sendExperienceChange(this.player.getExp(), this.player.getLevel());
            this.callback.accept(this.player);
        } else {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, this.progress, this.progress);
            this.player.sendExperienceChange(this.progress, (int)(this.progress * 100));
            this.timeout = this.executor.schedule(this::decrement, 300, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public void onComplete(Consumer<Player> callback) {
        this.callback = callback;
    }

}
