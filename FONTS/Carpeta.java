import java.util.ArrayList;


public class Carpeta extends No_comprimit {

    private ArrayList<No_comprimit> carpeta;
    public String nom;

    public Carpeta(){}
    public Carpeta(String nom)
    {
        this.nom = nom;
    }


    @Override
    public String getBody() {
        String s = "";
        return s;
    }

    @Override
    public void setBody(String s) {

    }

    @Override
    public String getFullNom() {
        return nom + ".fld";
    }

    @Override
    public ArrayList<No_comprimit> getContent() {
        return carpeta;
    }

    public void Add_element(No_comprimit n)
    {
        carpeta.add(n);
    }
}
