import java.util.HashMap;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class CtrlPresentacio {

    private static CtrlDominiComprimir ctrlDominiComprimir; //-> Referencia a domini
    private static CtrlDominiDescomprimir ctrlDominiDescomprimir;
    private VisualPresentacio visualPresentacio;
    //-> Referències a totes les finestres utilitzades
    private static Boolean isopened = false;
    private static String pathGlobal;

    //////////////////////// Constructor y metodos de inicializacion

    public CtrlPresentacio() {
        ctrlDominiComprimir = new CtrlDominiComprimir();
        ctrlDominiDescomprimir = new CtrlDominiDescomprimir();
        visualPresentacio = new VisualPresentacio();
        // -> Inicialitzar una nova finestra principal de VisualPresentacio i crear una instancia dels dominis o sino
    }

    public void inicializarPresentacion() {
        //ctrlDominio.inicializarCtrlDominio(); -> Si vull incialitzar algo al domini
        visualPresentacio.main(null);
    }
    private static void setPathG(String p){
        pathGlobal = p;
    }

    /////////////////////// Llamadas al controlador de dominio


    //RETORNEN UN STRING ON SI TOT HA ANAT BÉ ES RETORNA 'OK' I SI HA HAGUT ALGUN ERROR ES RETORNA 'ERROR: '+ EL ERROR (JA SIGUI ERROR DE PATH ENTRANT O DESTÍ, ERROR DE COMPRESSIÓ/DESCOMPRESSIÓ...)

    //Rep un path on és el fitxer, l'algorisme seleccionat de compressió i el path on el volem guardar
    public static String ComprimeixFitxerMan(String path, String alg, String dest,String nom, Integer qualitat){
        System.out.println("Abans: path "+path+"alg "+alg+" dest "+dest+" nom "+nom);
        ctrlDominiComprimir.ComprimeixFitxerMan(path, alg, dest,nom, qualitat);
        System.out.println("despre");
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return "OK";
    }
    //Rep un path on és el fitxer i el path on el volem guardar
    public static String ComprimeixFitxerAut(String path, String dest, String nom) throws ExecutionException, InterruptedException {


        ctrlDominiComprimir.ComprimeixFitxerAut(path, dest, nom);
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return "OK";

    }

    //Rep un path on és la carpeta, l'algorisme seleccionat de compressió i el path on el volem guardar
    public static String ComprimeixCarpetaManual(String path, String alg, String dest,String nom, Integer qualitat) throws ExecutionException, InterruptedException {
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiComprimir.ComprimeixCarpetaManual(path,  alg, dest, nom, qualitat);
    }
    //Rep un path on és la carpeta i el path on la volem guardar
    public static String ComprimeixCarpetaAut(String path, String dest,String nom) throws ExecutionException, InterruptedException {
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiComprimir.ComprimeixCarpetaAut(path, dest,nom);
    }
    //Rep un path on és la carpeta, un HashMap que indica per cada algorisme els fitxers que hem de comprimir amb aquest algorisme (ex: LZ78=cau.txt,a.txt , JPEG=b.ppm...) i el path on la volem guardar
    public static String ComprimeixCarpetaSemiaut(String path, HashMap<String,String> FitxersIAlg, String dest,String nom,Integer qualitat) throws ExecutionException, InterruptedException {
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiComprimir.ComprimeixCarpetaSemiaut(path, FitxersIAlg, dest, nom, qualitat);
    }

    //Rep un path on és el fitxer i el descomprimeix en l'adreça dest
    public static String DescomprimirFitxer(String path, String dest,String nom){
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiDescomprimir.DescomprimirFitxer(path, dest,nom);
    }
    //Rep un path on és la carpeta i la descomprimeix en l'adreça dest
    public static String DescomprimirCarpeta(String path, String dest,String nom) throws ExecutionException, InterruptedException {
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiDescomprimir.DescomprimirCarpeta(path, dest,nom);
    }
    public static ArrayList<String> getLlistaArxius(String pathsrc){
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return ctrlDominiComprimir.getLlistaArxius(pathsrc);
    }
    public static ArrayList<String> getLlistaEstadistiques(){
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return CtrlEstadistiques.getLlistaEstadistiques();
        /*ArrayList<String> a = new ArrayList<String>();
        a.add("hola");
        return a;*/
    }
    public static ArrayList<Double> getEstadisticaByName(String seleccionat){
        if(isopened){
            isopened = false;
            BorrarObrirVisual(pathGlobal);
        }
        return CtrlEstadistiques.getEstadistica(seleccionat);

    }
    public static void OpenComprimit(String path, Boolean isOpen){
        isopened = true;
        CtrlDominiObrir.Obrir(path);
        setPathG(path);
    }
    public static void BorrarObrirVisual(String path){
        //CtrlDominiObrir.BorrarObrirVisual(path);
    }


}