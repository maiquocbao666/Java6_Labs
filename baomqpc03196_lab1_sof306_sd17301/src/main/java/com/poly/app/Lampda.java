package com.poly.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.poly.bean.Student;

// Bài 1
public class Lampda {

    static List<Student> list = Arrays.asList(
            new Student("Nguyên Văn Tèo", true, 7.5),
            new Student("Trần Thị Thu Hương", false, 5.5),
            new Student("Phạm Đức Cường", true, 9.5),
            new Student("Lê Thị Mỹ Hồng", false, 6.5),
            new Student("Đoàn Thị Kim Ty", false, 8.0));

    public static void main(String[] agrs) {
        // demo1();
        // demo2();
        // demo3();
        demo4();
    }

    private static void demo4() {
        Demo4Inter o = (int x) -> {
            System.out.println(x);
        };
        o.m1(2021);

        // Demo4Inter o = new Demo4Inter() {
        // @Override
        // public void m1(int x){
        // System.out.println(x);}
        // }
        // };o.m1(2021);};

    }

    private static void demo3() {

        // Sắp xếp tăng dần
        // Collections.sort(list, (sv1, sv2) -> sv1.getMarks().compareTo(sv2.getMarks())
        // );
        // list.forEach(sv -> {
        // System.out.println(">> Name: " + sv.getName());
        // System.out.println(">> Name: " + sv.getMarks());
        // System.out.println();
        // });

        // Sắp xếp giảm dần
        Collections.sort(list, (sv1, sv2) -> -sv1.getMarks().compareTo(sv2.getMarks()));
        list.forEach(sv -> {
            System.out.println(">> Name: " + sv.getName());
            System.out.println(">> Name: " + sv.getMarks());
            System.out.println();
        });
    }

    private static void demo2() {
        list.forEach(sv -> {
            System.out.println(">> Name: " + sv.getName());
            System.out.println(">> Name: " + sv.getMarks());
            System.out.println();
        });
    }

    private static void demo1() {
        List<Integer> list = Arrays.asList(1, 9, 4, 7, 5, 2);
        list.forEach(n -> System.out.println(n));
    }

}

@FunctionalInterface
interface Demo4Inter {
    void m1(int x);

    default void m2() {
    }

    public static void m3() {
    }
}