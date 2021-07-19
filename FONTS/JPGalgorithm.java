import java.util.*;
import java.util.concurrent.*;

public class JPGalgorithm implements Algorithmstrategy { //may want to make it private and extend algorithm
    private PPM ppm;
    private JPGfile jpg;
    private HTree huffmanT;
    private AppList apparitions;
    private Chunk[][] Chunks;
    private int Nfil;
    private int Ncol;
    JPGalgorithm(){}
    public JPGfile comprimir(No_comprimit a) {
        ppm = (PPM)a;
        apparitions = new AppList();
        huffmanT = new HTree();
        Nfil = ppm.getNfil();
        Ncol = ppm.getNcol();
        jpg = new JPGfile(ppm.getNom());
        jpg.setFil(Nfil);
        jpg.setCol(Ncol);
        jpg.setQuality(Chunk.getQuality());
        Chunks = new Chunk[(Nfil+7)/8][(Ncol+7)/8];
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Future<Integer>> futurs = new ArrayList<Future<Integer>>();
        for(int i = 0; i < Nfil; i += 8){
            int finalI = i;
            futurs.add(service.submit(() -> {
                    for (int j = 0; j < Ncol; j += 8) {
                        Chunks[finalI / 8][j / 8] = new Chunk(ppm, finalI, j);
                        Chunks[finalI / 8][j / 8].downsapmple();
                        Chunks[finalI / 8][j / 8].subtract128();
                        Chunks[finalI / 8][j / 8].DCT();
                        Chunks[finalI / 8][j / 8].Quant();
                    }
                return null;
            }));
            /*for (int j = 0; j < Ncol; j += 8) {
                Chunks[i / 8][j / 8] = new Chunk(ppm, i, j);
                Chunks[i / 8][j / 8].downsapmple();
                Chunks[i / 8][j / 8].subtract128();
                Chunks[i / 8][j / 8].DCT();
                Chunks[i / 8][j / 8].Quant();
                Chunks[i / 8][j / 8].countAppartions(apparitions);
            }*/
        }
        try {
            for (Future<Integer> f : futurs) {
                f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < Nfil; i += 8){ for (int j = 0; j < Ncol; j += 8) { Chunks[i / 8][j / 8].countAppartions(apparitions);} }
        huffmanT.load(apparitions);
        encodeAndWrite();
        writeHeader();
        return jpg;
    }

    public PPM descomprimir(Comprimit a) {
        jpg = (JPGfile) a; Nfil = jpg.getFil(); Ncol = jpg.getCol();
        Chunk.setQuality(jpg.getQuality());
        huffmanT = new HTree();
        //Dimensionar chunks
        Chunks = new Chunk[(Nfil+7)/8][(Ncol+7)/8];
        //Llegir header
        int [] A = new int[jpg.getHeaderSize()];
        int [] V = new int[jpg.getHeaderSize()];
        for(int i = 0, size = V.length ; i < size; ++i){
            A[i] = jpg.getApparition(i);
            V[i] = jpg.getValue(i);
        }
        apparitions = new AppList(V,A);
        huffmanT.load(apparitions);
        //Llegir fitxer i carregar chunks
        read(jpg.getInfo());
        //Operar chunks

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Future<Integer>> futurs = new ArrayList<Future<Integer>>();
        for(int i = 0; i < Nfil; i += 8){
            int finalI = i;
            futurs.add(service.submit(() -> {
                for (int j = 0; j < Ncol; j += 8) {
                    Chunks[finalI/8][j/8].InvQuant();
                    Chunks[finalI/8][j/8].InvDCT();
                    Chunks[finalI/8][j/8].add128();
                }
                return null;
            }));
        }
        try {
            for (Future<Integer> f : futurs) {
                f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        int[][] R = new int[Nfil][Ncol];
        int[][] G = new int[Nfil][Ncol];
        int[][] B = new int[Nfil][Ncol];
        for(int i = 0; i < Nfil; ++i){
            for(int j = 0; j < Ncol; ++j){
                R[i][j] = Chunks[i/8][j/8].getR(i%8,j%8);
                G[i][j] = Chunks[i/8][j/8].getG(i%8,j%8);
                B[i][j] = Chunks[i/8][j/8].getB(i%8,j%8);
            }
        }
        ppm = new PPM(jpg.getNom());
        ppm.setNfil(Nfil); ppm.setNcol(Ncol); ppm.setMaxv(255); ppm.setRED(R); ppm.setGREEN(G); ppm.setBLUE(B);
        return ppm;
    }
    private void encodeAndWrite (){
        HashMap<Integer,boolean []> map = new HashMap<> ();
        map = huffmanT.getcode();
        for (int i = 0; i < Nfil; i += 8){
            for(int j = 0; j < Ncol; j += 8){
                Chunks[i/8][j/8].encode(map,jpg,"Y");
                Chunks[i/8][j/8].encode(map,jpg,"Cb");
                Chunks[i/8][j/8].encode(map,jpg,"Cr");
            }
        }
    }
    private void writeHeader(){
        int size = apparitions.getSize();
        int[] A = new int[size];
        int[] V = new int[size];
        for(int i = 0; i < size; ++i){
            A[i] = apparitions.getA(i);
            V[i] = apparitions.getV(i);
        }
        jpg.valuesAndApparitions(V,A);
        //read(jpg.getInfo());
    }

    public static void setQuality(int i) {Chunk.setQuality(i);}
    private void read(String jpgBody){
        boolean b;
        int j = 128, i = 0, jpgIt = -1;
        char c = jpgBody.charAt(0);
        for (int fil = 0; fil < Nfil; fil += 8){
            for (int col = 0; col < Ncol; col += 8) {
                Chunks[fil/8][col/8] = new Chunk(Nfil-fil,Ncol-col);
                for(int CoefIt = 0; CoefIt < 3; ++CoefIt) {
                    String m;
                    switch (CoefIt) {
                        case 0:
                            m = "Y";
                            break;
                        case 1:
                            m = "Cb";
                            break;
                        default:
                            m = "Cr";
                            break;
                    }
                    int[] Coef = new int[64];
                    while (i < 64) {
                        if(j == 128) c = jpgBody.charAt(++jpgIt);
                        b = c >= j;
                        if (b) c -= j;
                        if (huffmanT.move(b)) {
                            Coef[i] = huffmanT.get();
                            ++i;
                        }
                        if (j == 1) j = 128;
                        else j = j / 2;
                    }
                    Chunks[fil/8][col/8].Assign(Coef, m);
                    i = 0;
                }
            }
        }
    }
}
