package Developers;

public class Main {
    public static void main(String[] args) {
        FrontendDeveloper frontendDev = new FrontendDeveloper();
        Developer dev = (Developer) frontendDev;
        if (dev instanceof Frontender) {
            ((Frontender) dev).developGUI();
        }
    }
}