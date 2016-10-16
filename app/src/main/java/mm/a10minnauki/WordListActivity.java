package mm.a10minnauki;

import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class WordListActivity extends AppCompatActivity {
    ZarzadcaBazy zb;
    TableLayout tl;
    String org_pl;
    String org_en;
    ArrayList<TextView> tv_all;
    View focusedView;
    Toast toast;
    Context context;
    CharSequence text;
    int duration;
    boolean isReturned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        zb=new ZarzadcaBazy(this);
        tl=(TableLayout)findViewById(R.id.Tab_layout);
        isReturned=false;
        TableRow tit_row=new TableRow(this);
        TableRow.LayoutParams p0=new TableRow.LayoutParams();
        p0.setMargins(20,5,20,5);
        tit_row.setLayoutParams(p0);
        TextView tv_tit_pl=new TextView(this);
        tv_tit_pl.setText("  Polskie :   ");
        tit_row.setPadding(20,5,20,5);
        tit_row.setBackgroundColor(getResources().getColor(R.color.word_pl_backgroud));
        tv_tit_pl.setTextSize(20);
        tv_tit_pl.setTypeface(null, Typeface.BOLD);
        tit_row.addView(tv_tit_pl);
        TextView tv_tit_en=new TextView(this);
        tv_tit_en.setText("Angielskie :  ");
        tv_tit_en.setTextSize(20);
        tv_tit_en.setTypeface(null, Typeface.BOLD);
        tit_row.addView(tv_tit_en);
        tl.addView(tit_row);

        ///

        ///

        for(Slowo s:zb.ShowAll()){
            TableRow row=new TableRow(this);

            final EditText tv_pol=new EditText(this);
            tv_pol.setText(s.getword_pl());
            tv_pol.setTextSize(20);
            TableRow.LayoutParams p=new TableRow.LayoutParams();
            p.setMargins(20,5,20,5);
            tv_pol.setLayoutParams(p);
            row.addView(tv_pol);
            //
            View.OnFocusChangeListener foc_pl=new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if(b){
                org_pl=tv_pol.getText().toString();
                    Log.d("fokus ma:",org_pl);
                    focusedView=view;
                    TableRow tr1=(TableRow)view.getParent();
                    tr1.setBackgroundColor(getResources().getColor(R.color.dobrze));
                }else{
                    String new_pl=tv_pol.getText().toString();
                    if(new_pl!=org_pl){
                        //tu najpierw szukanie rekordu gdzie word_pl==org_pl
                        try{Slowo s=zb.showOne(org_pl);
                        //tu wywolanie unkcji zmieniajacej rekord w db
                        zb.updateWord(true,s,new_pl);
                        Log.d("stracił fokusa",tv_pol.getText().toString());
                        TableRow tr1=(TableRow)view.getParent();
                        tr1.setBackgroundColor(getResources().getColor(R.color.tlo));}catch(CursorIndexOutOfBoundsException |NullPointerException ex){
                            Log.d("pustu kursor","chyba");
                            return;
                        }

                    }
                }
                }
            };
            tv_pol.setOnFocusChangeListener(foc_pl);
            //

            final EditText tv_en=new EditText(this);
            tv_en.setText(s.getword_en());
            tv_en.setTextSize(20);
            tv_en.setLayoutParams(p);
            row.addView(tv_en);

            //
            View.OnFocusChangeListener foc_en=new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                if(b){
                    org_en=tv_en.getText().toString();
                    Log.d("fokus ma:",org_en);
                    focusedView=view;
                    TableRow tr2=(TableRow)view.getParent();
                    tr2.setBackgroundColor(getResources().getColor(R.color.dobrze));
                }else{
                    String new_en=tv_en.getText().toString();
                    if(new_en!=org_en){
                        //tu najpierw szukanie rekordu gdzie word_pl==org_pl
                       try{ Slowo s=zb.showOne(org_en);
                        //tu wywolanie unkcji zmieniajacej rekord w db
                        zb.updateWord(false,s,new_en);
                        Log.d("stracił fokusa",tv_en.getText().toString());
                        TableRow tr2=(TableRow)view.getParent();
                        tr2.setBackgroundColor(getResources().getColor(R.color.tlo));}catch(CursorIndexOutOfBoundsException |NullPointerException ex){
                           Log.d("pustu kursor","chyba");
                           return;
                       }

                    }
                }
                }
            };
            tv_en.setOnFocusChangeListener(foc_en);

            //
            /*
            TextView tv_nr=new TextView(this);
            tv_nr.setText(s.getNr()+"");
            tv_nr.setLayoutParams(p);
            row.addView(tv_nr);

            TextView tv_rat=new TextView(this);
            tv_rat.setText(s.getrating()+"");
            tv_rat.setLayoutParams(p);
            row.addView(tv_rat);
            */
            tl.addView(row);
            /*tl.setColumnCollapsed(2,true);
            tl.setColumnCollapsed(3,true);*/
            row.setBackgroundColor(getResources().getColor(R.color.tlo));
            tv_all=new ArrayList<TextView>();
            tv_all.add(tv_pol);
            tv_all.add(tv_en);
        }
     tl.setColumnShrinkable(0,true);
     tl.setColumnStretchable(0,true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id=item.getItemId();
        if(id==R.id.to_main){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        switch(item.getItemId()){
            case R.id.to_main:
                Intent i1=new Intent(this,MainActivity.class);
                WordListActivity zmienna=this;
                startActivity(i1);
                return true;
            case R.id.add_new_word:
                Intent i2=new Intent(this,popup.class);
                startActivity(i2);
                return true;
            case R.id.delete_all:
                zb.removeAll();
                this.recreate();
                context = getApplicationContext();
                text = "Usunięto z bazy wszystkie rekordy";
                duration = Toast.LENGTH_LONG;
                toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
               for(TextView t:tv_all){
                t.setVisibility(View.GONE);
                }

                return true;
            case R.id.remove:
                EditText et0=(EditText)focusedView;
                String to_look_for=et0.getText().toString();
                zb.removeWord(to_look_for);
                context = getApplicationContext();
                text="Usunięto słowo:"+to_look_for;
                toast=Toast.makeText(context,text,duration);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                this.recreate();
        }
        return true;
    }
    @Override
    public void onPause(){
        super.onPause();
        isReturned=true;
    }
    @Override
    public void onResume(){
        super.onResume();
        if(isReturned)this.recreate();
        isReturned=false;
    }
   /* @Override
    public void onResume(){
        super.onResume();
       Log.d("stan","onResume");
        setContentView(R.layout.activity_word_list);
        zb=new ZarzadcaBazy(this);
        tl=(TableLayout)findViewById(R.id.Tab_layout);

        TableRow tit_row=new TableRow(this);
        TableRow.LayoutParams p0=new TableRow.LayoutParams();
        p0.setMargins(20,5,20,5);
        tit_row.setLayoutParams(p0);
        TextView tv_tit_pl=new TextView(this);
        tv_tit_pl.setText("  Polskie :   ");
        tit_row.setPadding(20,5,20,5);
        tit_row.setBackgroundColor(getResources().getColor(R.color.word_pl_backgroud));
        tv_tit_pl.setTextSize(20);
        tv_tit_pl.setTypeface(null, Typeface.BOLD);
        tit_row.addView(tv_tit_pl);
        TextView tv_tit_en=new TextView(this);
        tv_tit_en.setText("Angielskie :  ");
        tv_tit_en.setTextSize(20);
        tv_tit_en.setTypeface(null, Typeface.BOLD);
        tit_row.addView(tv_tit_en);
        tl.addView(tit_row);


        for(Slowo s:zb.ShowAll()){
            TableRow row=new TableRow(this);

            final EditText tv_pol=new EditText(this);
            tv_pol.setText(s.getword_pl());
            tv_pol.setTextSize(20);
            TableRow.LayoutParams p=new TableRow.LayoutParams();
            p.setMargins(20,5,20,5);
            tv_pol.setLayoutParams(p);
            row.addView(tv_pol);
            //
            View.OnFocusChangeListener foc_pl=new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        org_pl=tv_pol.getText().toString();
                        Log.d("fokus ma:",org_pl);
                        focusedView=view;
                        TableRow tr1=(TableRow)view.getParent();
                        tr1.setBackgroundColor(getResources().getColor(R.color.dobrze));
                    }else{
                        String new_pl=tv_pol.getText().toString();
                        if(new_pl!=org_pl){
                            //tu najpierw szukanie rekordu gdzie word_pl==org_pl
                            Slowo s=zb.showOne(org_pl);
                            //tu wywolanie unkcji zmieniajacej rekord w db
                            zb.updateWord(true,s,new_pl);
                            Log.d("stracił fokusa",tv_pol.getText().toString());
                            TableRow tr1=(TableRow)view.getParent();
                            tr1.setBackgroundColor(getResources().getColor(R.color.tlo));

                        }
                    }
                }
            };
            tv_pol.setOnFocusChangeListener(foc_pl);
            //

            final EditText tv_en=new EditText(this);
            tv_en.setText(s.getword_en());
            tv_en.setTextSize(20);
            tv_en.setLayoutParams(p);
            row.addView(tv_en);

            //
            View.OnFocusChangeListener foc_en=new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        org_en=tv_en.getText().toString();
                        Log.d("fokus ma:",org_en);
                        focusedView=view;
                        TableRow tr2=(TableRow)view.getParent();
                        tr2.setBackgroundColor(getResources().getColor(R.color.dobrze));
                    }else{
                        String new_en=tv_en.getText().toString();
                        if(new_en!=org_en){
                            //tu najpierw szukanie rekordu gdzie word_pl==org_pl
                            Slowo s=zb.showOne(org_en);
                            //tu wywolanie unkcji zmieniajacej rekord w db
                            zb.updateWord(false,s,new_en);
                            Log.d("stracił fokusa",tv_en.getText().toString());
                            TableRow tr2=(TableRow)view.getParent();
                            tr2.setBackgroundColor(getResources().getColor(R.color.tlo));
                        }
                    }

                }
            };
            tv_en.setOnFocusChangeListener(foc_en);

            //

//            TextView tv_nr=new TextView(this);
//            tv_nr.setText(s.getNr()+"");
//            tv_nr.setLayoutParams(p);
//            row.addView(tv_nr);
//
//            TextView tv_rat=new TextView(this);
//            tv_rat.setText(s.getrating()+"");
//            tv_rat.setLayoutParams(p);
//            row.addView(tv_rat);
//
//            tl.addView(row);
//            tl.setColumnCollapsed(2,true);
//            tl.setColumnCollapsed(3,true);
            row.setBackgroundColor(getResources().getColor(R.color.tlo));
            tv_all=new ArrayList<TextView>();
            tv_all.add(tv_pol);
            tv_all.add(tv_en);
        }
        tl.setColumnShrinkable(0,true);
        tl.setColumnStretchable(0,true);

    }
    @Override
    public void onRestart(){
        super.onRestart();
    }*/
}

