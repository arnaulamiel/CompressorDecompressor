public class AppList{
    private int [] val;
    private int [] app;
    private int size;
    private int maxsize;

    AppList(){
        maxsize = 64;
        size = 0;
        val = new int [maxsize];
        app = new int [maxsize];
    }
    AppList(int [] v, int [] a){
        if(v.length == a.length) {
            size = v.length;
            if(size == 0) maxsize = 1;
            else maxsize = size;
            val = new int [maxsize];
            app = new int [maxsize];
            for (int i = 0; i < size; ++i){
                val[i] = v[i];
                app[i] = a[i];
            }
        }
    }

    public int getSize() {return size;}
    public int getV(int i) {
        if(i >= size) return -999999;
        return val[i];
    }
    public int getA(int i) {
        if(i >= size) return -999999;
        return app[i];
    }

    public void seen(int I){
        int i = 0;
        while ((i < size) && (val[i] != I)){++i;}
        if (i == size){
            if (size == maxsize){
                int [] auxv = new int [maxsize*2];
                int [] auxa = new int [maxsize*2];
                for (int j = 0; j < maxsize; ++j) {
                    auxv[j] = val[j];
                    auxa[j] = app[j];
                }
                auxv[maxsize] = I;
                auxa[maxsize] = 1;

                size = maxsize+1;
                maxsize = maxsize*2;
                val = auxv;
                app = auxa;
            }
            else {
                val[size] = I;
                app[i] = 1;
                ++size;
            }
        }
        else {
            app[i] = app[i] + 1;
            while ( i > 0 && app[i] > app[i-1]){
                int aux = app[i-1];
                app[i-1] = app[i];
                app[i] = aux;
                aux = val[i-1];
                val[i-1] = val[i];
                val[i] = aux;
                --i;
            }
        }
    }

}