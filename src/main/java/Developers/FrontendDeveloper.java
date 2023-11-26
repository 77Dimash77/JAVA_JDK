package Developers;

public class FrontendDeveloper implements Frontender, Developer {
    @Override
    public void developGUI() {
        System.out.println("GUI done");
    }
}