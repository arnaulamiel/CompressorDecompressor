import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EstadistiquesVisual {
    private JPanel panel1;
    private JTextArea Tamanyinicial;
    private JTextArea tamanyfinal;
    private JTextArea ratio;
    private JTextArea temps;
    private JButton següentButton;
    private static JFrame frame;
    private Double tamanyini;
    private Double tamanyfi;
    private Double ratiocomp;
    private Double tempscomp;
    private String nom;


    public void NewScreen() {

        frame = new JFrame("EstadistiquesVisual");
        frame.setContentPane(new EstadistiquesVisual(nom).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    private void CloseScreen() {
        frame.setVisible(false);
    }

    public EstadistiquesVisual(String nomest) {
        this.nom = nomest;
        ArrayList<Double> est = CtrlPresentacio.getEstadisticaByName(nom);

        tamanyini = est.get(1);
        tamanyfi = est.get(2);
        ratiocomp = est.get(3);
        tempscomp = est.get(4);
        /*
        tamanyini = 23412321f;
        tamanyfi = 2131.32f;
        ratiocomp = 21.3f;
        tempscomp = 23f;*/

        Tamanyinicial.setText(Tamanyinicial.getText()+"\n\n\n"+tamanyini+" bytes");
        tamanyfinal.setText(tamanyfinal.getText()+"\n\n\n"+tamanyfi+" bytes");
        ratio.setText(ratio.getText()+"\n\n\n                           "+ratiocomp);
        temps.setText(temps.getText()+"\n\n\n                           "+tempscomp+"ms");

        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VisualPresentacio p = new VisualPresentacio();
                p.main(null);
                CloseScreen();
            }
        });
    }
}
