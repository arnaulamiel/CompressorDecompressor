import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiComprimir {
    public static CtrlPersistencia disk = new CtrlPersistencia();
    public static No_comprimit nocomp;
    public static Comprimit comp;



    public static ArrayList<Double> est = new ArrayList<Double>();



    public static ArrayList<String> getLlistaArxius(String path) {

        int index = 0;
        ArrayList<String> noms = new ArrayList<String>();

        nocomp = disk.ReadElementCarpeta(path,index);
        while(nocomp != null) {

            if(nocomp instanceof Carpeta) return null;
            else {
                noms.add(nocomp.getNom());

                ++index;
                nocomp = disk.ReadElementCarpeta(path, index);
            }
        }
        return noms;
    }

    public static String ComprimeixFitxerAut(String path, String dest, String nom) {

        long inicic = System.currentTimeMillis();
        nocomp = disk.ReadNoComprimit(path);
        comp = new Algorithm().decideixalgcomp(nocomp);
        comp.setNom(nom);

        long a = disk.WriteComprimit(comp,dest);

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double) nocomp.getTamany());
        est.add((double) a);
        est.add((double) a/ nocomp.getTamany());
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nom);

        return "OK";
    }

    public static String ComprimeixFitxerMan(String path, String alg, String dest, String nom, Integer qualitat) {

        long a = 0;
        long inicic = System.currentTimeMillis();
        nocomp = disk.ReadNoComprimit(path);
        System.out.println(alg);

        if (alg.equals("LZW")) {



            comp = new Algorithm().comprimeixLZW(nocomp);
            comp.setNom(nom);
             a = disk.WriteComprimit(comp,dest);
        }

        if (alg.equals("LZSS")) {

            comp = new Algorithm().comprimeixLZSS(nocomp);
            comp.setNom(nom);
            comp.setFormat("ISO-8859-1");
            a = disk.WriteComprimit(comp,dest);
        }

        if (alg.equals("LZ78")) {

            comp = new Algorithm().comprimeixLZ78(nocomp);
            comp.setNom(nom);
            comp.setFormat("ISO-8859-1");
             a = disk.WriteComprimit(comp,dest);
        }

        if (alg.equals("JPEG")) {


            comp = new Algorithm().comprimeixJPEG(nocomp, qualitat);
            comp.setNom(nom);

           a =  disk.WriteComprimit(comp,dest);
        }

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double) nocomp.getTamany());
        est.add((double) a);
        est.add((double) a/ nocomp.getTamany());
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nom);

        return "OK";
    }

    public static String ComprimeixCarpetaAut(String path, String dest, String nomCompresFolder) {

        long a = 0;
        long inicic = System.currentTimeMillis();
        double count = 0;
        String aux;
        Double countfake;
        int index = 0;
        String nom;
        CompressedFolder compfol = new CompressedFolder();
        nocomp = disk.ReadElementCarpeta(path,index);

        while(nocomp != null) {

            nom = nocomp.getNom();

            if(nocomp instanceof Carpeta) {
                aux = path + "\\" + nom;
                comp = ComprimeixCarpetaManualDinsCarpeta(aux,"AUT",compfol,dest,count,nom, 3);
                }
            else {
                count = count + nocomp.getTamany();
                    comp = new Algorithm().decideixalgcomp(nocomp);
                    comp.setNom(nom);

                    System.out.println(comp.getFormat());
                }

            compfol.AddElement(comp);

                ++index;
            nocomp = disk.ReadElementCarpeta(path,index);

        }


        compfol.setNom(nomCompresFolder);

        a = disk.WriteComprimit(compfol,dest);

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double) CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) a);
        est.add((double) a/  CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nomCompresFolder);

        return "OK";
    }

    public static String ComprimeixCarpetaManual(String path, String alg, String dest, String nomCompresFolder, Integer qualitat) {

        long a = 0;
        Double countfake;
        long inicic = System.currentTimeMillis();
        double count = 0;
        String nom;
        String aux;
        int index = 0;
        CompressedFolder compfol = new CompressedFolder();
        CompressedFolder comit = new CompressedFolder();

        if(alg.equals("LZW")) {

            nocomp = disk.ReadElementCarpeta(path,index);


            while (nocomp != null) {

                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {
                   aux = path + "\\" + nom;
                   comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,comit,dest,count,nom, qualitat);

                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixLZW(nocomp);
                    comp.setNom(nom);

                }

                compfol.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);



            }

            compfol.setNom(nomCompresFolder);
            System.out.println(compfol.Cont_Name);
            a = disk.WriteComprimit(compfol,dest);
        }

        if(alg.equals("LZSS")) {

            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {

                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {
                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,compfol,dest,count,nom, qualitat);
                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixLZSS(nocomp);
                    comp.setNom(nom);

                }
                compfol.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);
            }

            compfol.setNom(nomCompresFolder);
           a =  disk.WriteComprimit(compfol,dest);
        }

        if(alg.equals("LZ78")) {

            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {
                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {
                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,compfol,dest,count,nom, qualitat);

                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixLZ78(nocomp);
                    comp.setNom(nom);

                }
                compfol.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);
            }

            compfol.setNom(nomCompresFolder);
            a = disk.WriteComprimit(compfol,dest);
        }

        if(alg.equals("JPEG")) {

            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {

                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {
                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,compfol,dest,count,nom, qualitat);

                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixJPEG(nocomp, qualitat);
                    comp.setNom(nom);

                }
                compfol.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);
            }


            compfol.setNom(nomCompresFolder);
            a = disk.WriteComprimit(compfol,dest);
        }

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double)  CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) a);
        est.add((double) a/  CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nomCompresFolder);


        return "OK";

    }

    public  static String ComprimeixCarpetaSemiaut(String path, HashMap<String,String> FitxersIAlg, String dest, String nomCompresFolder, Integer qualitat ) { //hashmap

        long a = 0;
        long inicic = System.currentTimeMillis();
        double count = 0;
        int index = 0;
        String nom;
        nocomp = disk.ReadElementCarpeta(path,index);
        CompressedFolder compfol = new CompressedFolder();

        while(nocomp != null) {


            nom = nocomp.getNom();
            count = count + nocomp.getTamany();
            if (FitxersIAlg.get("AUT").contains(nom)) {

                    comp = new Algorithm().decideixalgcomp(nocomp);
                } else if (FitxersIAlg.get("LZW").contains(nom)) {

                    comp = new Algorithm().comprimeixLZW(nocomp);

                } else if (FitxersIAlg.get("LZSS").contains(nom)) {

                    comp = new Algorithm().comprimeixLZSS(nocomp);

                } else if (FitxersIAlg.get("LZ78").contains(nom)) {

                    comp = new Algorithm().comprimeixLZ78(nocomp);
                } else if (FitxersIAlg.get("JPEG").contains(nom)) {

                    comp = new Algorithm().comprimeixJPEG(nocomp, qualitat);
                }


                comp.setNom(nom);
                compfol.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path, index);


        }



        compfol.setNom(nomCompresFolder);
        a = disk.WriteComprimit(compfol,dest);

        long fic = System.currentTimeMillis();
        est.add((double)1);
        est.add((double)  CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) a);
        est.add((double) a/  CtrlPersistencia.getTamanyCarpeta(path));
        est.add((double) (fic- inicic) / 1000);
        CtrlEstadistiques e = new CtrlEstadistiques();
        e.setEstadistica(est,nomCompresFolder);
        return "OK";
    }

    public static CompressedFolder ComprimeixCarpetaManualDinsCarpeta(String path, String alg, CompressedFolder
            compfoli, String dest, Double count, String nom,  Integer qualitat) {

        Double countfake;
        String aux;
        String nomaux;
        int index = 0;

        CompressedFolder compfolgran = new CompressedFolder();
        CompressedFolder comit = new CompressedFolder();
        compfolgran.setNom(nom);

        if(alg.equals("LZW")) {


            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {


                nomaux = nocomp.getNom();
                if(nocomp instanceof Carpeta) {

                    aux = path + "\\" + nomaux;

                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,comit,dest,count,nomaux, qualitat);

                }
                else {
                    comp = new Algorithm().comprimeixLZW(nocomp);
                    comp.setNom(nomaux);
                }
                compfolgran.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);

            }

        }

        if(alg.equals("LZSS")) {


            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {


                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {

                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,comit,dest,count,nom, qualitat);

                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixLZSS(nocomp);
                    comp.setNom(nom);

                }
                compfolgran.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);

            }

        }

        if(alg.equals("LZ78")) {


            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {


                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {

                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,compfoli,dest,count,nom, qualitat);
                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixLZ78(nocomp);
                    comp.setNom(nom);

                }
                compfolgran.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);

            }

        }

        if(alg.equals("JPEG")) {


            nocomp = disk.ReadElementCarpeta(path,index);

            while (nocomp != null) {


                nom = nocomp.getNom();
                if(nocomp instanceof Carpeta) {

                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,alg,compfoli,dest,count,nom, qualitat);
                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().comprimeixJPEG(nocomp,qualitat);
                    comp.setNom(nom);

                }
                compfolgran.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);

            }


        }

        if(alg.equals("AUT")) {

            nocomp = disk.ReadElementCarpeta(path,index);

            while(nocomp != null) {

                nom = nocomp.getNom();

                if(nocomp instanceof Carpeta) {
                    aux = path + "\\" + nom;
                    comp = ComprimeixCarpetaManualDinsCarpeta(aux,"AUT",compfoli,dest,count,nom, 3);
                }
                else {
                    count = count + nocomp.getTamany();
                    comp = new Algorithm().decideixalgcomp(nocomp);
                    comp.setNom(nom);

                }


                compfolgran.AddElement(comp);
                ++index;
                nocomp = disk.ReadElementCarpeta(path,index);

            }

        }

        return compfolgran;
    }






//comit
}