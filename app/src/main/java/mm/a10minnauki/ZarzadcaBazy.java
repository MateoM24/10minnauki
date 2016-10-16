package mm.a10minnauki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ZarzadcaBazy extends SQLiteOpenHelper {

    protected String[] kolumny={"nr","word_pl","word_en","rating"};
    //private String table_main;


    public ZarzadcaBazy(Context context) {
        super(context, "BD_words.db", (CursorFactory) null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table table_main(nr integer primary key autoincrement,word_pl text,word_en text,rating integer);");
        } catch (SQLException e) {
            Log.d("błąd", "nie udalo sie utworzyc tablicy");
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void addWord(Slowo s){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("word_pl",s.getword_pl());
        cv.put("word_en",s.getword_en());
        cv.put("rating",s.getrating());
        db.insertOrThrow("table_main",(String)null,cv);
        db.close();
    }
    public void removeWord(String s){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] args={s,s};
        db.delete("table_main"," word_pl=? or word_en=?",args);
        db.close();
    }//nie objektowa metoda
    public void removeAll(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("table_main",null,null);
        db.close();
    }
    public Slowo showOne(String str){
        Slowo s = new Slowo();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[]{str,str};
        Cursor kursor = db.query("table_main", kolumny, " word_pl=? or word_en=?", args, (String)null, (String)null, (String)null, (String)null);
        if(kursor != null) {
            kursor.moveToFirst();
            s.setNr(kursor.getInt(0));
            s.setword_pl(kursor.getString(1));
            s.setword_en(kursor.getString(2));
            s.setrating(kursor.getInt(3));
            db.close();
        }
        return s;
    }
    public boolean search_for_example(){
        Slowo s = new Slowo();
        boolean wynik;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[]{"przykład"};
        Cursor kursor = db.query("table_main", kolumny, " word_pl=?", args, (String)null, (String)null, (String)null, (String)null);
        if(kursor != null) wynik= true;
        else wynik= false;
    return wynik;
    }
    public Cursor nextWord(){
        Cursor kursor;
        Slowo s;
        SQLiteDatabase db=this.getReadableDatabase();
        kursor=db.query("table_main",kolumny,null,null,null,null,kolumny[3],"40");

        return kursor;
    }
    public void addRating(Slowo s,boolean ok){
        SQLiteDatabase db=getWritableDatabase();
        String[] arg={Integer.toString(s.getNr())};
        ContentValues cv=new ContentValues();
        int g;
        g=ok?s.getrating()+1:s.getrating()-1;
        //cv.put(kolumny[0],s.getNr());
        //cv.put(kolumny[1],s.getword_pl());
        //cv.put(kolumny[2],s.getword_en());
        cv.put(kolumny[3],g);
        db.update("table_main",cv," nr=?",arg);
        db.close();
    }
    public List<Slowo> ShowAll(){
        LinkedList lista = new LinkedList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor kursor = db.query("table_main", kolumny, (String)null, (String[])null, (String)null, (String)null, (String)null);

        while(kursor.moveToNext()) {
            Slowo s = new Slowo();
            s.setNr(kursor.getInt(0));
            s.setword_pl(kursor.getString(1));
            s.setword_en(kursor.getString(2));
            s.setrating(kursor.getInt(3));
            lista.add(s);
        }
        db.close();
        return lista;
    }
    public void updateWord(boolean pl,Slowo s,String upd){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        if(pl) {
            cv.put(kolumny[1],upd);
            cv.put(kolumny[2],s.getword_en());
        }else{
            cv.put(kolumny[1],s.getword_pl());
            cv.put(kolumny[2],upd);
        }
            //cv.put(kolumny[3],s.getrating());
        String[] arg={Integer.toString(s.getNr())};
        db.update("table_main",cv,"nr=?",arg);
        db.close();
    }
}
