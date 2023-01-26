public class Main {

    private static AquaponicsSimGUI gui;
    
    public static void main(String[] args)
    {
        gui = new AquaponicsSimGUI();
        gui.setVisible(true);
    }

    public static AquaponicsSimGUI getGUI()
    {
        return gui;
    }
}