import java.util.HashMap;

public class HTree {
    private Node root;
    private Node current;
    HTree(){root = null; current = null;}
    HTree(AppList L){
        this.load(L);
    }

    public void load (AppList L){
        int size = L.getSize();
        Node[] nodes = new Node [size];
        for(int i = 0; i < size; ++i){
            nodes[i] = new Node(L.getV(i),L.getA(i));
        }
        while (size > 1){
            Node N1 = nodes[size-1];
            Node N2 = nodes[size-2];
            nodes[size-2] = new Node(N1.getWeight()+N2.getWeight(),N1,N2);
            for (int i = size-1; i > 0 && nodes[i].getWeight() > nodes[i-1].getWeight(); --i){
                N1 = nodes[i];
                nodes[i] = nodes[i-1];
                nodes[i-1] = N1;
            }
            --size;
        }
        if(nodes.length == 0) root = null;
        else root = nodes[0];
        current = root;
    }

    public int get(){
        int i = current.getData();
        current = root;
        return i;
    }
    public boolean move(boolean b){
        if (b) current = current.getRight();
        else current = current.getLeft();
        return current.getLeft() == null;
    }
    public HashMap<Integer, boolean []> getcode(){
        HashMap<Integer, boolean[]> map = new HashMap<> ();
        boolean [] b = new boolean[0];
        if(root != null) root.getCode(map,b);
        return map;
    }

    private class Node {
        private int data;
        private int weight;
        private Node right;
        private Node left;
        Node (int i, int w) {
            weight = w;
            data = i;
            right = null;
            left = null;
        }
        Node (int w, Node l, Node r) {
            weight = w;
            data = 0;
            right = r;
            left = l;
        }
        public int getData(){return data;}
        public int getWeight(){return weight;}
        public Node getRight(){return right;}
        public Node getLeft(){return left;}
        public void getCode(HashMap<Integer, boolean[]> map,boolean [] code){
            if(left == null) {
                map.put(data, code);
            }
            else {
                int size = code.length;
                boolean [] leftChildrenCode = new boolean[size+1];
                boolean [] rightChildrenCode = new boolean[size+1];
                for (int i = 0; i < size; ++i) {
                    leftChildrenCode[i] = code[i];
                    rightChildrenCode[i] = code[i];
                }
                leftChildrenCode[size] = false;
                rightChildrenCode[size] = true;
                left.getCode(map,leftChildrenCode);
                right.getCode(map,rightChildrenCode);
            }
        }
    }
}