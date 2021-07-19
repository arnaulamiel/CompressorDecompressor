import java.util.Scanner;

public class AppListDriver {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String s = "load";
        AppList L = new AppList();
        while (!s.equals("exit")) {
            switch (s) {
                case "load":
                    System.out.println("Indica el nombre d'elements");
                    int sz = teclado.nextInt();
                    System.out.println("Insereix cada element acompanyat pel seu nombre d'aparicions");
                    int[] Values = new int[sz];
                    int[] Apparitions = new int[sz];
                    for(int i = 0; i < sz; ++i){
                        Values[i] = teclado.nextInt();
                        Apparitions[i] = teclado.nextInt();
                    }
                    s = teclado.nextLine();
                    L = new AppList(Values,Apparitions);
                    break;
                case "getSize":
                    System.out.println(L.getSize());
                    break;
                case "getValue":
                    System.out.println(L.getV(teclado.nextInt()));
                    s = teclado.nextLine();
                    break;
                case "getApparition":
                    System.out.println(L.getA(teclado.nextInt()));
                    s = teclado.nextLine();
                    break;
                case "seen":
                    L.seen(teclado.nextInt());
                    s = teclado.nextLine();
                    break;
                case "list":
                    System.out.print("V");System.out.print(" ");System.out.println("A");
                    for(int i = 0, size = L.getSize(); i < size; ++i){
                        System.out.print(L.getV(i));System.out.print(" ");System.out.println(L.getA(i));
                    }
            }
            System.out.println("Que vols fer? list,seen,getApparition,getValue,getSize,load");
            s = teclado.nextLine();
        }
    }
}