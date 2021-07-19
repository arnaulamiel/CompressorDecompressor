import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObrirVisual {
    private JButton seleccionaArxiuButton;
    private JButton seguentButton;
    private JButton tornaButton;
    private JPanel obrir;
    private String pathg;
    private static JFrame frame;

    public ObrirVisual() {
        seleccionaArxiuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jf.showOpenDialog(null);
                String a = jf.getSelectedFile().toString();
                if(a != null && a!= "")pathg = a;
                else infoMessage("Selecciona arxiu","Has de seleccionar algun arxiu",0);
            }
        });
        seguentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                CtrlPresentacio.OpenComprimit(pathg,true);
                VisualPresentacio v = new VisualPresentacio();
                v.main(null);
                CloseScreen();
            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VisualPresentacio v = new VisualPresentacio();
                v.main(null);
                CloseScreen();
            }
        });
    }

    public void NewScreen() {
        frame = new JFrame("ObrirVisual");
        frame.setContentPane(new ObrirVisual().obrir);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private void CloseScreen() {
        frame.setVisible(false);
    }
    private void infoMessage( String title, String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }
}
