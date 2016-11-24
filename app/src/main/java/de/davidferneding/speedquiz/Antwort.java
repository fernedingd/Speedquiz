package de.davidferneding.speedquiz;

public class Antwort {
    private String Frage;
    private int Antwort;
    private int RichtigeAntwort;

    public Antwort(String frage, int antwort, int richtigeAntwort){
        Frage = frage;
        Antwort = antwort;
        RichtigeAntwort = richtigeAntwort;
    }

    public String getFrage(){
        return Frage;
    }

    public int getAntwort() {
        return Antwort;
    }

    public int getRichtigeAntwort() {
        return RichtigeAntwort;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public void setAntwort(int antwort) {
        Antwort = antwort;
    }

    public void setRichtigeAntwort(int richtigeAntwort) {
        RichtigeAntwort = richtigeAntwort;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
