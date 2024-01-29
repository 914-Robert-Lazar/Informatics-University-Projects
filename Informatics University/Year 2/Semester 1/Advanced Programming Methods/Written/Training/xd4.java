class A {
    static int f1 = 1;
    int s1;
    public A(int a) { 
        this.f1 = a * s1;
        s1= s1 + 1; 
    }
    static int getS() { return getS1(s1); }
    int getS1(int x) {return (x*getS());}
}

class A2 {
    protected int f1;
    static int s1=0;
    public A2(int a) { this.f1=a*s1;s1=s1+1; }
    static int getS() { return getS1(s1); }
    static int getS1(int x) {return (x*getS());}
    }

public class xd4 {
    public static void main(String[] args) {
        
    }
}
