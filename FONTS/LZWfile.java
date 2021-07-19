import java.util.*;

public class LZWfile extends Comprimit{

   public  ArrayList<Integer> res = new ArrayList<Integer>();

   @Override
   public String getBody(){
      StringBuilder s = new StringBuilder(res.size());
      for (int i = 0; i < res.size(); i++) s.append((char)(int)res.get(i));
      return s.toString();
   }

   @Override
   public void setBody(String S) {

      ArrayList<Integer> aux = new ArrayList<Integer>();
      int i = 0;
      int w;

      while(i < S.length() ) {
         aux.add((int) S.charAt(i));
         ++i;
      }

      res = aux;

   }



   @Override
   public String getFullNom() {
      return this.getNom() + ".LZW";
   }
//comit
}
