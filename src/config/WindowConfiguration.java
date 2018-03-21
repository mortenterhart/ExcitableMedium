package config;

public class WindowConfiguration {

    public static final String TITLE = "Excitable Medium Simulation";
    public static int WINDOW_WIDTH = 880;
    public static int WINDOW_HEIGHT = 595;

    public static long ITERATION_WAIT_INTERVAL = 500;

    public static void setIterationWaitInterval(long iterationWaitInterval) {
        ITERATION_WAIT_INTERVAL = iterationWaitInterval;
    }
}
