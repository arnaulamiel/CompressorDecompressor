import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualPresentacio {
    private JTextArea compressorDescompressorPROP2019TextArea;
    private JTextArea escullLaFuncionalitatQueTextArea;
    private JButton descomprimirButton;
    private JButton sortirButton;
    private JButton comprimirButton;
    private JPanel Panel;
    private JTextArea sergiCurtoEfrenCarlesTextArea;
    private JButton mostrarEstadístiquesButton;
    private JButton obrirComprimitButton;
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("VisualPresentacio");

        frame.setContentPane(new VisualPresentacio().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public static void CloseScreen() {

        //frame.setVisible(false);
        frame.dispose();
    }

    public VisualPresentacio() {

        comprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Comprimir");
                //Va cap a ComprimirVisual
                //this.setVisible(false);
                ComprimirVisual cv = new ComprimirVisual();
                cv.NewScreen(0);
                CloseScreen();

            }
        });
        descomprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Descomprimir");
                ComprimirVisual cv = new ComprimirVisual();
                cv.NewScreen(1);
                CloseScreen();
            }
        });
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Sortir");
                System.exit(0);
            }
        });
        mostrarEstadístiquesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Mostra Estadístiques");
                MostrarEstadistiquesVisual m = new MostrarEstadistiquesVisual();
                m.NewScreen();
                CloseScreen();

            }
        });
        obrirComprimitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ObrirVisual o = new ObrirVisual();
                o.NewScreen();
                CloseScreen();
            }
        });
    }
}
