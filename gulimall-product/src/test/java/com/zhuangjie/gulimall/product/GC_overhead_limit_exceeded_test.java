package com.zhuangjie.gulimall.product;

import java.util.ArrayList;

public class GC_overhead_limit_exceeded_test {
    public static void main(String[] args) {
        Person person = new Person("John Doe", 30);
        System.out.println(Integer.toHexString(System.identityHashCode(person)));
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

