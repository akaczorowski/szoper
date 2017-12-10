package pl.akac.android.szoper.common.tmp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadUtil {

  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  private ThreadUtil() {
  }

  public static void runInBackground(Runnable r) {
    executor.execute(r);
  }
}
