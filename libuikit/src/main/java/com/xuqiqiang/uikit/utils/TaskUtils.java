package com.xuqiqiang.uikit.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.xuqiqiang.uikit.utils.Utils.mMainHandler;

public class TaskUtils {
    private static ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(1, 4, 60,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(4),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    private static ThreadPoolExecutor getExecutor() {
        if (mExecutor == null) {
            mExecutor = new ThreadPoolExecutor(1, 4, 60,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(4),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        }
        return mExecutor;
    }

    public static <T> void execute(Task<T> task) {
        getExecutor().execute(task);
    }

    public static void execute(Runnable runnable) {
        getExecutor().execute(runnable);
    }

    public static void shutdown() {
        if (mExecutor != null) {
            mExecutor.shutdown();
            mExecutor = null;
        }
    }

    public static abstract class Task<T> implements Runnable {

        private static final int NEW = 0;
        private static final int COMPLETING = 1;
        private static final int CANCELLED = 2;
        private static final int EXCEPTIONAL = 3;

        private volatile int state;

        public abstract T doInBackground();

        public void onMainThread(T result) {
            // default no implementation
        }

        public void onJobThread(T result) {
            // default no implementation
        }

        public void onError(Throwable t) {
            // default no implementation
        }

        @Override
        public final void run() {
            try {
                state = NEW;
                final T res = doInBackground();
                if (state != NEW) return;
                state = COMPLETING;

                if (res != null) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onMainThread(res);
                        }
                    });

                    onJobThread(res);
                }
            } catch (Exception e) {
                if (state != NEW) return;
                state = EXCEPTIONAL;
                Logger.d("job execute error", e);
                onError(e);
            }
        }

        public void cancel() {
            state = CANCELLED;
        }

        public boolean isDone() {
            return state != NEW;
        }

        public boolean isCanceled() {
            return state == CANCELLED;
        }
    }
}
