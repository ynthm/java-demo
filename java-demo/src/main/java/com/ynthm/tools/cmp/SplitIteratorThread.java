package com.ynthm.tools.cmp;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 分割遍历
 */
public class SplitIteratorThread<T> extends Thread {
    private Spliterator<T> mSpliterator;

    public SplitIteratorThread(Spliterator<T> spliterator) {
        mSpliterator = spliterator;
    }

    @Override
    public void run() {
        super.run();
        if (mSpliterator != null) {
            mSpliterator.forEachRemaining(t -> {
                        System.out.println(Thread.currentThread().getName() + "-" + t + " ");
                    }
            );
        }
    }
}
