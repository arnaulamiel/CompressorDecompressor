import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public  class   CtrlPersistencia {

    public static void main(String[] args) {

        OpenFile("TEST.txt");

    }

    //Returns array list of strings with names of the content files of a folder with path "Path"
    public static ArrayList<String> OpenFolderNoSubDir(String Path)
    {
        return DiskIO.OpenFolderNoSubDir(Path);
    }
    public static ArrayList<String> OpenStats()
    {
        return DiskIO.OpenStats();
    }

    public static ArrayList<String> OpenFolder(String Path)  //TESTED
    {
        return DiskIO.OpenFolder(Path);
    }

    public static boolean MakeFolder(String Path, String Nom) //TESTED
    {
        return DiskIO.MakeFolder(Path,Nom);
    }

    public static long WriteNoComprimit(No_comprimit n, String Path) throws UnsupportedEncodingException //TESTED
    {
        return DiskIO.WriteNoComprimit(n,Path);
    }

    public static long WriteComprimit(Comprimit c,String Path)//TESTED
    {
        return DiskIO.WriteComprimit(c,Path);
    }

    public  static No_comprimit ReadElementCarpeta(String Path, int index) //TESTED
    {
        return DiskIO.ReadElementCarpeta(Path,index);
    }
    public static Comprimit ReadComprimit(String Path) //TESTED
    {
        return DiskIO.ReadComprimit(Path);
    }

    public static No_comprimit ReadNoComprimit(String Path) //TESTED
    {
        return DiskIO.ReadNoComprimit(Path);
    }

    public static CompressedFolder ReadCompressedFolder(String Path) //TESTED
    {
        return DiskIO.ReadCompressedFolder(Path);
    }

    public static void DeleteFile(String Path)
    {
        DiskIO.DeleteFile(Path);
    }

    public static void writeEstadistiques(Estadistiques est)
    {
        DiskIO.writeEstadistiques(est);
    }

    public static Estadistiques readEstadistiques(String nom)
    {
        return DiskIO.readEstadistiques(nom);
    }

    public static void OpenFile(String path)
    {
        DiskIO.OpenFile(path);
    }
    public static long getTamanyCarpeta(String Path)
    {
        return DiskIO.getTamanyCarpeta(Path);
    }


}
