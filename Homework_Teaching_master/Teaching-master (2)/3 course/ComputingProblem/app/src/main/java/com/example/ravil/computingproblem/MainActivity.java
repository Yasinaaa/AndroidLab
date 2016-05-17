package com.example.ravil.computingproblem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringConcat();
    }

    private void stringConcat() {
        String mainString = "";

        for (int i = 0; i < 1000000000; i++) {
            mainString = mainString + "hi ";
        }
    }

//    private void sort() {
//        int[] array = new int[1000000000];
//
//        Random random = new Random();
//        for (int i = 0; i < array.length; i++) {
//            array[i] = random.nextInt();
//        }
//
//        insertionSort(array);
//    }
//
//    private void insertionSort( int [ ] num) {
//        int j;
//        int key;
//        int i;
//
//        for (j = 1; j < num.length; j++) {
//            key = num[j];
//            for (i = j - 1; (i >= 0) && (num[i] < key); i--) {
//                num[i + 1] = num[i];
//            }
//            num[i + 1] = key;
//        }
//    }
}
