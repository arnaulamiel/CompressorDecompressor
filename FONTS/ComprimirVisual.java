import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComprimirVisual {
    private JTextArea introdueixElPATHDelTextArea;
    private JTextField textField1;
    private JPanel comprimirwind;
    private JButton següentButton;
    private JButton tornaButton;
    private JButton seleccionaArxiuButton;
    private static JFrame frame;
    private static Integer tip;
    private String pathg;
    private JLabel nomarxiuselec;

    private static void setTip(Integer tipo){
        tip = tipo;
    }
    public void NewScreen(Integer tipo) {
        frame = new JFrame("ComprimirVisual");
        frame.setContentPane(new ComprimirVisual().comprimirwind);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //0-> compr , 1-> desc

        tip = tipo;
        System.out.println("TIP: "+tip);
        System.out.println(tipo);
    }
    private void CloseScreen() {

        frame.dispose();
    }
    private void infoMessage( String title, String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }
    public ComprimirVisual() {
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Si es un fitxer va cap a FitxerVisual
                //Si es una carpeta cap a CarpetaVisual
                //System.out.println("Seguent tip: "+tip+" Path "+pathg);
                Integer FoC;
                String path = pathg;
                System.out.println("NOMAR "+nomarxiuselec.getText());
                if(nomarxiuselec.getText() != "") {
                    if(tip == 0) { //Si ha de comprimir
                        if ((path.endsWith(".txt") || path.endsWith(".TXT")) || (path.endsWith(".ppm") || path.endsWith(".PPM"))) {

                            FitxerVisual f = new FitxerVisual();
                            f.NewScreen(path);

                        } else {
                            CarpetaVisual c = new CarpetaVisual();
                            c.NewScreen(path);
                        }
                    }
                    else{ //Si es un descomprimit
                        //Crida a persistencia per descomprimir arxiu (acabat en .LZX o .JPEG), enviar el PATH a controlador i alla fem tot

                        DestiVisual d = new DestiVisual();
                        if (path.endsWith(".LZ7") || path.endsWith(".LZS") || path.endsWith(".LZW") || path.endsWith(".jpg"))d.NewScreen(1,path,null,null,0,null);
                        else d.NewScreen(1,path,null,null,1,null);
                        d.setAut(false);
                    }
                    CloseScreen();
                }
                else infoMessage("Error","Selecciona un fitxer",0);

            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VisualPresentacio f = new VisualPresentacio();
                f.main(null);
                CloseScreen();
            }
        });
        seleccionaArxiuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jf.showOpenDialog(null);
                String a = jf.getSelectedFile().toString();
                System.out.println("Arxiu "+a);
                if(a != null && a!= ""){
                    pathg = a;
                    String[] arr = pathg.split("\\\\");
                    nomarxiuselec.setText(arr[arr.length -1]);
                }
                else infoMessage("Selecciona arxiu","Has de seleccionar algun arxiu",0);

            }
        });
    }


}
