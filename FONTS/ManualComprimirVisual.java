import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManualComprimirVisual {
    private JTextArea ambQuinAlgorismeVolsTextArea;
    private JButton LZ78Button;
    private JButton JPEGButton;
    private JButton LZSSButton;
    private JButton LZWButton;
    private JPanel ManualCompwind;
    private JButton tornaButton;
    private static JFrame frame;
    private static Integer FoC;
    private static String pathg;

    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setPath(String path){
        pathg = path;
    }
    private static void setFoC(Integer FioCa){
        FoC = FioCa;
    }
    public void NewScreen(Integer FitxOCarp, String path) {
        frame = new JFrame("ManualComprimirVisual");
        frame.setContentPane(new ManualComprimirVisual().ManualCompwind);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        setFoC(FitxOCarp);
        setPath(path);
    }
    private void CloseScreen() {

        frame.setVisible(false);
    }
    private void infoMessage( String title,String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }

    public ManualComprimirVisual() {
        LZ78Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String s = "LZ78";
                //TODO: AMB CARPETA???
                if(FoC == 0 && !pathg.endsWith(".txt"))infoMessage("Error","No pots comprimir aquest arxiu amb aquest algorisme",0);
                else {
                    DestiVisual v = new DestiVisual();
                    v.setAut(false);
                    v.setAlgorisme(s);
                    v.NewScreen(0, pathg, 0, false, FoC, null);
                    CloseScreen();
                }
            }
        });
        LZSSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: AMB CARPETA???
                String s = "LZSS";
                if(FoC == 0 && !pathg.endsWith(".txt"))infoMessage("Error","No pots comprimir aquest arxiu amb aquest algorisme",0);
                else {
                    DestiVisual v = new DestiVisual();
                    v.NewScreen(0, pathg, 0, false, FoC, null);
                    v.setAut(false);
                    v.setAlgorisme(s);
                    CloseScreen();
                }
            }
        });
        LZWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: AMB CARPETA???
                String s = "LZW";
                //TODO: Si tenim un arxiu k no es compatible per la seva extensio amb el algorisme sha de fer un missatge -> CUIDAR SI ES CARPETA O FITXER
                //infoMessage(s);
                if(FoC == 0 && !pathg.endsWith(".txt"))infoMessage("Error","No pots comprimir aquest arxiu amb aquest algorisme",0);
                else {
                    DestiVisual v = new DestiVisual();
                    v.NewScreen(0, pathg, 0, false, FoC, null);
                    v.setAut(false);
                    v.setAlgorisme(s);
                    CloseScreen();
                }
            }
        });
        JPEGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String s = "JPEG";
                //TODO: AMB CARPETA???
                //infoMessage(s);
                if(FoC == 0 && !pathg.endsWith(".ppm"))infoMessage("Error","No pots comprimir aquest arxiu amb aquest algorisme",0);
                else {
                    QualitatJPEGVisual q = new QualitatJPEGVisual();
                    q.NewScreen(0, pathg, FoC, null);
                    //DestiVisual v = new DestiVisual();
                    //v.NewScreen(0 ,pathg);
                    CloseScreen();
                }
            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("FoC: "+FoC);
                if(FoC == 0){ //Si ve d'un fitxer
                    FitxerVisual c = new FitxerVisual();
                    c.NewScreen(pathg);
                    CloseScreen();
                }else{ //Si ve d'una Carpeta
                    CarpetaVisual c = new CarpetaVisual();
                    c.NewScreen(pathg);
                    CloseScreen();
                }

            }
        });
    }
}
