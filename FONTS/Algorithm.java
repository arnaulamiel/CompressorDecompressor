import java.util.ArrayList;

public class Algorithm {

    public Comprimit decideixalgcomp(No_comprimit nocomp) {

        Comprimit resultat = null;
        long inicic = 0;
        long fic = 0;
        long tamanycomp = 0;

        if (nocomp instanceof TXT) {

            String s = nocomp.getBody();

            if (s.length() > 4096) {
                Algorithmstrategy strategy = null;
                strategy = new LZSS();
                resultat = new LZSSfile();
                inicic = System.currentTimeMillis();
                resultat = strategy.comprimir(nocomp);
                fic = System.currentTimeMillis();

            } else if (s.length() <= 4096) {


                Algorithmstrategy strategy = null;
                strategy = new LZW();
                resultat = new LZWfile();
                inicic = System.currentTimeMillis();
                resultat = strategy.comprimir(nocomp);
                fic = System.currentTimeMillis();
                tamanycomp = resultat.getTamany();


            }

        } else if (nocomp instanceof PPM) {

            Algorithmstrategy strategy = null;
            strategy = new JPGalgorithm();
            resultat = new JPGfile();
            inicic = System.currentTimeMillis();
            resultat = strategy.comprimir(nocomp);
            fic = System.currentTimeMillis();


        }

        //EstadistiquesC e = new EstadistiquesC(inicic, fic, nocomp.getBody().length(), tamanycomp);
        //e.afegeix_histo(e);

        return resultat;
    }

    public Comprimit comprimeixLZW(No_comprimit nocomp) {

        long inicic = 0;
        long fic = 0;
        long tamanycomp = 0;


        Algorithmstrategy strategy = null;
        strategy = new LZW();
        Comprimit resultat = new LZWfile();
        inicic = System.currentTimeMillis();
        resultat = strategy.comprimir(nocomp);
        fic = System.currentTimeMillis();
        tamanycomp = resultat.getTamany();


        ArrayList<Double> est = new ArrayList<Double>();

        return resultat;

    }

    public Comprimit comprimeixLZSS(No_comprimit nocomp) {

        long inicic = 0;
        long fic = 0;
        long tamanycomp = 0;

        Algorithmstrategy strategy = null;
        strategy = new LZSS();
        Comprimit resultat = new LZSSfile();
        inicic = System.currentTimeMillis();
        resultat = strategy.comprimir(nocomp);
        fic = System.currentTimeMillis();
        tamanycomp = resultat.getTamany();

        //EstadistiquesC e = new EstadistiquesC(inicic, fic, nocomp.getBody().length(), tamanycomp);
        //e.afegeix_histo(e);
        return resultat;

    }

    public Comprimit comprimeixLZ78(No_comprimit nocomp) {

        long inicic = 0;
        long fic = 0;
        long tamanycomp = 0;

        Algorithmstrategy strategy = null;
        strategy = new LZ78();
        Comprimit resultat = new LZ78file();
        inicic = System.currentTimeMillis();
        resultat = strategy.comprimir(nocomp);
        fic = System.currentTimeMillis();
        tamanycomp = resultat.getTamany();
        //EstadistiquesC e = new EstadistiquesC(inicic, fic, nocomp.getBody().length(), tamanycomp);
        //e.afegeix_histo(e);
        return resultat;

    }

    public Comprimit comprimeixJPEG(No_comprimit nocomp, int qualitat) {

        long inicic = 0;
        long fic = 0;
        long tamanycomp = 0;

        Algorithmstrategy strategy = null;
        strategy = new JPGalgorithm();
        Comprimit resultat = new JPGfile();
        inicic = System.currentTimeMillis();
        resultat = strategy.comprimir(nocomp);
        fic = System.currentTimeMillis();
        tamanycomp = resultat.getTamany();
        //EstadistiquesC e = new EstadistiquesC(inicic, fic, nocomp.getBody().length(), tamanycomp);
        //e.afegeix_histo(e);
        return resultat;

    }

    public No_comprimit decideixalgdes(Comprimit c) {

        No_comprimit nocomp = null;
        long inicic = 0;
        long fic = 0;


        if (c instanceof LZWfile) {

            Algorithmstrategy strategy = null;
            strategy = new LZW();
            nocomp = new TXT();
            inicic = System.currentTimeMillis();
            nocomp = strategy.descomprimir(c);
            nocomp.setFormat("UNICODE");
            fic = System.currentTimeMillis();


        } else if (c instanceof LZ78file) {

            Algorithmstrategy strategy = null;
            strategy = new LZ78();
            nocomp = new TXT();
            inicic = System.currentTimeMillis();
            nocomp = strategy.descomprimir(c);
            nocomp.setFormat("ISO-8859-1"); //poseu porfa el vostre llenguatge
            fic = System.currentTimeMillis();

        } else if (c instanceof LZSSfile) {

            Algorithmstrategy strategy = null;
            strategy = new LZSS();
            nocomp = new TXT();
            inicic = System.currentTimeMillis();
            nocomp = strategy.descomprimir(c);
            nocomp.setFormat("ISO-8859-1"); //poseu porfa el vostre llenguatge
            fic = System.currentTimeMillis();

        } else if (c instanceof JPGfile) {

            Algorithmstrategy strategy = null;
            strategy = new JPGalgorithm();
            nocomp = new PPM();
            inicic = System.currentTimeMillis();
            nocomp = strategy.descomprimir(c);
            fic = System.currentTimeMillis();

        }

        //EstadistiquesD e = new EstadistiquesD(inicic, fic, c.getTamany(), nocomp.getBody().length());
        //e.afegeix_histo(e);

        return nocomp;
    }
//comi
}

