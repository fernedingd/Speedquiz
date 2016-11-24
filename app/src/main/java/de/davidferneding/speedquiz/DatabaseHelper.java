package de.davidferneding.speedquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.gesture.GestureOverlayView;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "fragenkatalog";

    public static final String TABLE_GEO = "geographie";
    public static final String TABLE_MED = "medizin";
    public static final String TABLE_FIS = "filmundserien";
    public static final String TABLE_NAT = "naturwissenschaften";

    private static final String KEY_ID = "id";
    private static final String KEY_FRAGE = "frage";
    private static final String KEY_RAW = "rAntwort";
    private static final String KEY_AW1 = "antwort1";
    private static final String KEY_AW2 = "antwort2";
    private static final String KEY_AW3 = "antwort3";
    private static final String KEY_AW4 = "antwort4";

    private SQLiteDatabase dbase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GEO + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FRAGE
                + " TEXT, " + KEY_RAW + " INTEGER, " + KEY_AW1 + " TEXT, "
                + KEY_AW2 + " TEXT, " + KEY_AW3 + " TEXT, " + KEY_AW4 + " TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MED + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FRAGE
                + " TEXT, " + KEY_RAW + " INTEGER, " + KEY_AW1 + " TEXT, "
                + KEY_AW2 + " TEXT, " + KEY_AW3 + " TEXT, " + KEY_AW4 + " TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FIS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FRAGE
                + " TEXT, " + KEY_RAW + " INTEGER, " + KEY_AW1 + " TEXT, "
                + KEY_AW2 + " TEXT, " + KEY_AW3 + " TEXT, " + KEY_AW4 + " TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAT + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FRAGE
                + " TEXT, " + KEY_RAW + " INTEGER, " + KEY_AW1 + " TEXT, "
                + KEY_AW2 + " TEXT, " + KEY_AW3 + " TEXT, " + KEY_AW4 + " TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAT);
        onCreate(db);
    }

    public void addQuestion(Question quest, String table) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FRAGE, quest.getFRAGE());
        values.put(KEY_RAW, quest.getRAW());
        values.put(KEY_AW1, quest.getAW1());
        values.put(KEY_AW2, quest.getAW2());
        values.put(KEY_AW3, quest.getAW3());
        values.put(KEY_AW4, quest.getAW4());
        dbase.insert(table, null, values);
    }

    public List<Question> getAllQuestions(String table) {
        List<Question> quesList = new ArrayList<Question>();
        String selectQuery = "SELECT  * FROM " + table;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question("", "", "", "", "", 0);
                quest.setID(cursor.getInt(0));
                quest.setFRAGE(cursor.getString(1));
                quest.setRAW(cursor.getInt(2));
                quest.setAW1(cursor.getString(3));
                quest.setAW2(cursor.getString(4));
                quest.setAW3(cursor.getString(5));
                quest.setAW4(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        return quesList;
    }

    public int rowcount(String table) {
        int anzahl = 0;
        String selectQuery = "SELECT  * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        anzahl = cursor.getCount();
        return anzahl;
    }

    public Question getSingleQuestion(String table, int id) {
        String selectQuery = "SELECT * FROM " + table + " WHERE id=" + id + " LIMIT 1";
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        Question quest = new Question("", "", "", "", "", 0);
        if (cursor.moveToFirst()) {
            quest.setID(cursor.getInt(0));
            quest.setFRAGE(cursor.getString(1));
            quest.setRAW(cursor.getInt(2));
            quest.setAW1(cursor.getString(3));
            quest.setAW2(cursor.getString(4));
            quest.setAW3(cursor.getString(5));
            quest.setAW4(cursor.getString(6));
        }
        return quest;
    }

    private void addQuestions() {
        Question q = new Question("Was ist die Hauptstadt von Frankreich?", "Berlin", "Rom", "Paris", "Madrid", 3);
        this.addQuestion(q, TABLE_GEO);

        q = new Question("Was ist die Landeshauptstadt von Bayern?", "Hamburg", "München", "Berlin", "Dresden", 2);
        this.addQuestion(q, TABLE_GEO);

        q = new Question("Welches Land hat nicht den Euro als Währung?", "Frankreich", "Portugal", "Schweiz", "Griechenland", 3);
        this.addQuestion(q, TABLE_GEO);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);

        q = new Question("Welcher Agent hat die \"Lizenz zum töten?\"", "Kingsmen", "Jason Bourne", "Ethan Hunt", "James Bond", 4);
        this.addQuestion(q, TABLE_FIS);

        q = new Question("Von wem handelt \"The Social Network\"?", "Mark Zuckerberg", "Bill Gates", "Steve Wozniak", "Henry Ford", 1);
        this.addQuestion(q, TABLE_FIS);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);

        q = new Question("Was ist Kochsalz?", "Natriumbromid", "Wasserstoffperoxid", "Kaliumchlorid", "Natriumchlorid", 4);
        this.addQuestion(q, TABLE_NAT);

        q = new Question("Warum werden bestimmte Frequenzen als \"Ultraschall\" bezeichnet?", "Die Schallwellen sind besonders lang", "Sie liegen im nicht hörbaren Bereich", "Sie sind sehr lange zu hören", "Alle Antworten", 2);
        this.addQuestion(q, TABLE_NAT);

        q = new Question("Was ist die chemische Formel von Wasser?", "W", "HO(2)", "H(2)O", "Wa", 3);
        this.addQuestion(q, TABLE_NAT);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);

        q = new Question("Welches Organ produziert Insulin?", "Hirnanhangdrüse", "Galle", "Zwölffingerdarm", "Bauchspeicheldrüse", 4);
        this.addQuestion(q, TABLE_MED);

        q = new Question("In welchem Rythmus wird die Herz-Lungen-Wiederbelebung durchgeführt?", "30x Herzdruckmassage\n2x Beatmen", "2x Herzdruckmassage\n30x Beatmen", "15x Herzdruckmassage\n15x Beatmen", "Mit den Armen des Patienten über dem Körper wedeln", 1);
        this.addQuestion(q, TABLE_MED);

        q = new Question("Wo wird ein Wendl-Tubus eingeführt?", "Mund", "Nase", "Ohr", "Anus", 2);
        this.addQuestion(q, TABLE_MED);

        q = new Question("Wie viele Nieren hat ein normaler Mensch?", "1", "3", "2", "4", 3);
        this.addQuestion(q, TABLE_MED);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 1);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 2);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 3);
//        this.addQuestion(q, TABLE_);
//
//        q = new Question("Frage", "Antwort", "Antwort", "Antwort", "Antwort", 4);
//        this.addQuestion(q, TABLE_);
    }
}