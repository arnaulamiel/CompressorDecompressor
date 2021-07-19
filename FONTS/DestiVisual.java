import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class DestiVisual {
    private JPanel panell;
    private JTextArea especificaElPATHOnTextArea;
    private JButton següentButton;
    private JButton tornaButton;
    private JButton seleccionaPathDestíButton;
    private JTextField textField1;
    private static Integer tip;
    private static JFrame frame;
    private static String text;
    private static Integer GoS;
    private static String pathg;
    private static Boolean JPEG;
    private static Integer FoC;
    private String pathdesti;
    private static String algorisme;
    private static Boolean aut = false;
    private static Integer qualitat;
    private static HashMap<String,String> hm;

    private static void setTip(Integer tipo){
        tip = tipo;
    }
    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setPath(String path){
        pathg = path;
    }
    private static void setFoC(Integer FioCa){
        FoC = FioCa;
    }
    private static void setisJPEG(Boolean is){
        JPEG = is;
    }
    public static void setAlgorisme(String a){
        algorisme = a;
    }
    private static void setGoA(Integer GeoA){
        GoS = GeoA;
    }
    private static void settext(String textt){
        text = textt;
    }
    public static void setAut(Boolean auto){aut = auto;}
    public static void setQualitat(Integer q){qualitat=q;}
    public static void setHM(HashMap<String,String> m){hm = m;}

    public DestiVisual() {


        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Descomprimeix al path que ens retorna textField1.getText();

                String Nom = textField1.getText();

                //infoMessage("Error de descompresió","Ha hagut un error en la descompressió", 0 ); //Si no correcta
                if (pathdesti != null && pathdesti != "") {
                    if(!Nom.isBlank() && Nom != null) {
                        CridaPresentacio(Nom);
                    }
                    else {
                        System.out.println("NOM BUIT: ");
                        String[] a = pathg.split("\\\\");
                        Nom = a[a.length-1];
                        CridaPresentacio(Nom);
                    }
                }
                else infoMessage("Selecciona Path","Has de seleccionar un PATH destí",0);


            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Torna a la selecció de path per a buscar l'arxiu a comprimir
                    if(tip == 0){//Si ve de comprimit
                        if(!aut) {
                            if (GoS == 0) { //Si ve de general
                                if (JPEG) { //Si hi havia algun JPEG
                                    QualitatJPEGVisual q = new QualitatJPEGVisual();
                                    q.NewScreen(0, pathg, FoC, hm);
                                } else {
                                    ManualComprimirVisual m = new ManualComprimirVisual();
                                    m.NewScreen(FoC, pathg);
                                }
                            } else { //Si ve de semiautomàtic (serà una carpeta si o si)
                                if (JPEG) { //Si hi havia algun JPEG
                                    QualitatJPEGVisual q = new QualitatJPEGVisual();
                                    q.NewScreen(1, pathg, 1,hm);
                                } else {
                                    SeleccionaArxiusCompCarp s = new SeleccionaArxiusCompCarp(pathg);
                                    s.NewScreen(pathg);
                                }

                            }
                        }
                        else{
                            ComprimirVisual c = new ComprimirVisual();
                            c.NewScreen(tip);
                        }
                    }
                    else{//Si ve de descomprimit
                        ComprimirVisual c = new ComprimirVisual();
                        c.NewScreen(tip);
                    }
                    CloseScreen();

            }
        });
        seleccionaPathDestíButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jf.showOpenDialog(null);
                String a = jf.getSelectedFile().toString();
                System.out.println("Arxiu "+a);
                if(a != null && a!= "")pathdesti = a;
                else infoMessage("Selecciona arxiu","Has de seleccionar algun destí",0);


            }
        });
    }

    private void CloseScreen() {
        frame.setVisible(false);
    }

    public void NewScreen(Integer CompoDesc, String path, Integer GenoAut, Boolean isJPEG, Integer FitxoCarp, HashMap<String,String> hashMap) {
        frame = new JFrame("DestiVisual");
        frame.setContentPane(new DestiVisual().panell);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("Automàtic ? "+aut);
        //1-> desc 0-> comp
        init(CompoDesc,path,GenoAut,isJPEG,FitxoCarp,hashMap);
    }

    public void init(Integer tipo, String path,Integer GenoAut, Boolean isJPEG, Integer FitxoCarp, HashMap<String,String> hashMap){
        String textt;
        if(tipo!=null)setTip(tipo);
        if(GenoAut!=null)setGoA(GenoAut);
        if(path!=null)setPath(path);
        if(isJPEG!=null)setisJPEG(isJPEG);
        if(FitxoCarp!=null)setFoC(FitxoCarp);
        System.out.println(tip);
        //if(hashMap != null) hm.putAll(hashMap);



    }
    private void CridaPresentacio(String Nom){
        System.out.println(Nom);
        String s = "";
        if (tip == 0) {//Si ve de comprimit
            if (FoC == 0) { //És un fitxer
                if (JPEG != null && JPEG) { //Si es JPEG
                    System.out.println("CtrlPresentacio.ComprimirFitxerMan(pathg,\"JPEG\",pathdesti);");
                    s = CtrlPresentacio.ComprimeixFitxerMan(pathg,"JPEG",pathdesti,Nom,qualitat);

                } else {
                    if (aut){
                        System.out.println("CtrlPresentacio.ComprimirFitxerAut(pathg,pathdesti);");
                        try {
                            s = CtrlPresentacio.ComprimeixFitxerAut(pathg, pathdesti, Nom);
                        }
                        catch(Exception e){s = "Error: ";System.out.println(e.getCause());}
                    }
                    else{
                        System.out.println("CtrlPresentacio.ComprimirFitxerMan(pathg,algorisme,pathdesti,Nom);"+pathg+" "+algorisme+" "+pathdesti+" "+Nom);
                        try {
                            s = CtrlPresentacio.ComprimeixFitxerMan(pathg,algorisme,pathdesti,Nom,null);
                        }
                        catch(Exception e){s = "Error: ";System.out.println(e.getCause());}
                    }
                }
            } else {//És una carpeta que es comprimeix manual o automaticament
                if (JPEG != null && JPEG) {
                    if (GoS != null && GoS == 1) {
                        System.out.println("CtrlPresentacio.ComprimirCarpetaSemiaut(pathg,Hashmap,pathdesti,Nom,hm);-> AMB ALGUN JPEG");
                        try {
                            s = CtrlPresentacio.ComprimeixCarpetaSemiaut(pathg, hm, pathdesti, Nom, qualitat);
                        }
                        catch(Exception e){s= "Error: ";System.out.println(e.getCause());}
                    }
                    else{
                        System.out.println("CtrlPresentacio.ComprimirCarpetaManual(pathg,\"JPEG\",pathdesti,Nom);");
                        try {
                            s = CtrlPresentacio.ComprimeixCarpetaManual(pathg,"JPEG",pathdesti,Nom,qualitat);
                        }
                        catch(Exception e){s = "Error: "; System.out.println(e.getCause());}
                    }
                } else {
                    if (aut) {

                        try {
                            s = CtrlPresentacio.ComprimeixCarpetaAut(pathg,pathdesti,Nom);
                        }
                        catch(Exception e){s = "Error: ";System.out.println(e.getCause());}
                    }
                    else {
                        if (GoS != null && GoS == 1) {
                            System.out.println("CtrlPresentacio.ComprimirCarpetaSemiaut(pathg,Hashmap,pathdesti,Nom,hm);");

                            try {
                                s = CtrlPresentacio.ComprimeixCarpetaSemiaut(pathg,hm,pathdesti,Nom,null);
                            }
                            catch(Exception e){s = "Error: ";System.out.println(e.getCause());}

                        } else {
                            System.out.println("CtrlPresentacio.ComprimirCarpetaManual(pathg,algorisme,pathdesti,Nom);");
                            try {
                                s = CtrlPresentacio.ComprimeixCarpetaManual(pathg, algorisme, pathdesti, Nom, null);
                            }
                            catch(Exception e){s = "Error: ";System.out.println(e.getCause());}

                        }
                    }
                }

            }
            if(s.equals("OK")) {
                infoMessage("Compressió efectuada", "S'ha comprimit l'arxiu o carpeta amb èxit", -1); //Si correcta
                /*VisualPresentacio p = new VisualPresentacio();
                p.main(null);*/
                //TODO: String[] Anom = pathg.split("\");
                // String nom = Anom[Anom.lenght -1];
                // ArrayList<Float> est = CtrlPresentacio.getEstadistica(nom);
                ArrayList<Float> est = null;
                EstadistiquesVisual e = new EstadistiquesVisual(Nom);
                e.NewScreen();
                CloseScreen();
            }
            else{
                infoMessage("Error", "Error en la compressió", 0);
            }
        } else {//Si ve de descomprimit
            if (FoC == 0) {
                System.out.println("CtrlPresentacio.DescomprimirFitxer(pathg,pathdesti,Nom);");
                try {
                    s = CtrlPresentacio.DescomprimirFitxer(pathg,pathdesti,Nom);
                }
                catch(Exception e){s = "Error: ";e.getCause();}
            } else {
                System.out.println("CtrlPresentacio.DescomprimirCarpeta(pathg,pathdesti,Nom);");

                try {
                    s = CtrlPresentacio.DescomprimirCarpeta(pathg,pathdesti,Nom);
                }
                catch(Exception e){s = "Error: ";e.getCause();}
            }

            if(s.equals("OK")) {
                infoMessage("Descompressió efectuada", "S'ha descomprimit l'arxiu o carpeta amb èxit", -1); //Si correcta
                /*VisualPresentacio p = new VisualPresentacio();
                p.main(null);*/
                //TODO: String[] Anom = pathg.split("\");
                // String nom = Anom[Anom.lenght -1];
                // ArrayList<Float> est = CtrlPresentacio.getEstadistica(nom);
                ArrayList<Float> est = null;
                EstadistiquesVisual e = new EstadistiquesVisual(Nom);
                e.NewScreen();
                CloseScreen();
            }
            else{
                infoMessage("Error", "Error en la descompressió", 0);
            }
        }
    }
    private void infoMessage( String title, String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }

}
