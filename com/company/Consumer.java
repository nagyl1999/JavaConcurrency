package com.company;

/** Consumer osztály, Fifotól átvett üzeneteket ír a kimenetre */
public class Consumer extends Thread {
    /** Az osztályhoz tartozó Fifo osztály */
    private Fifo fifo;
    /** Az alvási időt tároló változó -> ms */
    private int sleeptime;
    /** Az osztály kapott üzenete */
    private String message;

    /** Konstruktor, beállítja a kapott Fifo-t, String-et, és az időt amig alszik */
    public Consumer(Fifo f, String s, int n) {
        this.fifo = f;
        this.message = s;
        this.sleeptime = n;
    }

    /** Az üzenet kiírása */
    private void printOutput(String msg) {
        System.out.println(String.format("consumed %s %s %d", message, msg, System.currentTimeMillis() % 100000));
    }

    /** Egyszeri üzenetkiírás */
    public void run() {
        try {
            /* Üzenet a Fifo-tól */
            String msg = fifo.get();
            /* Ha a String null, vagyis a tároló üres, továbblépünk */
            if(msg == null) return;
            /* Üzenet kiírása */
            this.printOutput(msg);
            /* Alvás */
            Thread.sleep(sleeptime);
        }catch(InterruptedException e) {
            /* Hiba */
            e.printStackTrace();
        }
    }

}
