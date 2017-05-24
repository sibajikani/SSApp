package com.example.s214092755.ssapp.Models;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by s214092755 on 2017/05/21.
 */

public class Duo implements Comparable{
    String name;
    Integer res;
    public static final Comparator<Duo> compare = new Comparator<Duo>() {
        @Override
        public int compare(Duo o1, Duo o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    public Duo(String name, Integer res) {
        this.name = name;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public Integer getRes() {
        return res;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
