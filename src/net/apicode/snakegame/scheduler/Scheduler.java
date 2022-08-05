package net.apicode.snakegame.scheduler;

public interface Scheduler {

  void start(long delayMilliseconds);

  void stop();

  boolean isRunning();

  static Scheduler createScheduler(Runnable runnable, long period) {
    return new DefaultScheduler(runnable, period);
  }

}
