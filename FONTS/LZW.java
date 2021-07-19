import java.util.ArrayList;
import java.util.HashMap;

public class LZW implements Algorithmstrategy{


    public Comprimit comprimir(No_comprimit txt) {


        if (txt != null) {
            LZWfile file = new LZWfile();
            file.setFormat("UNICODE");

            String nocomprimit = txt.getBody();
            String[] entrada = nocomprimit.split("");
            HashMap<String, Integer> dict = new HashMap<String, Integer>();
            String concanetacio;
            int i = 0;
            while (i <= 255) {
                char ch = (char) i;
                String str = "" + ch;
                dict.put(str, i);
                ++i;
            }
            --i;

            String actual = "";
            String auxiliar = null;
            int pointer = 0;

            while (pointer <= entrada.length - 1) { //Mentre no s'arribi al EOF
                actual = entrada[pointer];

                if (auxiliar == null) {

                    concanetacio = actual;
                } else {
                    concanetacio = auxiliar + actual;
                }
                pointer++;


                if (dict.containsKey(concanetacio)) {

                    auxiliar = concanetacio;
                } else {

                    file.res.add(dict.get(auxiliar));


                    ++i;
                    dict.put(concanetacio, i);

                    auxiliar = actual;
                }

            }

            file.res.add(dict.get(auxiliar));
            return file;
        }

        return null;
    }




    public No_comprimit descomprimir(Comprimit comp) {

        if(comp != null) {


            TXT txt = new TXT();

            String body = comp.getBody();
            HashMap<Integer, String> dict = new HashMap<Integer, String>();
            String concanetacio;
            ArrayList<Integer> tre = new ArrayList<Integer>();
            tre = ((LZWfile) comp).res;
            int i = 0;
            while (i <= 255) {
                char ch = (char) i;
                String str = "" + ch;
                dict.put(i, str);
                ++i;
            }
            --i;
            ArrayList<String> resultat = new ArrayList<String>();


            int cod_vell, cod_nou, pointer = 0;
            String cad = null;
            String tmp = null;
            cod_vell = tre.get(pointer);
            String car = dict.get(cod_vell);
            resultat.add(car);
            ++pointer;

            while (pointer <= tre.size() - 1) {

                cod_nou = tre.get(pointer);

                if (dict.containsKey(cod_nou)) {

                    cad = dict.get(cod_nou);

                } else {

                    cad = dict.get(cod_vell);
                    cad = cad + car;

                }

                resultat.add(cad);
                car = cad.substring(0, 1);
                tmp = dict.get(cod_vell) + car;
                ++i;
                dict.put(i, tmp);
                cod_vell = cod_nou;
                ++pointer;
            }
            int j = 0;
            txt.body = "";
            while (j < resultat.size()) {

                txt.body += resultat.get(j);
                ++j;
            }



            return txt;
        }
        return null;
    }
//comit

}
