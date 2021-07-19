import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FitxerVisual {
    private JTextArea seleccionaElTipusDeTextArea;
    private JButton manualButton;
    private JButton automàticButton;
    private JPanel fitxerwind;
    private JTextArea seleccionaManualPerATextArea;
    private JButton tornaButton;
    private static JFrame frame;
    private static String pathg;

    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setPath(String path){
        pathg = path;
    }

    public FitxerVisual() {
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Manual");
                ManualComprimirVisual c = new ManualComprimirVisual();
                c.NewScreen(0,pathg);
                //Salta a decidir l'algorisme
                CloseScreen();
            }
        });
        automàticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Automatic");
                DestiVisual v = new DestiVisual();
                v.NewScreen(0,pathg,null,null,0,null);
                v.setAut(true);
                CloseScreen();
                //A decideixAlgorisme
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
    }

    public void NewScreen(String path) {
        frame = new JFrame("FitxerVisual");
        frame.setContentPane(new FitxerVisual().fitxerwind);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        setPath(path);
    }
    public void CloseScreen() {

        //frame.setVisible(false);
        frame.dispose();
    }
}
