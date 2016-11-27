package de.davidferneding.speedquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.davidferneding.speedquiz.Time;

public class actAuswertung extends Activity {
    public static String tabellenName;
    private int richtwert;
    private long zeitInMillis;
    private int richtige;
    private int fragen;
    private long punkte;
    private double faktor;
    private long highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswertung);

        zeitInMillis = getIntent().getLongExtra("endZeit", 0) - getIntent().getLongExtra("startZeit", 0);
        richtige = getIntent().getIntExtra("richtige", 0);
        fragen = getIntent().getIntExtra("fragen", 0);
        faktor = (double) richtige / fragen;
        richtwert = (int) (30000 * ((double) fragen / 10));

        SharedPreferences prefs = this.getSharedPreferences("highscores", Context.MODE_PRIVATE);
        highscore = prefs.getLong("highscore_" + tabellenName, 0);

        //Formel (c)DLRG
        //
        //p{0<=zeit<=2*rec}=        467*(zeit/rec)²-2001*(zeit/rec)+2534
        //p{2*rec<=zeit<=5*rec}=    (200/3)-(400/3)*(zeit/rec)
        //
        //gesammtpunkte = p*faktor

        double zeitDurchRec = (double) zeitInMillis / (double) richtwert;
        if (zeitInMillis <= (2 * richtwert)) {
            punkte = (long) (467 * Math.pow(zeitDurchRec, 2) - 2001 * zeitDurchRec + 2534);
        } else {
            punkte = (long) ((200 / 3) - (400 / 3) * zeitDurchRec);
        }
        punkte *= faktor;
        if (punkte < 0)
            punkte = 0;

        TextView tvErgebnis = (TextView) findViewById(R.id.tvErgebnis);
        if (punkte > highscore) {
            tvErgebnis.setText("Anzahl Fragen:\t\t\t\t\t" + fragen + "\nRichtige Antworten:\t" + richtige +
                    "\nErreichte Punktzahl:\t" + punkte + "\nAlter Highscore:\t\t" + highscore +
                    "\nNeuer Highscore:\t\t" + punkte);
            highscore = punkte;
        } else {
            tvErgebnis.setText("Anzahl Fragen:\t\t\t\t\t" + fragen + "\nRichtige Antworten:\t" + richtige +
                    "\nErreichte Punktzahl:\t" + punkte + "\nHighscore:\t\t\t\t\t" + highscore);
        }
        tvErgebnis.setBackgroundColor(Color.WHITE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("highscore_" + tabellenName, highscore);
        editor.commit();

        Button btnNeustart = (Button) findViewById(R.id.btnNeustart);
        btnNeustart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actAuswertung.this, actThemenauswahl.class);
                startActivity(i);
                finish();
            }
        });
    }
}