import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MostrarEstadistiquesVisual {
    private JList list1;
    private JScrollBar scrollBar1;
    private JButton tornarButton;
    private JButton següentButton;
    private JPanel Mostrarest;
    private static JFrame frame;
    private DefaultListModel model;
    private ArrayList<String> listEst;
    private String seleccionat;


    public MostrarEstadistiquesVisual() {
        //String[] a = {"Arxiu1.txt", "Arxiu2.txt","asdskd.JPEG","abc.JPEG","asda.txt","Tete.JPEG","Cabr.txt"};
        listEst = CtrlPresentacio.getLlistaEstadistiques();
        model = new DefaultListModel();
        list1.setModel(model);
        for ( int i = 0; i <listEst.size(); i++ ){
            //model.addElement(a[i]);
            model.addElement(listEst.get(i));
        }
        tornarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VisualPresentacio p = new VisualPresentacio();
                p.main(null);
                CloseScreen();
            }
        });
        següentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if(list1.getSelectedValue() != null ){
                    String s = list1.getSelectedValue().toString();
                    EstadistiquesVisual e = new EstadistiquesVisual(s.substring(0,s.length()-4));
                    e.NewScreen();
                    CloseScreen();
                }
                else{
                    infoMessage("Error","Selecciona alguna estadística per a visualitzar",0);
                }

            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                seleccionat = list1.getSelectedValue().toString();

            }
        });
    }

    public void NewScreen() {
        frame = new JFrame("MostrarEstadistiquesVisual");
        frame.setContentPane(new MostrarEstadistiquesVisual().Mostrarest);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void CloseScreen() {
        frame.setVisible(false);
    }
    private void infoMessage( String title,String message, Integer flag){
        JOptionPane.showMessageDialog(null,message,title,flag);
    }
}
