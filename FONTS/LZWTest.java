/*
import java.util.*;
import static org.junit.Assert.*;

    public  class LZWTest {

        ArrayList<Integer> expected = new ArrayList<Integer>();



        @org.junit.Test
        public void comprimir_test(){


            expected.add(84);
            expected.add(114);
            expected.add(101);
            expected.add(115);
            expected.add(32);
            expected.add(116);
            expected.add(114);
            expected.add(105);
            expected.add(115);
            expected.add(116);
            expected.add(258);
            expected.add(260);
            expected.add(105);
            expected.add(103);
            expected.add(257);
            expected.add(115);


            LZWfile file = new LZWfile();
            String nocomprimit = "Tres tristes tigres";
            String[] entrada = nocomprimit.split("");
            HashMap<String,Integer> dict = new HashMap<String,Integer>() ;
            String concanetacio;
            int i = 0;
            while (i <= 255) {
                char ch = (char) i;
                String str = "" + ch;
                dict.put(str,i);
                ++i;
            }
            --i;

            assertEquals(255, i);

            String actual = "";
            String auxiliar = null;
            int pointer = 0;

            while(pointer <= entrada.length - 1){ //Mentre no s'arribi al EOF
                actual = entrada[pointer];

                if (auxiliar == null) {

                    concanetacio = actual;
                }

                else {
                    concanetacio = auxiliar + actual;
                }
                pointer++;



                if(dict.containsKey(concanetacio)){

                    auxiliar = concanetacio;
                }
                else {

                    file.res.add(dict.get(auxiliar));


                    ++i;
                    dict.put(concanetacio,i);

                    auxiliar = actual;
                }

            }

            assertEquals(entrada.length,pointer);

            file.res.add(dict.get(auxiliar));

            assertNotNull(file);
            assertEquals(expected,file.res);


        }


        @org.junit.Test
        public void descomprimir() {


            TXT txt = new TXT();
            HashMap<Integer, String> dict = new HashMap<Integer, String>();
            String concanetacio;
            ArrayList<Integer> tre = new ArrayList<Integer>();
            tre.add(84);
            tre.add(114);
            tre.add(101);
            tre.add(115);
            tre.add(32);
            tre.add(116);
            tre.add(114);
            tre.add(105);
            tre.add(115);
            tre.add(116);
            tre.add(258);
            tre.add(260);
            tre.add(105);
            tre.add(103);
            tre.add(257);
            tre.add(115);

            int i = 0;
            while (i <= 255) {
                char ch = (char) i;
                String str = "" + ch;
                dict.put(i,str);
                //ajudant.add(i);
                ++i;
            }
            --i;
            assertEquals(255, i);
            ArrayList<String> resultat = new ArrayList<String>();


            int cod_vell, cod_nou, pointer = 0;
            String cad = null;
            String tmp = null;
            cod_vell = tre.get(pointer);
            String car = dict.get(cod_vell);
            resultat.add(car);
            ++pointer;

            while (pointer <= tre.size() - 1 ) {

                cod_nou = tre.get(pointer);

                if (dict.containsKey(cod_nou)) {

                    cad = dict.get(cod_nou);

                }
                else {

                    cad = dict.get(cod_vell);
                    cad = cad + car;

                }

                resultat.add(cad);
                car = cad.substring(0,1);
                tmp = dict.get(cod_vell) + car;
                ++i;
                dict.put(i, tmp);
                cod_vell = cod_nou;
                ++pointer;
            }

            assertEquals(tre.size(),pointer);
            int j = 0;
            txt.body = "";
            while(j < resultat.size()) {

                txt.body += resultat.get(j);
                ++j;
            }

            assertNotNull(txt);
            assertEquals("Tres tristes tigres",txt.body);


        }

        @org.junit.Test
        public void setBody() {
            String s = "holahola";
            ArrayList<Integer> tre = new ArrayList<Integer>();
            tre.add(104);
            tre.add(111);
            tre.add(108);
            tre.add(97);
            tre.add(104);
            tre.add(111);
            tre.add(108);
            tre.add(97);

            int i = 0;
            int w;

            while(i < s.length() ) {
               expected.add((int) s.charAt(i));
                ++i;
            }

            assertEquals(tre,expected);
        }

        @org.junit.Test
        public void getBody(){
            ArrayList<Integer> tre = new ArrayList<Integer>();
            tre.add(3500);
            tre.add(111);
            tre.add(108);
            tre.add(97);
            tre.add(104);
            tre.add(111);
            tre.add(108);
            tre.add(97);
            StringBuilder s = new StringBuilder(tre.size());
            for (int i = 0; i < tre.size(); i++) s.append((char)(int)tre.get(i));
            String si = s.toString();
            assertEquals("holahola",si);
            int i = 0;
            int w;

            while(i < si.length() ) {
                expected.add((int) si.charAt(i));
                ++i;
            }

            assertEquals(tre,expected);
        }


    }

*/