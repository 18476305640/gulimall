package io.renren;

public class Test {
    public static void main(String[] args) {
        int x = -1;
        assert x > 0;
        System.out.println(x);
    }
}

class Parent {
    void v1 () { // 子类重写后父类的方法会被覆盖
        System.out.println("父类普通方法");
    }
    static void v2 () { // 就算子类重写后也不会被覆盖
        System.out.println("父类静态方法");
    }
    private void v3 () { // 父类的私有方法不可以被重视
        System.out.println("父类的私有方法");
    }
    public static void main(String[] args) {
        Parent parent = new Son();
        parent.v1();
        parent.v2();
        parent.v3();
    }
}
class Son extends  Parent {
    void v1 () {
        System.out.println("子类普通方法");
    }
    static void v2 () {
        System.out.println("子类静态方法");
    }
    private void v3 () {
        System.out.println("子类的私有方法");
    }
}
