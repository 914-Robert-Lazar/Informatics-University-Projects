package typecheck;

public class SimpleCode {

    public static void main(String[] args){
        {
            int v;
            v=2;
            System.out.println("block1 v="+v);
        }

        //System.out.println(v);
        for (int i=0; i<3; i++)
        {
            int v;
            v=10;
            System.out.println("block2 v="+v);
            System.out.println("block2 i="+i+" block2 v="+v);
        }
        //System.out.println("after block2 i="+i+" block2 v="+v);

        //similar to this...
        for(int i = 0; i < 10; ++i){
            int a = 1;
        }
        int a = 0;
    }
}
