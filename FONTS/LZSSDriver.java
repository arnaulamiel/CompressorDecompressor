import org.w3c.dom.ls.LSResourceResolver;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.Scanner;
import java.nio.file.*;

public class LZSSDriver {

   public static void main(String[] args)
   {
       double bytes1, bytes2;
       String s  = "";
       Scanner in = new Scanner(System.in);
       String name = "";
       long time;
       while (!(s.equals("exit")))
       {
           System.out.println("Vols comprimir o descomprimir?");
           s = in.nextLine();
           switch (s)
           {
               case "comprimir":
                   System.out.println("Indica el path i el nom del fitxer en linies separades");
                   s = in.nextLine();
                   name = in.nextLine();
                   No_comprimit n = Open_No_Comprimit(s,name);
                   bytes2 = n.getBody().getBytes().length;
                   LZSS comp = new LZSS();
                   time = System.currentTimeMillis();
                   LZSSfile c = comp.comprimir(n);
                   bytes1 = c.getBody().getBytes().length;
                   time = System.currentTimeMillis() - time;
                   writeComprimit(c,s);
                   System.out.println("Compressio acabada:");
                   System.out.print("TEMPS = ");System.out.print(time);System.out.println(" ms");
                   System.out.print("RATIO DE COMPRESSIO = ");System.out.println(bytes2/bytes1);

                   break;
               case "descomprimir":
                   System.out.println("Indica el path i el nom del fitxer en linies separades");
                   s = in.nextLine();
                   name = in.nextLine();;
                   LZSSfile d = Open_Comprimit(s,name);
                   LZSS des = new LZSS();
                   time = System.currentTimeMillis();
                   No_comprimit m = des.descomprimir(d);
                   bytes2 = d.getBody().getBytes().length;
                   bytes1 = m.getBody().getBytes().length;
                   time = System.currentTimeMillis() - time;
                   writeNo_comprimit(m,s);
                   System.out.println("descompressio acabada:");
                   System.out.print("TEMPS = ");System.out.print(time);System.out.println(" ms");
                   System.out.print("RATIO DE DESCOMPRESSIO = ");System.out.println(bytes2/bytes1);

                   break;
               case "test":
                   s = System.console().readLine();
                   name = System.console().readLine();
                   No_comprimit n1 = Open_No_Comprimit(s,name);
                   LZSS LS = new LZSS();
                   Comprimit c1;
                   c1 = LS.comprimir(n1);
                   n1 = LS.descomprimir(c1);
                   System.out.println(n1.getBody());
                   writeNo_comprimit(n1, s + ".txt");
                   break;
                default:
                    break;

           }
       }
   }

   private static No_comprimit Open_No_Comprimit(String Path, String name){
       No_comprimit l = new TXT();
       l.setNom(name);
       String res = "";
       String r = "";
       try
       {
           BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Path), "ISO-8859-1"));
           while ((r = reader.readLine()) != null)
           {
               res += r;
               res += '\n';
           }
           reader.close();
       }
       catch (IOException ex)
       {
           System.err.println(ex);

       }

       l.setBody(res);
       return l;
   }

   private static LZSSfile Open_Comprimit(String Path, String name) {
       LZSSfile l = new LZSSfile();
       l.setNom(name);
       String res = "";
       String r = "";
       try
       {
           BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Path), "ISO-8859-1"));
           while ((r = reader.readLine()) != null)
           {
               res += r;
               res += '\n';
           }
           reader.close();
       }
       catch (IOException ex)
       {
           System.err.println(ex);

       }
       l.setBody(res);
       return l;
   }

   private static void writeComprimit(LZSSfile c, String path)
   {
       try {

           String str = c.getBody();
           DataOutputStream outStream = new DataOutputStream(new FileOutputStream(path + ".LZS"));
           byte[] strToBytes = str.getBytes("ISO-8859-1");
           outStream.write(strToBytes);
           outStream.close();
       }
       catch (IOException ex)
       {
           System.err.println(ex);
       }
   }

   private static void writeNo_comprimit(No_comprimit n, String Path)
   {
       try {
           String str = n.getBody();
           BufferedWriter writer = new BufferedWriter(new FileWriter(Path + ".txt"));
           writer.write(str);
           writer.close();
       }
       catch (IOException ex)
       {
           System.err.println(ex);
       }
   }



}
