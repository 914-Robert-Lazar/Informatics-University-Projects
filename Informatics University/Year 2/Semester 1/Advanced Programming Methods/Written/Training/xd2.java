import java.util.ArrayList;

interface D {}


class A implements D{}
class B extends A implements D{}
class C extends A implements D{}

public class xd2 {

    D method1(ArrayList<?> listC) {
        if (listC.isEmpty()) return null; else return listC.get(1);
    }

    void method2(ArrayList<?> list, C elem) { list.add(elem);}
    void method3(C elem){
        ArrayList<A> listA=new ArrayList<A>(); listA.add(new B());listA.add(new C());
        ArrayList<B> listB = new ArrayList<B>(); listB.add(new B());listB.add(new B());
        ArrayList<C> listC = new ArrayList<C>(); listC.add(new C()); listC.add(new C());
        this.method1(listA); this.method1(listB); this.method1(listC);
        this.method2(listA,elem); this.method2(listC,elem);
    }
    public static void main(String[] args) {

    }

}
