import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.awt.*;


public  class  DiskIO {

    public static void main(String[] args) {

        OpenFile("TEST.txt");

    }

    //Returns array list of strings with names of the content files of a folder with path "Path"
    public static ArrayList<String> OpenFolder(String Path)  //TESTED
    {
        //return array list of strings with the names of the content of the directory with path "path"
        ArrayList<String> result = new ArrayList<String>();
        File fileName = new File(Path);
        File[] fileList = fileName.listFiles();
        for (File f:fileList)
        {
            result.add(f.getName());
        }
        return result;
    }

    public static ArrayList<String> OpenStats()  //TESTED
    {
        //return array list of strings with the names of the content of the directory with path "path"
        ArrayList<String> result = new ArrayList<String>();
        File fileName = new File("statistics");
        File[] fileList = fileName.listFiles();
        for (File f:fileList)
        {
            result.add(f.getName());
        }
        return result;
    }

    public static ArrayList<String> OpenFolderNoSubDir(String Path)  //TESTED
    {
        //return array list of strings with the names of the content of the directory with path "path"
        ArrayList<String> result = new ArrayList<String>();
        File fileName = new File(Path);
        File[] fileList = fileName.listFiles();
        for (File f:fileList)
        {
            if(!f.isDirectory())result.add(f.getName());
        }
        return result;
    }


    public static boolean MakeFolder(String Path, String Nom) //TESTED
    {
        boolean b = false;
        File file = new File(Path + "\\" + Nom);
        if(!file.exists()) {
            b = file.mkdir();
        }
        return b;
    }

    public static long WriteNoComprimit(No_comprimit n, String Path)  //TESTED
    {
        String BodyToWrite = null;
        try {
            BodyToWrite = new String(n.getBody().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String Path_to_write = Path + "\\" + n.getFullNom();
        if(n instanceof Carpeta)
        {
            File file = new File(Path_to_write);
            boolean bool = file.mkdir();

            for(No_comprimit A : n.getContent())
            {
                WriteNoComprimit(A, Path+"\\"+n.getNom());
            }
        }
        else
        {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Path_to_write))));
                try {
                    writer.write(BodyToWrite);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e /*| UnsupportedEncodingException e*/) {
                e.printStackTrace();
            }
        }
        File f = new File(Path_to_write);
        return f.length();
    }

    public static long WriteComprimit(Comprimit c,String Path)//TESTED
    {

        String BodyToWrite = c.getBody();
        System.out.println(c.getBody());
        String Path_to_write = Path + "\\"+ c.getFullNom();
        long size = 0;
        try {
            if (c instanceof CompressedFolder) {

                System.out.println("hola");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Path_to_write)), "UTF-16"));
                try {

                    writer.write(new String(BodyToWrite.getBytes(c.getFormat()),"UTF-16"));
                    writer.close();
                    File f = new File(Path_to_write);
                    size = f.length();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Path_to_write)), c.getFormat()));
                try {

                    writer.write(BodyToWrite);
                    writer.close();
                    File f = new File(Path_to_write);
                    size = f.length();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            } catch(FileNotFoundException | UnsupportedEncodingException e){
                e.printStackTrace();
            }
        return size;
    }

    public  static No_comprimit ReadElementCarpeta(String Path, int index) //TESTED
    {
        No_comprimit c = null;
        ArrayList<String> list = OpenFolder(Path);
        if(index < list.size())
        {
            c = ReadNoComprimit(Path + "\\" + list.get(index));
            /*String s = c.getNom();
            if(c instanceof Carpeta) {
                c.setNom(s);
            }
            else {
                c.setNom(s.substring(0, s.length() - 4));
            }
*/
        }

        return c;
    }

    public static Comprimit ReadComprimit(String Path) //TESTED
    {
        //test
        Comprimit res = null;
        File te = new File(Path);
        String extencio = Path.substring(Path.length()-3);
        switch (extencio)
        {
            case "LZW":
                res = new LZWfile();
                res.setFormat("UNICODE");
                break;
            case "LZS":
                res = new LZSSfile();
                res.setFormat("ISO-8859-1");
                break;
            case "LZ7":
                res = new LZ78file();
                res.setFormat("ISO-8859-1");
                break;
            case "jpg":
                res = new JPGfile();
                break;
            default:
                System.out.println("Unrecognised format in the process of reading file");
                break;
        }
        if(te.exists())
        {
            String[] spl = Path.split(Pattern.quote(File.separator));
            String filename = spl[spl.length - 1];
            res.setNom(filename.substring(0, filename.lastIndexOf('.')));
            String body = "";
            String r;
            long size = 0;
            try {
                File F = new File(Path);
                size = F.length();
                System.out.println(res.getFormat());
                Scanner scan = new Scanner(new FileInputStream(F),res.getFormat());
                scan.useDelimiter("\\A");
                res.setBody(scan.next());
                res.setTamany(size);
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        }
        return res;
    }

    public static No_comprimit ReadNoComprimit(String Path) //TESTED
    {
        File f = new File(Path);
        long size = 0;
        String[] spl = Path.split(Pattern.quote(File.separator));
        String file_name = spl[spl.length -1];
        if(f.isDirectory())
        {
            //Process of reading a directory
            Carpeta result = new Carpeta(file_name);
            result.setNom(file_name);
            /*
            ArrayList<String> content = OpenFolder(Path);
            for (String element : content)
            {
                String elem_path = Path + Pattern.quote(File.separator)+element;
                No_comprimit elem = ReadNoComprimit(elem_path);
                result.Add_element(elem);
            }

             */
            return result;
        }
        String extencio = Path.substring(Path.length()-3);
        No_comprimit res = null;
        switch (extencio)
        {
            case "txt":
                //read and return a txt
                res = new TXT(file_name);
                res.setFormat("ISO-8859-1");
                try {
                    File F = new File(Path);
                    //res.setFormat();
                    Scanner scan = new Scanner(new FileInputStream(F), res.getFormat());
                    if(F.length() != 0) {
                        scan.useDelimiter("\\A");
                        res.setBody(scan.next());
                    }
                    res.setTamany(F.length());
                }
                catch (IOException ex) {System.err.println(ex);}
                break;
            case "ppm":
                res = new PPM(file_name);

                try {
                    File F = new File(Path);
                    res.setBody(Files.readAllBytes(Paths.get(Path)));
                    res.setTamany(F.length());
                }
                catch (IOException ex) {System.err.println(ex);}
                break;


            default:
                System.out.println("Unrecognised format in the process of reading file");
                break;
        }

        return res;
    }

    public static CompressedFolder ReadCompressedFolder(String Path) //TESTED
    {
        CompressedFolder cmp = new CompressedFolder();
        long size = 0;
        try {
            File F = new File(Path);
            System.out.println(F.exists());
            size = F.length();
            Scanner scan = new Scanner(new FileInputStream(F),"UTF-16");
            scan.useDelimiter("\\A");
            cmp.setBody(scan.next());
            //cmp.setFormat(charset);
            cmp.setTamany(size);
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
        return cmp;
    }

    public static void DeleteFile(String Path)
    {
        File F = new File(Path);
        if(F.exists())
        {
            F.delete();
        }
    }

    public static void writeEstadistiques(Estadistiques est)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("statistics\\" + est.getNom() + ".txt"))));
            try {
                writer.write(est.getBody());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Estadistiques readEstadistiques(String nom)
    {
        Estadistiques est = new Estadistiques();
        try {
            File F = new File("statistics\\" + nom + ".txt");
            //res.setFormat();
            Scanner scan = new Scanner(new FileInputStream(F));
            if(F.length() != 0) {
                scan.useDelimiter("\\A");
                est.setBody(scan.next());
            }
        }
        catch (IOException ex) {System.err.println(ex);}
        return est;

    }

    public static void OpenFile(String path)
    {
        File file = new File(path);
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getTamanyCarpeta(String path)
    {
        long l = 0;
        ArrayList<String> list = OpenFolder(path);
        for(String s : list)
        {
            File f = new File(path+"\\"+s);
            if(f.isDirectory())
            {
                l += getTamanyCarpeta(path+"\\"+s);
            }
            else
                {
                    l += f.length();
                }

        }
        return l;
    }

}
