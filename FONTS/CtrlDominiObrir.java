
public class CtrlDominiObrir {

    public static String Obrir(String path) {
        String extencio = path.substring(path.length()-3);
        switch (extencio)
        {
            case "LZ7":
            case "LZS":
            case "LZW":
                CtrlDominiDescomprimir.DescomprimirFitxer(path,"T","Tempporal");
                CtrlPersistencia.OpenFile("T\\Tempporal.TXT");
                break;
            case "jpg":
                CtrlDominiDescomprimir.DescomprimirFitxer(path,"T","Tempporal");
                CtrlPersistencia.OpenFile("T\\Tempporal.ppm");
                break;
            default:
                CtrlDominiDescomprimir.DescomprimirCarpeta(path,"T","Tempporal");
                CtrlPersistencia.OpenFile("T\\Tempporal");
                break;
        }
        return "OK";
    }
}
