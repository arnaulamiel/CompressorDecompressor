import java.util.*;

public abstract class Comprimit {

    private String nom;
    private String format;
    private long tamany;

    public long getTamany() {return tamany;}
    public void setTamany(long i) {tamany = i;}

    public abstract String getBody();
    public abstract void setBody(String b);

    public void setFormat(String s){format = s;}
    public String getFormat(){return format;}

    public abstract String getFullNom();
    public String getNom()
    {
        return nom;
    }
    public void setNom(String n){nom = n;}

    }

