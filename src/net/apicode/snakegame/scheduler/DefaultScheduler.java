package net.apicode.snakegame.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class DefaultScheduler implements Scheduler {

  private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  private Future<?> task;
  private final Runnable runnable;
  private final long period;

  public DefaultScheduler(Runnable runnable, long period) {
    if(runnable == null) {
      throw new NullPointerException("Runnable is null");
    }
    this.runnable = runnable;
    this.period = period;
  }

  @Override
  public void start(long delay) {
    if(!isRunning()) {
      task = executorService.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }
  }

  @Override
  public void stop() {
    if(isRunning()) {
      task.cancel(false);
    }
  }

  @Override
  public boolean isRunning() {
    if(task == null) return false;
    return !task.isDone();
  }
}
