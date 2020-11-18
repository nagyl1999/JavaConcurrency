package com.company;

/** Producer osztály, Fifo-nak ad át üzenetet */
public class Producer implements Runnable {
    /** Az osztályhoz tartozó Fifo osztály */
    private Fifo fifo;
    /** A sorszámot tároló változó */
    private int serial;
    /** Az osztály saját üzenete */
    private String message;
    /** Alvási idő -> ms */
    private int sleeptime;

    /** Konstruktor, sorszám nullázása, és üzenet átvétele */
    public Producer(Fifo fifo, String message, int n) {
        this.fifo = fifo;
        this.serial = 0;
        this.message = message;
        this.sleeptime = n;
    }

    /** Az üzenet kiírása, majd sorszám növelése egyel, <üzenet> <sorszám> <időpont> */
    private void printOutput() {
        /* A sorszámot itt növeljük közvetlen a formatting után! */
        System.out.println(String.format("produced %s %d %d", message, serial++, System.currentTimeMillis() % 100000));
    }

    /** Egyszeri üzenetkiírás */
    public void run() {
        try {
            /* Fifo-hoz adás */
            fifo.put(String.format("%s %d", message, serial));
            /* Üzenet kiírása, sorszám itt nő */
            this.printOutput();
            /* Alvás */
            Thread.sleep(sleeptime);
        }catch(InterruptedException e) {
            /* Hiba */
            e.printStackTrace();
        }
    }

}
