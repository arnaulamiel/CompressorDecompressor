import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class QualitatJPEGVisual {
    private JComboBox comboBox1;
    private JButton tornaButton;
    private JButton següentButton;
    private JPanel qualitat;
    private static JFrame frame;
    private static Integer GoS;
    private static String pathg;
    private static Integer FoC;
    private static Integer quality;
    private static HashMap<String,String> hm;

    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setPath(String path){
        pathg = path;
    }
    private static void setFoC(Integer FioCa){
        FoC = FioCa;
    }
    private static void setGoS(Integer GeorSe){
        GoS = GeorSe;
    }
    private static void setQuality(Integer i){
        quality = i;
    }
    private static void setHm(HashMap<String,String> hashMap){
        hm.putAll(hashMap);
    }
    public void NewScreen(Integer GlobaloSemi, String path, Integer FitxoCarp, HashMap<String,String> hashMap) {
        frame = new JFrame("QualitatJPEGVisual");
        frame.setContentPane(new QualitatJPEGVisual().qualitat);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        setGoS(GlobaloSemi);
        setPath(path);
        setFoC(FitxoCarp);
        if(hashMap != null) setHm(hashMap);

    }
    public  void CloseScreen(){
        frame.dispose();
    }
    private void infoMessage( String title, String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }

    public QualitatJPEGVisual() {
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Torna
                if(GoS == 0){ //ve de comprimir un fitxer/carpeta global
                    ManualComprimirVisual m = new ManualComprimirVisual();
                    m.NewScreen(FoC, pathg);
                    CloseScreen();
                }
                else{//ve de comprimir una carpeta semiautomàtica i hi ha algun Jpeg
                    SeleccionaArxiusCompCarp s = new SeleccionaArxiusCompCarp(pathg);
                    s.NewScreen(pathg);
                    CloseScreen();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Quan es modifica
                System.out.println(comboBox1.getSelectedIndex());
                Integer i = comboBox1.getSelectedIndex();
                setQuality(i);
            }
        });
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(quality != null && quality != 0){
                    DestiVisual d = new DestiVisual();
                    //fSystem.out.println("HM: "+hm);
                    d.NewScreen(0, pathg, GoS, true, FoC,hm);
                    d.setAut(false);
                    d.setQualitat(quality);
                    CloseScreen();
                }
                else infoMessage("Selecciona Qualitat","Selecciona una qualitat per a l'algorisme JPEG",0);
            }
        });
    }
}
