package de.davidferneding.speedquiz;

public class Question {
    private int ID;
    private String FRAGE;
    private String AW1;
    private String AW2;
    private String AW3;
    private String AW4;
    private int RAW;

    public Question(String fRAGE, String aW1, String aW2, String aW3, String aW4, int rAW)
    {
        FRAGE = fRAGE;
        AW1 = aW1;
        AW2 = aW2;
        AW3 = aW3;
        AW4 = aW4;
        RAW = rAW;
    }

    public int getID()
    {
        return ID;
    }
    public String getFRAGE() {
        return FRAGE;
    }
    public String getAW1() {
        return AW1;
    }
    public String getAW2() {
        return AW2;
    }
    public String getAW3() {
        return AW3;
    }
    public String getAW4() {
        return AW4;
    }
    public int getRAW() {
        return RAW;
    }
    public void setID(int id)
    {
        ID=id;
    }
    public void setFRAGE(String fRAGE) {
        FRAGE = fRAGE;
    }
    public void setAW1(String aW1) {
        AW1 = aW1;
    }
    public void setAW2(String aW2) {
        AW2 = aW2;
    }
    public void setAW3(String aW3) {
        AW3 = aW3;
    }
    public void setAW4(String aW4) {
        AW4 = aW4;
    }
    public void setRAW(int rAW) {
        RAW = rAW;
    }

}