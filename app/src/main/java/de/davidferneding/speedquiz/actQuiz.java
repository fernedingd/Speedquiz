package de.davidferneding.speedquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Timer;

public class actQuiz extends Activity {

    int richtigeAntwort;
    public static final int ANTWORT_1 = 1;
    public static final int ANTWORT_2 = 2;
    public static final int ANTWORT_3 = 3;
    public static final int ANTWORT_4 = 4;
    public static final int GRUEN = 1;
    public static final int ROT = 2;
    public static String table;
    public Question currentquestion;
    public DatabaseHelper helper;
    public int anzahlRichtige = 0;
    private ArrayList<Antwort> antworten;
    private  boolean keineNeueFrage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_quiz);
        resizeButtons();

        keineNeueFrage = false;
        currentquestion = new Question("", "", "", "", "", 0);
        helper = new DatabaseHelper(this.getApplicationContext());
        antworten = new ArrayList<>();

        Button btn1 = (Button) findViewById(R.id.btnAntwort1);
        Button btn2 = (Button) findViewById(R.id.btnAntwort2);
        Button btn3 = (Button) findViewById(R.id.btnAntwort3);
        Button btn4 = (Button) findViewById(R.id.btnAntwort4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAnswer(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAnswer(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAnswer(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAnswer(4);
            }
        });

        getQuestion();
    }

    private void resizeButtons() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        double textViewHeight = ((size.y / 2) - 15);
        double buttonWidth = size.x / 2.5;//((size.x - 60) / 2) - 10;
        double buttonHeight = textViewHeight / 2.5;//((textViewHeight - 30) / 2) - 10;

        TextView textView = (TextView) findViewById(R.id.tbFrage);
        ViewGroup.LayoutParams tvParams = textView.getLayoutParams();
        tvParams.height = (int) textViewHeight;
        textView.setLayoutParams(tvParams);

        Button button1 = (Button) findViewById(R.id.btnAntwort1);
        ViewGroup.LayoutParams btnParams = button1.getLayoutParams();
        btnParams.height = (int) buttonHeight;
        btnParams.width = (int) buttonWidth;
        button1.setLayoutParams(btnParams);

        Button button2 = (Button) findViewById(R.id.btnAntwort2);
        btnParams = button2.getLayoutParams();
        btnParams.height = (int) buttonHeight;
        btnParams.width = (int) buttonWidth;
        button2.setLayoutParams(btnParams);

        Button button3 = (Button) findViewById(R.id.btnAntwort3);
        btnParams = button3.getLayoutParams();
        btnParams.height = (int) buttonHeight;
        btnParams.width = (int) buttonWidth;
        button3.setLayoutParams(btnParams);

        Button button4 = (Button) findViewById(R.id.btnAntwort4);
        btnParams = button4.getLayoutParams();
        btnParams.height = (int) buttonHeight;
        btnParams.width = (int) buttonWidth;
        button4.setLayoutParams(btnParams);
    }

    private void getQuestion() {
        if (keineNeueFrage)
                return;

        String frage = "";
        String antwort1 = "";
        String antwort2 = "";
        String antwort3 = "";
        String antwort4 = "";

        Random randomizer = new Random();
        int nextValue = 0;
        boolean OK = false;
        while (!OK) {
            nextValue = randomizer.nextInt((helper.rowcount(table)));
            if (antworten.size() == 0) {
                OK = true;
            } else {
                boolean existiert = false;
                Question q = helper.getSingleQuestion(table, nextValue + 1);
                for (int x = 0; x < antworten.size(); x++) {
                    if (antworten.get(x).getFrage().equals(q.getFRAGE())) {
                        existiert = true;
                    }
                }
                if (!existiert) {
                    OK = true;
                }
            }
        }

        antworten.add(new Antwort("", 0, 0));
        if (antworten.size() == helper.rowcount(table))
            keineNeueFrage = true;
        
        currentquestion = helper.getSingleQuestion(table, nextValue + 1);

        frage = currentquestion.getFRAGE();
        antwort1 = currentquestion.getAW1();
        antwort2 = currentquestion.getAW2();
        antwort3 = currentquestion.getAW3();
        antwort4 = currentquestion.getAW4();

        TextView tvFrage = (TextView) findViewById(R.id.tbFrage);
        Button btnAW1 = (Button) findViewById(R.id.btnAntwort1);
        Button btnAW2 = (Button) findViewById(R.id.btnAntwort2);
        Button btnAW3 = (Button) findViewById(R.id.btnAntwort3);
        Button btnAW4 = (Button) findViewById(R.id.btnAntwort4);

        tvFrage.setText(frage);
        btnAW1.setText(antwort1);
        btnAW2.setText(antwort2);
        btnAW3.setText(antwort3);
        btnAW4.setText(antwort4);
    }

    public void readAnswer(int antwort) {
        if (antwort == currentquestion.getRAW()) {
            screenFlash(GRUEN);
            anzahlRichtige++;
        } else {
            screenFlash(ROT);
        }

        antworten.set(antworten.size() - 1, new Antwort(currentquestion.getFRAGE(), antwort, currentquestion.getRAW()));
        getQuestion();
    }

    public void screenFlash(int colour) {
        View v = findViewById(R.id.layoutQuiz);

        switch (colour) {
            case GRUEN:
                v.setBackgroundColor(Color.GREEN);
                break;
            case ROT:
                v.setBackgroundColor(Color.RED);
                break;
        }

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                blackBackground();
            }
        }, 100);
    }

    public void blackBackground() {
        View v = findViewById(R.id.layoutQuiz);
        v.setBackgroundColor(Color.BLACK);
    }
}
