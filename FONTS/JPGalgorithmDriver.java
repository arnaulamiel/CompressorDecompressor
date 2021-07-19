import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class JPGalgorithmDriver {
    private static long bytes1, bytes2;
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Vols comprimir o descomprimir o sortir?");
        String s = teclado.nextLine();
        JPGalgorithm.setQuality(10);
        JPGfile jpg;
        PPM ppm;
        long time;
        JPGalgorithm alg = new JPGalgorithm();
        while (!s.equals("sortir")) {
            switch (s) {
                case "comprimir":
                    System.out.println("Indica el nom del fitxer");
                    s = teclado.nextLine();
                    ppm = readPPM(s);
                    time = System.currentTimeMillis();
                    jpg = alg.comprimir(ppm);
                    time = System.currentTimeMillis() - time;
                    System.out.print("TEMPS = ");System.out.print(time);System.out.println(" ms");
                    writeJPG(jpg);
                    System.out.println("Compressio acabada:");
                    System.out.print("RATIO DE COMPRESSIO = ");System.out.println((double)bytes2/(double)bytes1);
                    break;
                case "descomprimir":
                    System.out.println("Indica el nom del fitxer");
                    s = teclado.nextLine();
                    jpg = readJPG(s);
                    time = System.currentTimeMillis();
                    ppm = alg.descomprimir(jpg);
                    time = System.currentTimeMillis() - time;
                    System.out.print("TEMPS = ");System.out.print(time);System.out.println(" ms");
                    writePPM(ppm);
                    System.out.println("Descompressio acabada:");
                    System.out.print("RATIO DE COMPRESSIO = ");System.out.println((double)bytes1/(double)bytes2);
                    break;
                case "test":
                    System.out.println("Indica el nom del fitxer");
                    s = teclado.nextLine();
                    ppm = readPPM(s);
                    writePPM(ppm);
                    System.out.println("tested");
                    break;
                case "test2":
                    System.out.println("Indica el nom del fitxer");
                    s = teclado.nextLine();
                    ppm = readPPM(s);
                    jpg = alg.comprimir(ppm);
                    ppm = alg.descomprimir(jpg);
                    writePPM(ppm);
                    writeJPG(jpg);
                    System.out.println("tested");
                    break;
                default:
                    break;
            }
            System.out.println("Vols comprimir o descomprimir o sortir?");
            s = teclado.nextLine();
        }
    }

    private static PPM readPPM(String file){
        PPM ppm = null;
        long T = System.currentTimeMillis();
        try {
            bytes1 = new File(file+".ppm").length();
            ppm = new PPM(file);
            ppm.setBody(Files.readAllBytes(Paths.get(file+".ppm")));
        }
        catch (IOException ex) {System.err.println(ex);}
        T = System.currentTimeMillis() - T;
        System.out.print("TEMPS Lectura = ");System.out.print(T);System.out.println(" ms");
        return ppm;
    }

    private static void writePPM(PPM ppm){
        long T = System.currentTimeMillis();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File ("OUT.ppm")),ppm.getFormat()));
            writer.write(ppm.getBody());
            writer.close();
            bytes2 = new File ("OUT.ppm").length();
        }
        catch (IOException ex) {System.err.println(ex);}
        T = System.currentTimeMillis() - T;
        System.out.print("TEMPS Escriptura= ");System.out.print(T);System.out.println(" ms");
    }

    private static JPGfile readJPG(String file){
        JPGfile jpg = null;
        long T = System.currentTimeMillis();
        try {
            File F = new File(file+".jpg");
            bytes1 = F.length();
            Scanner scan = new Scanner(new FileInputStream(F),"UTF-8");
            scan.useDelimiter("\\A");
            jpg = new JPGfile(file);
            jpg.setBody(scan.next());
        }
        catch (IOException ex) {System.err.println(ex);}
        T = System.currentTimeMillis() - T;
        System.out.print("TEMPS Lectura = ");System.out.print(T);System.out.println(" ms");
        return jpg;
    }

    private static void writeJPG(JPGfile jpg){
        long T = System.currentTimeMillis();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File ("OUT.jpg")),"UTF-8"));
            writer.write(jpg.getBody());
            writer.close();
            bytes2 = new File ("OUT.jpg").length();
        }
        catch (IOException ex) {System.err.println(ex);}
        T = System.currentTimeMillis() - T;
        System.out.print("TEMPS Escriptura= ");System.out.print(T);System.out.println(" ms");
    }
}
