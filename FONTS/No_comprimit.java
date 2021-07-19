import java.util.ArrayList;

public abstract class No_comprimit {

        private String nom;
        private String format;
        private long tamany;

        public ArrayList<No_comprimit> getContent(){return null;}
        public long getTamany() {return tamany;}
        public void setTamany(long i) {tamany = i;}

        public abstract String getBody();
        public abstract void setBody(String s);
        public void setBody(byte[] b1){};

        public void setFormat(String s){format = s;}
        public String getFormat(){return format;}

        public abstract String getFullNom();
        public String getNom()
        {
                return nom;
        }
        public void setNom(String n){nom = n;}

}
