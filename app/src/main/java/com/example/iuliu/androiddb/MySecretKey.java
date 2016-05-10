package com.example.iuliu.androiddb;

/**
 * Created by Iuliu on 2016-05-08.
 */
public class MySecretKey {
    private static final long NUMBER1 = 9907;
    private static final long NUMBER2 = 6247;
    private static final long NUMBER3 = 4729;

    public long getNumber() {
        long a = NUMBER1;
        long b = NUMBER2;
        long c = NUMBER3;
        long temp1 = (a * c) + b;
        long temp2 = a * (b - c);
        long d = temp1 % temp2;
        System.out.println(d);

        return d;
    }
}