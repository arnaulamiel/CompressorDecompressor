import java.util.HashMap;
import java.util.Scanner;

public class HTreeDriver {
    public static void main(String[] args){
        Scanner teclado = new Scanner(System.in);
        String s = "load";
        AppList L;
        HTree T = new HTree();
        while (!s.equals("exit")) {
            switch (s) {
                case "load":
                    System.out.println("Indica el nombre d'elements");
                    int sz = teclado.nextInt();
                    System.out.println("Insereix cada element acompanyat pel seu nombre d'aparicions en ordre d'aparicions descendent");
                    System.out.println("Exemple dos deus i tres cincs s'escriu: 5 3 10 2");
                    int[] Values = new int[sz];
                    int[] Apparitions = new int[sz];
                    for(int i = 0; i < sz; ++i){
                        Values[i] = teclado.nextInt();
                        Apparitions[i] = teclado.nextInt();
                    }
                    s = teclado.nextLine();
                    L = new AppList(Values,Apparitions);
                    T = new HTree(L);
                    break;
                case "getcode":
                    HashMap<Integer, boolean []> code = T.getcode();
                    for (HashMap.Entry<Integer, boolean []> entry : code.entrySet()) {
                        System.out.print(entry.getKey()); System.out.print(" ");
                        for (boolean B : entry.getValue()) {
                            if(B) System.out.print("1");
                            else System.out.print("0");
                        }
                        System.out.println();
                    }
                    break;
                case "move":
                    if(T.move(teclado.nextInt() == 1)) {
                        System.out.print("Has arribat al numero ");
                        System.out.println(T.get());
                    }
                    else System.out.println("Encara no has arribat a cap fulla");
                    s = teclado.nextLine();
                    break;
            }
            System.out.println("Que vols fer? load,getcode,move");
            s = teclado.nextLine();
        }
    }
}
