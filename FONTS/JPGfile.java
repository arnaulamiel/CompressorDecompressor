import com.sun.jdi.Value;

import java.io.CharArrayReader;
import java.util.Scanner;

public class JPGfile extends Comprimit {
    private char cinfo;
    private int cinfoCounter;
    private int[] Values;
    private int[] Apparitions;
    private int fil;
    private int col;
    private int Quality;
    private StringBuilder bodyBuilder;

    JPGfile(){setTamany(0);setFormat("UTF-8");cinfo = 0;cinfoCounter=128;bodyBuilder = new StringBuilder();}
    JPGfile(String n){setNom(n);setTamany(0);setFormat("UTF-8");cinfo = 0;cinfoCounter=128;bodyBuilder = new StringBuilder();}
    @Override
    public void setBody(String s){
        Scanner scan = new Scanner(s);
        String auxs = scan.nextLine();
        scan.useDelimiter("\\A");
        bodyBuilder = new StringBuilder(scan.next());
        scan = new Scanner(auxs);
        this.setQuality(scan.nextInt());
        fil = scan.nextInt();
        col = scan.nextInt();
        Values = new int[scan.nextInt()];
        Apparitions = new int[Values.length];
        for(int i = 0; i < Values.length; ++i){
            Values[i] = scan.nextInt();
            Apparitions[i] = scan.nextInt();
        }
    }
    @Override
    public String getBody(){
        StringBuilder body = new StringBuilder(bodyBuilder.length()*2);
        body.append(String.valueOf(this.getQuality()));body.append(" ");
        body.append(String.valueOf(fil));body.append(" ");
        body.append(String.valueOf(col));body.append(" ");
        body.append(String.valueOf(Apparitions.length));
        for(int i = 0, size = Apparitions.length; i < size; ++i){
            body.append(" ");body.append(String.valueOf(Values[i]));
            body.append(" ");body.append(String.valueOf(Apparitions[i]));
        }
        body.append("\n");
        body.append(bodyBuilder.toString());
        body.append(cinfo);
        return body.toString();
    }

    public int getQuality(){return Quality;};
    public void setQuality(int i){
        Quality = i;
        if(Quality > 10) Quality = 10;
        if(Quality < 1) Quality = 1;
    }
    public int getValue(int i){
        if(Values.length > i) return Values[i];
        return -999999;
    }
    public int getApparition(int i){
        if(Apparitions.length > i) return Apparitions[i];
        return -999999;
    }
    public int getHeaderSize(){return Apparitions.length;}
    public String getInfo(){
        if(cinfoCounter == 128) return  bodyBuilder.toString();
        else return bodyBuilder.toString() + cinfo;
    }
    public int getFil(){return fil;}
    public void setFil(int i){fil = i;}
    public int getCol(){return col;}
    public void setCol(int i){col = i;}

    public void write(boolean b) {
        if(b) cinfo += cinfoCounter;
        cinfoCounter /= 2;
        if (cinfoCounter == 0) {
            cinfoCounter = 128;
            bodyBuilder.append(cinfo);
            cinfo = 0;
        }
    }

    public void valuesAndApparitions(int [] V, int [] A){
        if(V.length == A.length) {
            int size = V.length;
            Values = new int [size];
            Apparitions = new int [size];
            for (int i = 0; i < size; ++i){
                Values[i] = V[i];
                Apparitions[i] = A[i];
            }
        }
    }

    @Override
    public String getFullNom() {
        return this.getNom() + ".jpg";
    }
}
