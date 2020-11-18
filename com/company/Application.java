package com.company;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** Application osztály */
public class Application {
    /** Minimum várakozási idő */
    public static int min = 300;
    /** Maximum várakozási idő */
    public static int max = 1000;
    /** Kezdő várakozás -> ms */
    public static int start = 1000;
    /** Taskok közötti várakozás -> ms */
    public static int between = 250;
    /** PoolSize */
    public static int poolsize = 10;
    /** Maximum futtatandó objektumok */
    public static int maxRunnable = 15;

    /** Véletlenszerű várakozási idő generálás */
    public static int getWaittime() {
        return (int) ((Math.random() * (Application.max - Application.min)) + Application.min);
    }

    /** Belépő metódus */
    public static void main(String[] args) {
        /* Fifo */
        Fifo f = new Fifo();

        /* ScheduledThreadPoolExecutor Producer */
        ScheduledThreadPoolExecutor stpep = new ScheduledThreadPoolExecutor(Application.poolsize);
        /* 10 Producer beszúrása */
        for(int i=0;i<Application.maxRunnable;i++) {
            /* Példa execute-al */
            /* stpep.execute(new Producer(f, "producer" + i, getWaittime())); */
            /* Execute helyett scheduleWithFixedDelay, mivel támogatja beépítve az időzítést */
            stpep.scheduleWithFixedDelay(new Producer(f, "producer" + i, getWaittime()), Application.start, Application.between, TimeUnit.MILLISECONDS);
        }

        /* ScheduledThreadPoolExecutor Consumer */
        ScheduledThreadPoolExecutor stpec = new ScheduledThreadPoolExecutor(Application.poolsize);
        /* 10 Consumer beszúrása */
        for(int i=0;i<Application.maxRunnable;i++) {
            /* Példa execute-al */
            /* stpec.execute(new Consumer(f, "consumer" + i, getWaittime())); */
            /* Execute helyett scheduleWithFixedDelay, mivel támogatja beépítve az időzítést */
            stpec.scheduleWithFixedDelay(new Consumer(f, "consumer" + i, getWaittime()), Application.start, Application.between, TimeUnit.MILLISECONDS);
        }

    }
}
