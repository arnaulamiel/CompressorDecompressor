import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class PPM extends No_comprimit{
    private int[][]RED;
    private int[][]GREEN;
    private int[][]BLUE;
    private int ncol;
    private int nfil;
    private int maxv;

    PPM(){setTamany(0);setFormat("Cp1252");maxv = 255;}
    PPM(String N){setNom(N);setTamany(0);setFormat("Cp1252");maxv = 255;}

    public int getNcol(){return ncol;}
    public void setNcol(int i) {ncol = i;}
    public int getNfil(){return nfil;}
    public void setNfil(int i) {nfil = i;}
    public int getMaxv(){return maxv;}
    public void setMaxv(int i) {maxv = i;}
    public int getRED(int fil, int col){return RED[fil][col];}
    public void setRED(int[][] r) {RED = r;}
    public int getGREEN(int fil, int col){return GREEN[fil][col];}
    public void setBLUE(int[][] b) {BLUE = b;}
    public int getBLUE(int fil, int col){return BLUE[fil][col];}
    public void setGREEN(int[][] g) {GREEN = g;}

    @Override
    public void setBody(String s) { };

    @Override
    public void setBody(byte[] b1) {
        Scanner scan = new Scanner(" ");
        try {
            scan = new Scanner(new String(b1, "Cp1252"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String type = scan.next();
        while(! scan.hasNextInt()) scan.nextLine(); ncol = scan.nextInt();
        while(! scan.hasNextInt()) scan.nextLine(); nfil = scan.nextInt();
        while(! scan.hasNextInt()) scan.nextLine(); maxv = scan.nextInt();
        RED = new int[nfil][ncol];
        GREEN = new int[nfil][ncol];
        BLUE = new int[nfil][ncol];
        if(type.equals("P6")) {
            scan.useDelimiter("\\A");
            int inici = b1.length - scan.next().length();
            for (int fil = 0; fil < nfil; fil++) {
                for (int col = 0; col < ncol; col++) {
                    RED[fil][col] = b1[inici + 3 * (fil * ncol + col) + 1];if (RED[fil][col] < 0) RED[fil][col] += 256;
                    GREEN[fil][col] = b1[inici + 3 * (fil * ncol + col) + 2];if (GREEN[fil][col] < 0) GREEN[fil][col] += 256;
                    BLUE[fil][col] = b1[inici + 3 * (fil * ncol + col) + 3];if (BLUE[fil][col] < 0) BLUE[fil][col] += 256;
                }
            }
        }
        else{
            for (int fil = 0; fil < nfil; fil++) {
                for (int col = 0; col < ncol; col++) {
                    RED[fil][col] = scan.nextInt();
                    GREEN[fil][col] = scan.nextInt();
                    BLUE[fil][col] = scan.nextInt();
                }
            }
        }
    }
    @Override
    public String getBody() {
        StringBuilder s = new StringBuilder(nfil*ncol + 50);
        s.append("P3\n");
        s.append(String.valueOf(ncol)); s.append(" ");
        s.append(String.valueOf(nfil)); s.append("\n");
        s.append(String.valueOf(maxv)); s.append("\n");
        for(int i = 0; i < nfil; ++i){
            for(int j = 0; j < ncol; ++j){
                s.append(String.valueOf(RED[i][j]));s.append(" ");
                s.append(String.valueOf(GREEN[i][j]));s.append(" ");
                s.append(String.valueOf(BLUE[i][j]));s.append(" ");
            }
        }
        return s.toString();
    }
    @Override
    public String getFullNom() {
        return this.getNom() + ".ppm";
    }
}