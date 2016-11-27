package de.davidferneding.speedquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class actThemenauswahl extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_themenauswahl);

        Button btnGeographie = (Button) findViewById(R.id.btnGeographie);
        Button btnMedizin = (Button) findViewById(R.id.btnMedizin);
        Button btnFilmSerien = (Button) findViewById(R.id.btnFilmSerien);
        Button btnNaturwissenschaften = (Button) findViewById(R.id.btnNaturwissenschaften);
        TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
        try {
            tvVersion.setText("Version: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName.toString());
        } catch (Exception ex) {
            //Wird nicht behandelt
        }
        btnGeographie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actThemenauswahl.this, actQuiz.class);
                startActivity(i);
                actQuiz.table = DatabaseHelper.TABLE_GEO;
                finish();
            }
        });
        btnMedizin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actThemenauswahl.this, actQuiz.class);
                startActivity(i);
                actQuiz.table = DatabaseHelper.TABLE_MED;
                finish();
            }
        });
        btnFilmSerien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actThemenauswahl.this, actQuiz.class);
                startActivity(i);
                actQuiz.table = DatabaseHelper.TABLE_FIS;
                finish();
            }
        });
        btnNaturwissenschaften.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actThemenauswahl.this, actQuiz.class);
                startActivity(i);
                actQuiz.table = DatabaseHelper.TABLE_NAT;
                finish();
            }
        });
    }
}
