import java.util.HashMap;

public class Chunk {
    private static int Q =1;
    private int fil;
    private int col;
    private double[][] Y;
    private double[][] Cb;
    private double[][] Cr;
    private static int [][] Luminance = {
            {16,11,10,16,24,40,51,61},
            {12,12,14,19,26,58,60,55},
            {14,13,16,24,40,57,69,56},
            {14,17,22,29,51,87,80,62},
            {18,22,37,56,68,109,103,77},
            {24,35,55,64,81,104,113,92},
            {49,64,78,87,103,121,120,101},
            {72,92,95,98,112,100,103,99}
    };
    private static int [][] Chrominance = {
            {17,18,24,47,99,99,99,99},
            {18,21,26,66,99,99,99,99},
            {24,26,56,99,99,99,99,99},
            {47,66,99,99,99,99,99,99},
            {99,99,99,99,99,99,99,99},
            {99,99,99,99,99,99,99,99},
            {99,99,99,99,99,99,99,99},
            {99,99,99,99,99,99,99,99}
    };

    Chunk (int f, int c){
        if(f > 7) fil = 8;
        else fil = f;
        if(c > 7) col = 8;
        else col = c;
        Y  = new double[8][8];
        Cb = new double[8][8];
        Cr = new double[8][8];
    };
    Chunk (PPM ppm, int f, int c) {
        fil = ppm.getNfil() - f;
        col = ppm.getNcol() - c;
        if (fil > 8) fil = 8;
        if (col > 8) col = 8;
        Y = new double[8][8];
        Cb = new double[8][8];
        Cr = new double[8][8];
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                if(i < fil && j < col) {
                    int r = ppm.getRED(f+i, c+j);
                    int g = ppm.getGREEN(f+i,c+j);
                    int b = ppm.getBLUE(f+i,c+j);
                    Y[i][j] = 0.299*r + 0.587*g + 0.114*b;
                    Cb[i][j] = 128.0 - 0.168736*r - 0.331264*g + 0.5*b;
                    Cr[i][j] = 128.0 + 0.5*r - 0.418688*g - 0.081312*b;
                }
                else {
                    Y[i][j] = 128;
                    Cb[i][j] = 128;
                    Cr[i][j] = 128;
                }
            }
        }
    }

    public int getFil() {return fil;}
    public int getCol() {return col;}

    public static void setQuality(int i){
        Q = i;
        if(Q > 10) Q = 10;
        if(Q < 1) Q = 1;
    }
    public static int getQuality(){return Q;}

    public int getR(int f, int c){ //may want to thow an error when it doesnt work
        if (f > 7 || c > 7) {
            System.err.println("tried to access a wrong position into the RChunk");
            return -999999;
        }
        //else return (int)((298.082*Y[f][c]+408.583*Cr[f][c])/256 - 222.921);
        else return (int) (Y[f][c] + 1.402*(Cr[f][c]-128.0));
    }
    public int getG(int f, int c){ //may want to thow an error when it doesnt work
        if (f > 7 || c > 7) {
            System.err.println("tried to access a wrong position into the GChunk");
            return -999999;
        }
        //else return (int)((298.082*Y[f][c]-100.291*Cb[f][c]-208.120*Cr[f][c])/256 + 135.576);
        else return (int) (Y[f][c] - 0.344136*(Cb[f][c]-128.0) - 0.714136*(Cr[f][c]-128.0));
    }
    public int getB(int f, int c){ //may want to thow an error when it doesnt work
        if (f > 7 || c > 7) {
            System.err.println("tried to access a wrong position into the BChunk");
            return -999999;
        }
        //else return (int)((298.082*Y[f][c]+516.412*Cb[f][c])/256 - 276.836);
        else return (int) (Y[f][c] + 1.772*(Cb[f][c]-128.0));
    }

    public void downsapmple(){
        if(Q < 7) {
            for (int i = 0; i < 8; i+=2) {
                for (int j = 0; j < 8; j+=2) {
                    Cb[i][j] = (Cb[i][j]+Cb[i][j+1])/2; Cb[i][j+1] = Cb[i][j];
                    Cr[i][j] = (Cr[i][j]+Cr[i][j+1])/2; Cr[i][j+1] = Cr[i][j];
                    if(Q < 4){
                        Cb[i+1][j]= Cb[i][j]; Cb[i+1][j+1] = Cb[i][j];
                        Cr[i+1][j]= Cr[i][j]; Cr[i+1][j+1] = Cr[i][j];
                    }
                    else{
                        Cb[i+1][j] = (Cb[i+1][j]+Cb[i+1][j+1])/2; Cb[i+1][j+1] = Cb[i+1][j];
                        Cr[i+1][j] = (Cr[i+1][j]+Cr[i+1][j+1])/2; Cr[i+1][j+1] = Cr[i+1][j];
                    }
                }
            }
        }
    }
    public void subtract128(){
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                Y[i][j] = Y[i][j]-128;
                Cb[i][j] = Cb[i][j]-128;
                Cr[i][j] = Cr[i][j]-128;
            }
        }
    }
    public void DCT (){
        DCTMat("Y");
        DCTMat("Cb");
        DCTMat("Cr");
    }
    public void Quant() {
        QuantMat("Y");
        QuantMat("Cb");
        QuantMat("Cr");
    }
    public void countAppartions(AppList L) {
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                L.seen((int)(Y[i][j]));
                L.seen((int)(Cb[i][j]));
                L.seen((int)(Cr[i][j]));
            }
        }
    }

    private void QuantMat(String s){
        if (!s.equals("Y") && !s.equals("Cb") && !s.equals("Cr")) return;
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if (s.equals("Y")) Y[i][j] = (Q*Y[i][j]/Luminance[i][j]);
                else if (s.equals("Cb")) Cb[i][j] = (Q*Cb[i][j]/Chrominance[i][j]);
                else Cr[i][j] = (Q*Cr[i][j]/Chrominance[i][j]);
            }
        }
    }
    private void DCTMat(String S) {
        double[][] in;
        switch (S) {
            case "Y":
                in = Y;
                break;
            case "Cb":
                in = Cb;
                break;
            case "Cr":
                in = Cr;
                break;
            default:
                return;
        }

        double[][] out = new double[8][8];
        int i, j, u, v;
        double s;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                s = 0;
                for (u = 0; u < 8; u++) {
                    for (v = 0; v < 8; v++) {
                        s += in[u][v] * Math.cos((2 * u + 1) * i * Math.PI / 16) * Math.cos((2 * v + 1) * j * Math.PI / 16) * ((i == 0) ? 1 / Math.sqrt(2) : 1) * ((j == 0) ? 1 / Math.sqrt(2) : 1);
                    }
                }
                out[i][j] = s / 4;
            }
        }

        switch (S) {
            case "Y":
                Y = out;
                break;
            case "Cb":
                Cb = out;
                break;
            case "Cr":
                Cr = out;
                break;
        }
    }

    public void encode(HashMap<Integer,boolean[]> map,JPGfile output,String s){
        if (!s.equals("Y") && !s.equals("Cb") && !s.equals("Cr")) return;

        int [] Coef = new int[64];
        boolean b = true; //Flase means going down and true means going up
        for (int i = 0, j = 0, itCoef = 0; itCoef < 64; ++itCoef) {
            switch (s) {
                case "Y":
                    Coef[itCoef] = (int)(Y[i][j]);
                    break;
                case "Cb":
                    Coef[itCoef] = (int)(Cb[i][j]);
                    break;
                default:
                    Coef[itCoef] = (int)(Cr[i][j]);
                    break;
            }
            if (b) {
                if(i == 0) {
                    b = false; ++j;
                }
                else if (j == 7) {
                    b = false; ++i;
                }
                else {
                    --i; ++j;
                }
            }
            else {
                if (i == 7){
                    b = true; ++j;
                }
                else if (j == 0) {
                    b = true; ++i;
                }
                else {
                    ++i; --j;
                }
            }
        }

        boolean [] code;
        for(int i = 0; i < 64; ++i){
            code = map.get(Coef[i]);
            for (boolean B : code) output.write(B);
        }
    }
    public void Assign(int[] Coef, String s) {
        if(Coef.length != 64 || !s.equals("Y") && !s.equals("Cb") && !s.equals("Cr")) return;
        boolean b = true; //Flase means going down and true means going up
        for (int i = 0, j = 0, itCoef = 0; itCoef < 64; ++itCoef){
            switch (s) {
                case "Y":
                    Y[i][j] = Coef[itCoef];
                    break;
                case "Cb":
                    Cb[i][j] = Coef[itCoef];
                    break;
                default:
                    Cr[i][j] = Coef[itCoef];
                    break;
            }

            if (b) {
                if(i == 0) {
                    b = false; ++j;
                }
                else if (j == 7) {
                    b = false; ++i;
                }
                else {
                    --i; ++j;
                }
            }
            else {
                if (i == 7){
                    b = true; ++j;
                }
                else if (j == 0) {
                    b = true; ++i;
                }
                else {
                    ++i; --j;
                }
            }
        }
    }

    public void InvDCT (){
        InvDCTMat("Y");
        InvDCTMat("Cb");
        InvDCTMat("Cr");
    }
    public void InvQuant(){
        InvQuantMat("Y");
        InvQuantMat("Cb");
        InvQuantMat("Cr");
    }
    public void add128() {
        for (int i = 0; i < 8; ++i){
            for (int j = 0; j < 8; ++j){
                Y[i][j] = Y[i][j]+128;
                Cb[i][j] = Cb[i][j]+128;
                Cr[i][j] = Cr[i][j]+128;
            }
        }
    }

    private void InvQuantMat(String s){
        if (!s.equals("Y") && !s.equals("Cb") && !s.equals("Cr")) return;
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if (s.equals("Y")) Y[i][j] = (Y[i][j]*Luminance[i][j]/Q);
                else if (s.equals("Cb")) Cb[i][j] = (Cb[i][j]*Chrominance[i][j]/Q);
                else Cr[i][j] = (Cr[i][j]*Chrominance[i][j]/Q);
            }
        }
    }
    private void InvDCTMat(String S) {
        double[][] in;
        switch (S) {
            case "Y":
                in = Y;
                break;
            case "Cb":
                in = Cb;
                break;
            case "Cr":
                in = Cr;
                break;
            default:
                return;
        }

        double[][] out = new double[8][8];
        int i, j, u, v;
        double s;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                s = 0;
                for (u = 0; u < 8; u++) {
                    for (v = 0; v < 8; v++) {
                        s += in[u][v] * Math.cos((2 * i + 1) * u * Math.PI / 16) * Math.cos((2 * j + 1) * v * Math.PI / 16) * ((u == 0) ? 1 / Math.sqrt(2) : 1.) * ((v == 0) ? 1 / Math.sqrt(2) : 1.);
                    }
                }
                out[i][j] = s / 4;
            }
        }

        switch (S) {
            case "Y":
                Y = out;
                break;
            case "Cb":
                Cb = out;
                break;
            case "Cr":
                Cr = out;
                break;
        }

    }
}
