/*import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main (String [] args) {

        int fin = 0;
        String nom;
        CtrlDominiComprimir ctrlDominiComprimir = new CtrlDominiComprimir(); //-> Referencia a domini
        CtrlDominiDescomprimir ctrlDominiDescomprimir = new CtrlDominiDescomprimir();

        Comprimit resultat;

        while (fin == 0) {

            String usatge;
            System.out.println("Vols descomprimir o comprimir? -descomprimir -comprimir -carpeta -exit");
            Scanner teclado = new Scanner(System.in);
            usatge = teclado.nextLine();



            if (usatge.equals("comprimir")) {

                System.out.println("Selecciona l'arxiu a comprimir");
                String a, man_o_aut;
                teclado = new Scanner(System.in);
                a = teclado.nextLine();
                System.out.println("Indica el metode de compresio: -manual -automatic ");
                teclado = new Scanner(System.in);
                man_o_aut = teclado.nextLine();

                if (man_o_aut.equals("manual")) {

                    String algorisme;
                    System.out.println("Indica l'algorisme de compresio: -LZW -LZSS -LZ78 -JPEG");
                    teclado = new Scanner(System.in);
                    algorisme = teclado.nextLine();
                    System.out.println("Indica el nom del nou fitxer");
                    teclado = new Scanner(System.in);
                    nom = teclado.nextLine();

                    ctrlDominiComprimir.ComprimeixFitxerMan(a,algorisme,"C:\\Users\\joanf\\Documents",nom);


                    System.out.println("Compresio exitosa");
                }

                else if (man_o_aut.equals("automatic")) {

                    System.out.println("Indica el nom del nou fitxer");
                    teclado = new Scanner(System.in);
                    nom = teclado.nextLine();
                    ctrlDominiComprimir.ComprimeixFitxerAut(a,"C:\\Users\\joanf\\Documents",nom);
                    System.out.println("Compresio exitosa");

                }
            }

            else if (usatge.equals("descomprimir")) {


                System.out.println("Selecciona l'arxiu a descomprimir");

                    ctrlDominiDescomprimir.DescomprimirFitxer("C:\\Users\\joanf\\Documents\\aquielj.JPEG", "C:\\Users\\joanf\\Documents","resultat");
                    System.out.println("Descompresio exitosa");



            }

            else if (usatge.equals("carpeta")) {


                ctrlDominiComprimir.ComprimeixCarpetaAut("C:\\Users\\joanf\\Documents\\Carpeta", "C:\\Users\\joanf\\Documents\\Carpeta","Besultat");
                System.out.println("Descompresio exitosa");



            }

            else if (usatge.equals("descar")) {


                ctrlDominiDescomprimir.DescomprimirCarpeta("C:\\Users\\joanf\\Documents\\desc\\besultat.CPT", "C:\\Users\\joanf\\Documents\\Carpeta","carpeteta");
                System.out.println("Descompresio exitosa");



            }

            else if (usatge.equals("exit")) {
                fin = 1;
            }
        }
    }
}
*/