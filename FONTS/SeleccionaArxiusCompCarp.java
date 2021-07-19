import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class SeleccionaArxiusCompCarp {
    private JTextArea seleccionaElsArxiusElsTextArea;
    private JPanel panel1;
    private JList list1;
    private DefaultListModel model;
    private JTextPane textPane1;
    private JTextArea VOLSCOMPRIMIRAQUESTSARXIUSTextArea;
    private JComboBox comboBox1;
    private JTextArea ALGORISMEDESITJATTextArea;
    private JTextArea SELECCIONAELSARXIUSPERTextArea;
    private JButton dAcordButton;
    private JButton tornaButton;
    private JButton borrarButton;
    private static JFrame frame;
    private static String algorisme;
    private HashMap<String, String> hm = new HashMap<String, String>(){{put("LZ78","");put("JPEG","");put("LZSS","");put("LZW","");put("AUT","");}};
    private ArrayList<String> ArxiusDinsDirectori;
    private ArrayList<String> Aux;
    private static String pathsrc;

    private static void setFrames(JFrame fra){
        frame = fra;
    }
    private static void setAlgorisme(String alg){
        algorisme = alg;
    }
    private static void setPathsrc(String path){
        pathsrc = path;
    }

    public SeleccionaArxiusCompCarp(String path) {

        //String[] a = {"Arxiu1.txt", "Arxiu2.txt","asdskd.JPEG","abc.JPEG","asda.txt","Tete.JPEG","Cabr.txt"};
        //ArxiusDinsDirectori = CtrlPresentacio.getLlistaArxius(pathsrc);

        iniArxius(path);
        model = new DefaultListModel();
        list1.setModel(model);
        for ( int i = 0; i < ArxiusDinsDirectori.size(); i++ ){
            model.addElement(ArxiusDinsDirectori.get(i));
        }
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                String selected = list1.getSelectedValue().toString();
                String st = "";
                Integer flag = 0;
                String message = "Ja has seleccionat algorisme per aquest arxiu \nSi vols canviar-ho pots Esborrar la llista i tornar a seleccionar algorisme";

                //if(list1.getNextMatch(selected,0,javax.swing.text.Position.Bias.Forward) != -1){
                if (!hm.get(algorisme).contains(selected)) {
                    if (!hm.get(algorisme).isEmpty()){
                        if (!esPotSelecionat(selected).startsWith("Error:")){
                            hm.put(algorisme, hm.get(algorisme) + "," + selected);
                        }
                        else {
                            if(esPotSelecionat(selected).endsWith("tipus")) infoMessage("Error","No pots comprimir un arxiu amb aquest algorisme ja que no és compatible",flag);
                            else infoMessage("Error",message,flag);
                        }
                    }else{
                        if (!esPotSelecionat(selected).startsWith("Error:"))hm.put(algorisme, selected);
                        else {
                            if(esPotSelecionat(selected).endsWith("tipus")) infoMessage("Error","No pots comprimir un arxiu amb aquest algorisme ja que no és compatible",flag);
                            else infoMessage("Error",message,flag);
                        }
                    }
                }
                String t = preparaText();
                textPane1.setText(t);
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setAlgorisme(comboBox1.getSelectedItem().toString());

            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPane1.setText("");
                hm.clear();
                hm.put("LZ78","");
                hm.put("JPEG","");
                hm.put("LZSS","");
                hm.put("LZW","");
                hm.put("AUT","");
            }
        });
        tornaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CarpetaVisual c = new CarpetaVisual();
                c.NewScreen(pathsrc);
                CloseScreen();
            }
        });
        dAcordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Botó d'acord click
                //cridar a controlador per a fer comprimir del hashmap hm i la resta decideixalg.
                Integer a = -1;

                //System.out.println(hm.get("JPEG").isEmpty());
                OmplirAutomatics(hm);
                System.out.println("DESPRES CRIDA");

                if(!textPane1.getText().isEmpty()){
                    //infoMessage("Carpeta Comprimida","S'ha comprimit la carpeta amb èxit",-1 );

                    if(!hm.get("JPEG").isEmpty()){
                        QualitatJPEGVisual q = new QualitatJPEGVisual();
                        q.NewScreen(1,pathsrc,1,hm);
                    }
                    else {
                        DestiVisual v = new DestiVisual();
                        v.NewScreen(0, pathsrc,1,false,1,hm);
                        v.setAut(false);
                        v.setHM(hm);
                    }
                    CloseScreen();
                }
                else{
                    a = JOptionPane.showConfirmDialog(null,"Tota la carpeta es comprimirà amb l'algorisme decidit automàticament \nVols continuar?");
                    //0->Si 1->No 2->Cancelar

                }
                if(a == 0){
                    DestiVisual v = new DestiVisual();
                    v.NewScreen(0, pathsrc,1,false,1,hm);
                    v.setAut(false);
                    v.setHM(hm);
                    CloseScreen();
                }
            }
        });

    }

    public void NewScreen(String path) {
        frame = new JFrame("SeleccionaArxiusCompCarp");
        frame.setContentPane(new SeleccionaArxiusCompCarp(path).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        setAlgorisme("JPEG");
    }

    private void CloseScreen() {
        frame.setVisible(false);
    }
    private String esPotSelecionat(String s){
        String[] aux = {"JPEG","LZSS","LZW","LZ78"};
        for(int i = 0; i < aux.length; i++){
            if(hm.get(aux[i]).contains(s)){return "Error: seleccionat";}
        }
        if(s.endsWith(".JPEG") && algorisme != "JPEG")return "Error: tipus";
        else if((s.endsWith(".txt")) && algorisme == "JPEG" ) return "Error: tipus";
        return "OK";
    }

    private String preparaText(){
        String s1 = "";
        String[] aux = {"JPEG","LZSS","LZW","LZ78"};
        for(int i = 0; i < hm.size() -1 ; ++i){
            if(s1 !=  null && s1 != ""){
                if(!hm.get(aux[i]).isEmpty())s1 += aux[i]+": "+hm.get(aux[i])+"\n";
            }
            else{
                if(!hm.get(aux[i]).isEmpty())s1 = aux[i]+": "+hm.get(aux[i])+"\n";
            }
            System.out.println(hm.get(aux[i]));
        }
        return s1;
    }
    private void infoMessage( String title, String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }
    private void OmplirAutomatics(HashMap<String,String>hm){
        ArrayList<String> as = new ArrayList<>();
        String[] aux = {"JPEG","LZSS","LZW","LZ78"};

        for(int i = 0; i < aux.length  ; i++){
           as.add(hm.get(aux[i]));
        } //Afegeixo els que tenen algorisme especificat a as i ara vull mirar quins son els restants


        for(int i = 0; i < as.size(); i++){

            if(!as.get(i).isBlank() && (hm.get("LZ78").contains(as.get(i)) || hm.get("LZSS").contains(as.get(i)) || hm.get("LZW").contains(as.get(i)) || hm.get("JPEG").contains(as.get(i)) )){
                String[] arr= as.get(i).split(",");
                for(Integer j = 0; j<arr.length; ++j){
                    if(hm.get("LZ78").contains(arr[j]) || hm.get("LZSS").contains(arr[j]) || hm.get("LZW").contains(arr[j]) || hm.get("JPEG").contains(arr[j])) Aux.remove(arr[j]);
                }
            } //A Aux nomes quedaran els automatics
        }

        for(int i = 0; (i < Aux.size()); i++){

            if(Aux.get(i) != null) {
                if(!hm.get("AUT").isEmpty()){
                    if(Aux.get(i) != null && Aux.get(i) != "")hm.put("AUT",(hm.get("AUT")+","+Aux.get(i)) );
                }
                else hm.put("AUT", Aux.get(i));
            }
        }
        System.out.println("HM2: "+hm);

    }
    private void iniArxius(String path){

        //Li asigno al ArxiuDinsDirectori els arxius que hi han en cada path
        System.out.println("Abans");
        setPathsrc(path);
        //System.out.println(CtrlPresentacio.getLlistaArxius(pathsrc));
        ArxiusDinsDirectori = CtrlPresentacio.getLlistaArxius(pathsrc);
        //System.out.println(ArxiusDinsDirectori+" i "+i);

        //System.out.println(ArxiusDinsDirectori);

        //Inicialitzo un auxiliar que portarà inicialment tots els arxius i que servirà per asignar el algorisme Automatic als que no hagin estat escollits a OmplirAutomàtics
        Aux = new ArrayList<String>(ArxiusDinsDirectori);
        //System.out.println(ArxiusDinsDirectori+" "+Aux);

    }

}
