    package mm.a10minnauki;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

    public class MainActivity extends AppCompatActivity{
        private Slowo s;
        private ZarzadcaBazy zb;
        private TextView tv_pol;
        private TextView tv_en;
       // private TextView pom;
        private Cursor kursor;
        private TextView counter;
        private double ile_dobrze=0;
        private double ile_zle=0;
        private double wynik;
        boolean powtorz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_pol=(TextView)findViewById(R.id.polskie);
        tv_en=(TextView)findViewById(R.id.angielskie);
        //pom=(TextView)findViewById(R.id.pomocniczy);
        zb = new ZarzadcaBazy(this);
        s=new Slowo();
        counter=(TextView)findViewById(R.id.counter);
        counter.setText("%");
        Slowo word = new Slowo("kot", "cat", 0);
        Slowo word2 = new Slowo("pies", "dog", -2);
        Slowo word3=new Slowo("tygrys","tiger",1);
        Slowo word4=new Slowo("słoń","elephant",2);
        Slowo word0=new Slowo("przykład","example",0);

        //zb.removeAll();

        //powtorz=zb.search_for_example();
       // if(!powtorz)zb.addWord(word0);
        /*zb.addWord(word);
        zb.addWord(word2);
        zb.addWord(word3);
        zb.addWord(word4);*/
        kursor=zb.nextWord();
        if(kursor.getCount()==0){
            zb.addWord(word0);
            kursor=zb.nextWord();
        }
        kursor.moveToFirst();
        s.setNr(kursor.getInt(0));
        s.setword_pl(kursor.getString(1));
        s.setword_en(kursor.getString(2));
        s.setrating(kursor.getInt(3));
        tv_pol.setText(s.getword_pl());

        List<Slowo> lista = zb.ShowAll();
        //pom.setText("");
       //for (Slowo s : lista) {
        //    pom.setText(pom.getText() + "\n" + s.getNr() + s.getword_pl() +" "+s.getword_en()+ " " + s.getrating());
        //}
    }

    @Override
    public void onStart(){
        super.onStart(); //tuuu wyszukj od nowa
        kursor=zb.nextWord();
        kursor.moveToFirst();
        s.setNr(kursor.getInt(0));
        s.setword_pl(kursor.getString(1));
        s.setword_en(kursor.getString(2));
        s.setrating(kursor.getInt(3));
        tv_pol.setText(s.getword_pl());

        List<Slowo> lista = zb.ShowAll();
        //pom.setText("");
        //for (Slowo s : lista) {
        //    pom.setText(pom.getText() + "\n" + s.getNr() + s.getword_pl() +" "+s.getword_en()+ " " + s.getrating());
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            int id=item.getItemId();
            if(id==R.id.do_listy){
                Intent i=new Intent(this,WordListActivity.class);
                startActivity(i);
            }
            return true;
        }
    public void klik_check(View v){
        tv_en.setText(s.getword_en());
        }
    public void klik_true(View v){
        tv_pol=(TextView)findViewById(R.id.polskie);
        zb.addRating(s,true);
        if(kursor.moveToNext()==false){
        kursor=zb.nextWord();
            kursor.moveToFirst();
        }
        s.setNr(kursor.getInt(0));
        s.setword_pl(kursor.getString(1));
        s.setword_en(kursor.getString(2));
        s.setrating(kursor.getInt(3));
        tv_pol.setText(s.getword_pl());

        List<Slowo> lista = zb.ShowAll();
        //pom.setText("");
        //for (Slowo s : lista) {
        //    pom.setText(pom.getText() + "\n" + s.getNr() + s.getword_pl() +" "+s.getword_en()+ " " + s.getrating());
        //}
        tv_en.setText("sprawdź");
        ile_dobrze++;
        wynik=ile_dobrze/(ile_dobrze+ile_zle);
        //counter.setText((new DecimalFormat("##.##").format(wynik*100))+"%");
        counter.setText(String.format("%.2f",wynik*100)+"%");

    }
        public void klik_false(View v){
            tv_pol=(TextView)findViewById(R.id.polskie);
            zb.addRating(s,false);
            if(kursor.moveToNext()==false){
                kursor=zb.nextWord();
                kursor.moveToFirst();
            }
            s.setNr(kursor.getInt(0));
            s.setword_pl(kursor.getString(1));
            s.setword_en(kursor.getString(2));
            s.setrating(kursor.getInt(3));
            tv_pol.setText(s.getword_pl());

            //List<Slowo> lista = zb.ShowAll();
            //pom.setText("");
            //for (Slowo s : lista) {
            //    pom.setText(pom.getText() + "\n" + s.getNr() + s.getword_pl() +" "+s.getword_en()+ " " + s.getrating());
            //}
            tv_en.setText("sprawdź");
            ile_zle++;
            wynik=ile_dobrze/(ile_dobrze+ile_zle);
            //counter.setText((new DecimalFormat("##.##").format(wynik*100))+"%");
            counter.setText(String.format("%.2f",wynik*100)+"%");
        }
        }

    //oryginalne tlo=#3300CC