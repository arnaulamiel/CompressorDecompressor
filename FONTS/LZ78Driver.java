import java.io.*;
import java.util.*;


public class LZ78Driver {
    private static LZ78file fileG;
    public static void main(String[] args) {
        //String s = "comprimir";
        String fi = "";
        LZ78file file;
        TXT txt;
        LZ78 alg;
        String nom;
        String path;
        System.out.println("Indica si vols comprimir,  descomprimir o sortir");
        String s = System.console().readLine();
        while (!s.equals("sortir")) {
            switch (s) {
                case "comprimir":
                    System.out.println("Indica el nom del fitxer:");
                    nom = System.console().readLine();
                    //String nom = "NOM";
                    txt = readTXT(nom);
                    alg = new LZ78();
                    long inicic = System.currentTimeMillis();
                    file = (LZ78file)alg.comprimir(txt);
                    long fic = System.currentTimeMillis();
                    double tempsc = (double) ((fic - inicic)/1000);
                    file.setNom(nom);
                    //System.out.println("MapComprimit " +file.MapComprimit);
                    //file.body = file.tradueix(file.MapComprimit);
                    //fileG = file;
                    //System.out.println("+++: " +file.body);
                    System.out.println("Compresio exitosa");
                    System.out.println("Tamany no comprimit:");
                    System.out.println(txt.getTamany());
                    System.out.println("Temps de compressió: "+tempsc);
                    System.out.println("Tamany comprimit:");
                    //System.out.println(file.setBody(body).length());
                    System.out.println("Escriu el nom amb el que es guarda: ");
                    nom = System.console().readLine();
                    writeLZ78(file, nom);
                    System.out.println("Indica si vols comprimir  / descomprimir / sortir");

                    break;
                case "descomprimir":
                    System.out.println("Indica el nom del fitxer:");
                    nom = System.console().readLine();

                    alg = new LZ78();
                    file = readLZ78(nom);
                    //System.out.println("+++: " +file.body);
                    long inicid = System.currentTimeMillis();
                    txt = alg.descomprimir(file);
                    System.out.println("Descompresio exitosa");
                    long fid = System.currentTimeMillis();
                    double tempsd = (double) ((fid - inicid)/1000);
                    System.out.println("Temps de descompressió: "+tempsd);
                    System.out.println("Indica el nom del sortida:");
                    nom = System.console().readLine();
                    writeTXT(txt,nom);
                    //System.out.println("+++: " +txt.body);
                    //fi = "acabaBucle";
                    System.out.println("Indica si vols comprimir,  descomprimir o sortir");

                    break;
                default:
                    break;
            }
            //s = "descomprimir";
            //if(fi.equals("acabaBucle"))s="exit";
            s = System.console().readLine();


        }
    }
    private static TXT readTXT(String nom){

        String body = "";
        FileInputStream archivo;
        InputStreamReader fr;
        BufferedReader reader;
        TXT txt = new TXT();
        txt.setNom(nom);
        try {
            archivo = new FileInputStream(nom);
            fr = new InputStreamReader (archivo,"Cp1252");
            reader = new BufferedReader(fr);
            while (reader.ready()) {
                //System.out.print(reader.readLine());
                body += reader.readLine();
            }
        }
        catch (IOException ex) {System.err.println(ex);}
        txt.setBody(body);
        return txt;
    }
    private static LZ78file readLZ78(String name) {
        LZ78file file = new LZ78file(name);
        String body = "";
        //FileInputStream archivo = null;
        //InputStreamReader fr = null;
        //BufferedReader reader = null;
        String r ;
        try {
           /* archivo = new FileInputStream(path);
            fr = new InputStreamReader (archivo,"Cp1252");
            reader = new BufferedReader(fr);
            while (reader.ready()) {
                //System.out.print(reader.readLine());
                body += reader.readLine();
            }*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(name), "ISO-8859-1"));
            while ((r = reader.readLine()) != null)
            {
                body += r;
            }
            reader.close();

        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        file.setBody(body);
        return file;
    }
    private static void writeLZ78(LZ78file file, String nom)
    {
        file.setNom(nom);
        try {

            //Collection<String> bodys = file.MapComprimit.values();
            //String body = file.getBody();
            //System.out.println("BODY: "+body);
            /*FileOutputStream fos = new FileOutputStream(file.getNom());
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
            //String body = bodys.toString();
            outStream.writeBytes(bodys);
            outStream.close();*/
            String body = file.getBody();
            FileOutputStream fos = new FileOutputStream(file.getNom());
            byte[] stb = body.getBytes("ISO-8859-1");
            fos.write(stb);
            fos.close();

        }

        catch (IOException ex)
        {
            System.err.println(ex);
        }

    }


    private static void writeTXT(TXT txt, String path)
    {
        txt.setNom(path);
        try {
            String body = txt.getBody();
            //System.out.println(body);
            FileOutputStream fos = new FileOutputStream(txt.getNom()+".txt");
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
            outStream.writeBytes(body);
            outStream.close();
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }


}

