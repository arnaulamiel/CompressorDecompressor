
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CtrlDominiDescomprimir {

   private static CtrlPersistencia disk = new CtrlPersistencia();
    private static No_comprimit nocomp;
    private static Comprimit comp;
    private static CompressedFolder compfol = new CompressedFolder();

    public static ArrayList<Double> est = new ArrayList<Double>();

    public static String DescomprimirFitxer(String path, String dest, String nom) {
        long a = 0;
        long inicic = System.currentTimeMillis();
        comp = disk.ReadComprimit(path);
        nocomp = new Algorithm().decideixalgdes(comp);
        nocomp.setNom(nom);
        try {
            a = disk.WriteNoComprimit(nocomp,dest); //hem de distingir entre charset si tots retornen un String ?¿
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double) comp.getTamany());
        est.add((double) a);
        est.add((double) comp.getTamany()/a);
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nom);

        return "OK";
    }

    public static String DescomprimirCarpeta(String path, String dest, String nomCarpeta)  {

        int i = 0;
        Double countfake;
        long a = 0;
        long inicic = System.currentTimeMillis();
        double count = 0;
        String nom;
        boolean b = disk.MakeFolder(dest,nomCarpeta);
        String aux = dest + "\\" + nomCarpeta;
        compfol = disk.ReadCompressedFolder(path);
        comp = compfol.GetComprimit(i);

        while(comp != null) {

            if(comp instanceof CompressedFolder) {


                ++i;
                a = a +  DescomprimirCarpetaDinsCarpeta(path,aux,comp.getNom(),a,i, (CompressedFolder)comp);
            }

            else {
                nom = comp.getNom();
                nocomp = new Algorithm().decideixalgdes(comp);
                nocomp.setNom(nom);
                try {
                    a =  a + disk.WriteNoComprimit(nocomp, aux);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
            ++i;
            comp = compfol.GetComprimit(i);

        }

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double) compfol.getTamany());
        est.add((double) a);
        est.add((double)  compfol.getTamany()/a);
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nomCarpeta);


        return "OK";

    }

    public static long DescomprimirCarpetaDinsCarpeta(String path, String dest, String nomCarpeta, long a, Integer i, CompressedFolder compfoldergran) {

        i = 0;
        Double countfake;
        String nom;
        boolean b = disk.MakeFolder(dest,nomCarpeta);
        String aux = dest + "\\" + nomCarpeta;
        //compfol = disk.ReadCompressedFolder(path);
        comp = compfoldergran.GetComprimit(i);

        while(comp != null) {

            if (comp instanceof CompressedFolder && comp != compfoldergran) {

                a = a + DescomprimirCarpetaDinsCarpeta(path, aux, comp.getNom(), a, i, (CompressedFolder)comp);
            }
            else {
                nom = comp.getNom();
                nocomp = new Algorithm().decideixalgdes(comp);
                nocomp.setNom(nom);
                try {
                    a = a + disk.WriteNoComprimit(nocomp, aux);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            ++i;
            comp = compfoldergran.GetComprimit(i);

        }


        return a;

    }
    //comit

}