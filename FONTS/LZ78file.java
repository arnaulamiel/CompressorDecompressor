import java.util.*;

public class LZ78file extends Comprimit {

    private String body;

    public HashMap<Integer, String> MapComprimit = new HashMap<Integer, String>();

    LZ78file(){this.setNom(""); body = "";}
    LZ78file(String s){this.setNom(s); body = "";}

    @Override
    public String getFullNom() {
        return this.getNom() + ".LZ7";
    }

    @Override
    public void setBody(String s){
        body = s;
        int j = 1;
        String[] a = s.split(",");
        for(int i = 0;  i < a.length  ; ++i){
            MapComprimit.put(j,a[i]);
            ++j;
        }
        System.out.println("BODY "+getBody());
    }
    @Override
    public  String getBody(){return body = tradueix(MapComprimit);}
    //public  ArrayList<Integer> getBodyArr(){return tradueix(MapComprimit);}


    static String tradueix(HashMap<Integer, String> MapComprimit){
        //ArrayList<Integer> resultat = new ArrayList<Integer>();
        //String resultat ="";
        StringBuilder resultat = new StringBuilder(10000);

        if(!MapComprimit.isEmpty()){
            for(int i = 1; i <= MapComprimit.size() ; ++i) {
                //resultat += MapComprimit.get(i)+"," ;
                resultat.append(MapComprimit.get(i));
                resultat.append(",");
            }
        }
        //El String retornat retorna el mapa sense espais en el format 'num-char', amb un null a l'inici i sense el pointer (que podrem treure facilment d'un bucle ascendent)
        return resultat.toString();
    }


}
