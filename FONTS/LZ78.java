import java.io.*;
import java.util.*;

public class LZ78 implements Algorithmstrategy {

    public LZ78file comprimir(No_comprimit txt){

        LZ78file file = new LZ78file();
        file.setFormat("ISO-8859-1");
        String nocomprimit = txt.getBody();
        file.setNom(txt.getNom());
        //L'arxiu que ens entra
        String[] entrada = nocomprimit.split("");
        // La Llista diccionari que omplirem per a treballar
        ArrayList<String> dict = new ArrayList<>();
        //El diccionari resultant
        //L'actual char/conjunt de chars que estem mirant
        String actual;
        int index = 0;
        dict.add (null); //Afegim a la posicio 0 del dict un null perque coincideixi amb l'index
        int pointer = 0;

        while(index < entrada.length ){ //Mentre no s'arribi al EOF
            actual = entrada[index];
            pointer++;
            if(dict.contains(actual)){
                String anterior="";
                //Mirem quin ha de ser el conjunt de caracters a afegir
                actual = CalculaActual(actual, dict, index, entrada);
                int j = index;
                anterior = CalculaAnterior(anterior, actual, j, entrada);

                //Obtenim un map amb el punter i un String de la direccio on es lo anterior mes la lletra actual [ (posicio de caracters anteriors al diccionari , ultim caracter vist )]
                if (anterior.equals("NO")) {

                    file.MapComprimit.put(pointer,(dict.indexOf(actual)+"-"+null));
                }
                else{

                    file.MapComprimit.put(pointer, (dict.indexOf(anterior)+"-"+UltimCaracterNouActual(actual)));
                }
                //Actualitzem l'index
                index += actual.length();
                //Actualitzem la llista amb la sequencia actual
                dict.add(actual);
            }
            else {
                //Com no ho hem trobat està a la direcció 0

                file.MapComprimit.put(pointer, ("0-"+actual));
                //Actualitzem la llista
                dict.add(actual);
                //Actualitzem l'actual
                index++;
            }

        }
        //System.out.println("out "+file.MapComprimit);
        //System.out.println("resultat compressio: "+resultat);
        return file;

    }
    public TXT descomprimir(Comprimit comp){

        TXT txt = new TXT();
        String StringComprimit = comp.getBody();
        txt.setNom(comp.getNom());
        HashMap<Integer,String> comprimit = new HashMap<>();
        //System.out.println("comprimit: "+comprimit);
        //System.out.println("StringComprimit: "+StringComprimit);
        String[] a = StringComprimit.split(",");
        if(StringComprimit.equals("0-,")){txt.setBody(""); return txt;}
        int j = 1;
        for(int i = 0;  i < a.length  ; ++i){
            comprimit.put(j,a[i]);
            ++j;
        }
        Collection<String> valor = comprimit.values();
        StringBuilder output = new StringBuilder();

        if(valor.size()>0){
            ArrayList<String> dict = new ArrayList<>();
            String key,value;
            for(String s : valor){
                // Agafem la clau i el valor de cada string
                if(s.length()!= 0) {
                    String[] ss = s.split("-");
                    key = ss[0];
                    if (ss.length == 1) value = "-";
                    else value = ss[1];
                    int index = Integer.parseInt(key);
                    //Si la key es 0 insertem el valor tal qual
                    //Si en canvi es diferent hem d'anar a la posició que ens indica i afegir-hi el que hi havia mes el valor

                    if (index == 0) dict.add(value);
                    else {
                        if (value.equals("null")) dict.add(dict.get(index - 1));
                        else dict.add(dict.get(index - 1) + value);
                    }
                }
            }
            //Per acabar omplim un StringBuilder(mes eficient) amb el resultat final del text descomprimit
            for(String c : dict){
                output.append(c);
            }
        }
        //if(output.toString().contains("null"))output.delete(output.length()-4,output.length());

        //System.out.println("resultat descompressio : "+output);
        txt.setBody(output.toString());

        return txt;

    }
    //Funció per a obtenir els caràcters que estan al diccionari i retorna la pròxima sequencia de chars que no existeix al diccionari
    private String CalculaActual(String actual, ArrayList<String> dict, Integer index, String[] entrada){
        for(int i = index; i < entrada.length -1; ++i){
            if(dict.contains(actual) && entrada[i+1] != null)actual += entrada[i+1];
        }
        //System.out.println("entradaSize: "+(entrada.length - 1)+ " actual: "+actual+" index: "+index);
        return actual;
    }
    //Funcio per a obtenir els caràcters que son anteriors a l'ultim actual
    private String CalculaAnterior(String anterior, String actual, Integer j, String[] entrada){
        for(int i = 0; i < actual.length() - 1 ; i++){
            anterior += entrada [j];
            j++;
        }
        //Si nomes hi ha un actual, és a dir és l'ultim de la entrada
        if(actual.length() == 1) anterior = "NO";
        return anterior;
    }
    //Funció per trobar el char nou a afegir al Map. L'ultim del actual.
    private String UltimCaracterNouActual(String actual){
        String [] a = actual.split("");
        return a[a.length - 1];
    }
}

