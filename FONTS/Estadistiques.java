import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Estadistiques {

    public static void main(String[] args) {
        Estadistiques es = new Estadistiques();
        ArrayList<Double> ar = new ArrayList<>();
        ar.add(1d);
        ar.add(1d);
        ar.add(1d);
        ar.add(1d);
        ar.add(1d);
        es.setParameters(ar,"test");
        Estadistiques est = new Estadistiques();
        System.out.println(es.getBody());
        est.setBody(es.getBody());
        System.out.println(est.getBody());
    }

    private  double tamanyinicial;
    private  double tamanyfinal;
    private  double temps;
    private  double factor;
    private  String Nom_document;
    private  boolean compresio;

    /*public double getCom{
        double b = (double)1;
        if(compresio) return b;
        else return 0;
    } */

    public String getNom()
    {
        return Nom_document;
    }

    public Double getTI()
    {
        return tamanyinicial;
    }
    public Double getTF()
    {
        return tamanyfinal;
    }
    public Double gett()
    {
        return temps;
    }
    public Double getfactor()
    {
        return factor;
    }

    public void setTI(double ti)
    {
        this.tamanyinicial = ti ;
    }
    public void setTF(double tf)
    {
        this.tamanyfinal = tf ;
    }
    public void setTT(double t)
    {
        this.temps = t ;
    }
    public void setF(double f)
    {
        this.factor = f ;
    }



    public String getBody()
    {
        String res ;
        JSONObject Estadistica = new JSONObject();
        JSONArray content = new JSONArray();
        Estadistica.put("nom", this.Nom_document);
        Estadistica.put("operacio",compresio);
        Estadistica.put("tamanyinicial",tamanyinicial);
        Estadistica.put("tamanyfinal",tamanyfinal);
        Estadistica.put("temps",temps);
        Estadistica.put("factor",factor);
        res = Estadistica.toString();
        return res;
    }
    public void setParameters(ArrayList<Double> params, String nom_document)
    {

        if(params.get(0) == 0) compresio = false;
        tamanyinicial = params.get(1);
        tamanyfinal = params.get(2);
        factor = params.get(3);
        temps = params.get(4);
        this.Nom_document = nom_document;
    }

    public ArrayList<Double> getParameters()
    {
        ArrayList<Double> res = new ArrayList<>();
        if(compresio)res.add(1d);
        else res.add(0d);
        res.add(tamanyinicial);
        res.add(tamanyfinal);
        res.add(factor);
        res.add(temps);
        return res;
    }

    public void setBody(String b)
    {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(b);
            JSONObject est = (JSONObject) obj;
            this.Nom_document = est.get("nom").toString();
            this.compresio = (boolean) est.get("operacio");
            this.tamanyinicial = ((Double)est.get("tamanyinicial"));
            this.tamanyfinal = (Double)est.get("tamanyfinal");
            this.factor = (Double)est.get("factor");
            this.temps = (Double)est.get("temps");

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
