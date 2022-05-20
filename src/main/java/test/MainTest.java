package test;

import hotswap.thread.FileEventWatchThread;
import hotswap.thread.ProcessFileEventThread;

public class MainTest {
    public static void main(String[] args) {
        FileEventWatchThread fileEventWatchThread = new FileEventWatchThread();
        fileEventWatchThread.startWatchServiceThread();

        ProcessFileEventThread processFileEventThread = new ProcessFileEventThread();
        processFileEventThread.processEventThread();
    }
}
