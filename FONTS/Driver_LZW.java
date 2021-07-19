import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;


public class Driver_LZW {

    public static void main(String[] args) throws IOException {

        String s = "s";
        String x = "";
        String nom;
        LZWfile lzw = new LZWfile();
        TXT txt;
        LZW alg;
        String ei = "d";
        long inicic;
        long fic;
        double tempsc;
        double val;
        double val1;
        double factor;

        System.out.println("Indica si vols /comprimir  /descomprimir /exit");

        while (!s.equals("exit")) {

            switch (s) {
                case "comprimir":


                    System.out.println("Indica el que vols comprimir");
                    s = System.console().readLine();
                    File filetam = new File(s);
                    long fileSizeInBytes = filetam.length();
                    if(fileSizeInBytes == 0) System.out.println("Fitxer buit - Indica si vols /comprimir  /descomprimir /exit");
                    else {
                        FileReader f = new FileReader(s);
                        BufferedReader b = new BufferedReader(f);

                        while ((ei = b.readLine()) != null) {
                            x = x + ei;
                        }
                        txt = readTXT(x);

                        alg = new LZW();
                        inicic = System.currentTimeMillis();
                        lzw = (LZWfile) alg.comprimir(txt);
                        fic = System.currentTimeMillis();
                        tempsc = (double) ((fic - inicic) / 1000);

                        System.out.println("Indica el nom de l'arxiu");
                        s = System.console().readLine();
                        writeLZW(lzw, s);
                        System.out.println("Compresio exitosa");
                        System.out.println("Tamany no comprimit(Bytes):");
                        System.out.println(fileSizeInBytes);
                        val = fileSizeInBytes;
                        System.out.println("Tamany comprimit(Bytes):");
                        fileSizeInBytes = new File(s + ".LZW").length();
                        System.out.println(fileSizeInBytes);
                        System.out.println("Factor de conmpresio");
                        val1 = fileSizeInBytes;
                        factor = val1 / val;
                        System.out.println(factor);
                        if (factor < 1) System.out.println("La compresio es efectiva");
                        else System.out.println("La compresio no es efectiva");
                        System.out.println("Temps en comprimir(en milisegons)");
                        System.out.println(fic);
                        x = "";
                        lzw.setBody("");
                        lzw.res = null;
                        ei = "";

                        System.out.println("Indica si vols /comprimir  /descomprimir /exit");
                    }
                    break;
                case "descomprimir":

                    System.out.println("Indica el no comprimit a descomprimir");
                    alg = new LZW();
                    int e;
                    ArrayList<Integer> ar = new ArrayList<Integer>();

                    s = System.console().readLine();
                    String a = s;
                    FileInputStream fis = new FileInputStream(s);
                    DataInputStream inputStream = new DataInputStream(new BufferedInputStream(fis));
                    int longi = inputStream.available();
                    int w = 0;

                    while(--longi/2 > 0) {
                        w = inputStream.readShort();
                        ar.add(w);
                        --longi;
                    }
                    w = inputStream.readShort();
                    ar.add(w);
                    inputStream.close();


                    lzw.res = ar;
                    inicic = System.currentTimeMillis();
                    txt = (TXT) alg.descomprimir(lzw);
                    fic = System.currentTimeMillis();
                    tempsc = (double) ((fic - inicic)/1000);
                    System.out.println("Indica el nom de l'arxiu");
                    s = System.console().readLine();
                    writeTXT(txt,s);
                    System.out.println("Descompresio exitosa");

                    System.out.println("Tamany comprimit:");
                    System.out.println(new File(a).length());
                    System.out.println("Tamany no comprimit(Bytes):");
                    System.out.println(new File(s + ".TXT").length());
                    System.out.println("Factor de descompresio(Bytes):");
                    val = new File(a).length();
                    val1 = new File(s + ".TXT").length();
                    factor = val/val1;
                    System.out.println(factor);
                    if(factor < 1) System.out.println("La compresio havia sigut efectiva");
                    else System.out.println("La compresio no havia sigut efectiva");
                    System.out.println("Temps en comprimir(en milisegons)");
                    System.out.println(fic);
                    x = "";
                    ei = "";
                    ar = null;
                    System.out.println("Indica si vols /comprimir  /descomprimir /exit");
                    break;
                default:
                    break;
            }

            s = System.console().readLine();


        }
    }


    private static TXT readTXT(String file) {

        TXT txt = new TXT();
        txt.body = file;
        txt.setTamany(file.length());
        return txt;

    }

    private static void writeLZW(LZWfile file, String nom) {
        file.setNom(nom);
        try {

            FileOutputStream fos = new FileOutputStream(file.getFullNom());
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File ("FitxerSergi.LZW")),"UNICODE"));

            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File ("FitxerSergi2.LZW")),"UNICODE"));
            writer2.write(file.getBody());
            writer2.close();

            for (int i = 0; i < file.res.size(); i++) {
                outStream.writeShort(file.res.get(i));
                writer.write((char)(int)file.res.get(i));
            }

            writer.close();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void writeTXT(TXT file, String nom) throws IOException {
        file.setNom(nom);
        DataOutputStream outStream = null;
        try {
            FileOutputStream fos = new FileOutputStream(file.getFullNom());
            outStream = new DataOutputStream(new BufferedOutputStream(fos));
            outStream.writeBytes(file.body);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outStream.close();

    }

}



