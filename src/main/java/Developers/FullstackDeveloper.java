package Developers;

public class FullstackDeveloper implements Backender, Frontender, Developer {
    @Override
    public void developServer() {
        System.out.println("Server done");
    }

    @Override
    public void developGUI() {
        System.out.println("GUI done");
    }
}