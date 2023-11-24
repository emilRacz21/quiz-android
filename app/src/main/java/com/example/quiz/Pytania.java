package com.example.quiz;

import java.io.Serializable;

public class Pytania implements Serializable {
    private final String tresc;
    private final String odpowiedz1;
    private final String odpowiedz2;
    private final String odpowiedz3;
    private final String odpowiedz4;
    private final String poprawna;
    private final int img;
    public String getTresc() {
        return tresc;
    }

    public String getOdpowiedz1() {
        return odpowiedz1;
    }

    public String getOdpowiedz2() {
        return odpowiedz2;
    }

    public String getOdpowiedz3() {
        return odpowiedz3;
    }

    public String getOdpowiedz4() {
        return odpowiedz4;
    }

    public String getPoprawna() {
        return poprawna;
    }

    public int getImg() {
        return img;
    }

    public Pytania(String tresc, String odpowiedz1,String odpowiedz2, String odpowiedz3, String odpowiedz4, String poprawna, int img) {
        this.tresc = tresc;
        this.odpowiedz1 = odpowiedz1;
        this.odpowiedz2 = odpowiedz2;
        this.odpowiedz3 = odpowiedz3;
        this.odpowiedz4 = odpowiedz4;
        this.poprawna = poprawna;
        this.img = img;
    }
}
