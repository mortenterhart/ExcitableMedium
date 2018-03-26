package main;

public class Main {
    public static Application application;
    public static void main(String[] args) {
         application = new Application();
        application.startApplication(args);
    }

    public static Application getApplication() {
        return application;
    }
}
