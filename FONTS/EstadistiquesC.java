import java.util.ArrayList;

public class EstadistiquesC {

    public static long tamanyinicial;
    public static long tamanyfinal;
    public static double temps;
    public static double factor;
    public static ArrayList<EstadistiquesC> historial = new ArrayList<EstadistiquesC>();

    public EstadistiquesC(double ini, double fi, long tamany_i, long tamany_fi) {

        this.tamanyinicial = tamany_i;
        this.tamanyfinal = tamany_fi;
        this.temps = (double) ((ini - fi) / 1000);
        this.factor = tamany_fi/tamany_i;
    }

    public void afegeix_histo(EstadistiquesC e) {
        historial.add(e);
    }

    public ArrayList<EstadistiquesC> getHistorial() {
        return historial;
    }
    //comit
}