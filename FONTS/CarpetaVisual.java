import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarpetaVisual {
    private JTextArea seleccionaElTipusDeTextArea;
    private JButton manualButton;
    private JButton automàticButton;
    private JButton semiautomàticButton;
    private JPanel carpetacomprwin;
    private JTextArea seleccionaManualPerATextArea;
    private JButton tornaButton;
    private static JFrame frame;
    private static String pathg;

    public void NewScreen(String path) {
        frame = new JFrame("CarpetaVisual");
        frame.setContentPane(new CarpetaVisual().carpetacomprwin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        setPath(path);
    }
    public  void CloseScreen() {
        //frame.setVisible(false);
        frame.dispose();
    }
    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setPath(String path){
        pathg = path;
    }


    public CarpetaVisual() {
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Manual");
                //Anirà a ManualComprimirVisual
                ManualComprimirVisual c = new ManualComprimirVisual();
                c.NewScreen(1,pathg);
                CloseScreen();
            }
        });
        automàticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DestiVisual d = new DestiVisual();
                d.NewScreen(0,pathg,null,null,1,null);
                d.setAut(true);
                CloseScreen();


            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ComprimirVisual v = new ComprimirVisual();
                v.NewScreen(0);
                CloseScreen();
            }
        });
        semiautomàticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SeleccionaArxiusCompCarp v = new SeleccionaArxiusCompCarp(pathg);
                System.out.println("Arriba");
                v.NewScreen(pathg);
                CloseScreen();
            }
        });
    }
}
