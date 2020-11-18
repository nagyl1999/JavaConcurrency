package com.company;

import java.util.LinkedList;

/** Fifo osztály, egy láncolt listában tárolja a Producertől kapott üzeneteket */
public class Fifo extends Thread {
    /** Stringeket tároló láncolt lista */
    private LinkedList<String> stringek;
    /** Maximum elem, statikus */
    public static int max = 10;
    /** Minimum elem, statikus */
    public static int min = 0;

    /** Konstruktor, láncolt lista létrehozása */
    public Fifo() {
        stringek = new LinkedList<>();
    }

    /** Elem hozzáfűzése a láncolt listához a végére, thread-safe */
    public synchronized void put(String s) throws InterruptedException {
        /* Ha a tároló elemei elérik a maximum méretet, akkor a futtató szál várakozik */
        if(this.getSize() == Fifo.max) this.wait();
        /* Ha nincs tele a tároló, felébresztünk egy Producert */
        else this.notify();
        /* Kiírjuk a metódus nevét, és a futtató szál azonosítóját */
        System.out.println(String.format("Fifo put %d", Thread.currentThread().getId()));
        /* Új String hozzáfűzése a láncolt listához */
        stringek.addLast(s);
    }

    /** Legrégebben hozzáadott, vagyis a lista elején álló elem törlése, majd visszatérés az értékével, thread-safe */
    public synchronized String get() throws InterruptedException {
        /* Ha a tároló üres, akkor a futó szál várakozik, visszatérünk */
        if(this.getSize() == Fifo.min) {
            /* Szál várakoztatása */
            this.wait();
            /* Visszatérünk, mvel nincs mit lekérdezni a tárolóból */
            return null;
        /* Ha nem üres a tároló, felébresztünk egy Consumert */
        }else this.notify();
        /* Kiírjuk a metódus nevét, és a futtató szál azonosítóját */
        System.out.println(String.format("Fifo get %d", Thread.currentThread().getId()));
        /* String értékének eltárolása */
        String s = stringek.getFirst();
        /* String törlése a listából */
        stringek.removeFirst();
        /* Visszatérés az értékkel */
        return s;
    }

    /** Tároló elemeinek a száma */
    public synchronized int getSize() {
        return stringek.size();
    }

}
