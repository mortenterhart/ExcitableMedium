package config;

public class WindowConfiguration {

    public static int WINDOW_WIDTH = 653;
    public static int WINDOW_HEIGHT = 614;

    public static long ITERATION_WAIT_INTERVAL = 500;

    public static void setIterationWaitInterval(long iterationWaitInterval) {
        ITERATION_WAIT_INTERVAL = iterationWaitInterval;
    }
}
