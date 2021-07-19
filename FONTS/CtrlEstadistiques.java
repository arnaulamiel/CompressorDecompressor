import java.util.ArrayList;

public class CtrlEstadistiques {
    private static ArrayList<Double> est = new ArrayList<Double>();


    public static void setEstadistica(ArrayList<Double> esto, String nom) {


        Estadistiques e = new Estadistiques();
        e.setParameters(esto,nom);
        DiskIO.writeEstadistiques(e);

    }

    public static ArrayList<Double> getEstadistica(String nom) {


        est.clear();
        Estadistiques e =  DiskIO.readEstadistiques(nom);
        est.add((double)1);
        est.add(e.getTI());
        est.add(e.getTF());
        est.add(e.getfactor());
        est.add(e.gett());

        return est;


    }

    public static ArrayList<String> getLlistaEstadistiques(){
        return DiskIO.OpenStats();
    }



   /* public static ArrayList<String> getLlistaArxiusEs() {

        int index = 0;
        ArrayList<String> noms = new ArrayList<String>();

        nocomp = disk.ReadElementCarpeta(path,index);
        while(nocomp != null) {

            if(nocomp instanceof Carpeta) return null;
            else {
                noms.add(nocomp.getNom());

                ++index;
                nocomp = disk.ReadElementCarpeta(path, index);
            }
        }
        return noms;
    }*/
    //comit
}