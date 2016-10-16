package mm.a10minnauki;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class popup extends AppCompatActivity {
    Slowo s;
    EditText et_pl;
    EditText et_en;
    ZarzadcaBazy zb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int high=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(high*0.6));

        et_pl=(EditText)findViewById(R.id.dod_pl);
        et_pl.setHint("tu wpisz polskie słowo");
        et_en=(EditText)findViewById(R.id.dod_en);
        et_en.setHint("tu wpisz angielskie słowo");

        zb=new ZarzadcaBazy(this);
        s=new Slowo();

    }
    public void add_new_word(View v){
        String pl=et_pl.getText().toString();
        String en=et_en.getText().toString();
        s.setword_pl(pl);
        s.setword_en(en);
        s.setrating(-1);
        zb.addWord(s);
        et_pl.setText("");
        et_en.setText("");
        et_pl.setHint("tu wpisz polskie słowo");
        et_en.setHint("tu wpisz angielskie słowo");
        Toast.makeText(getApplicationContext(), "Dodano do listy słów", Toast.LENGTH_SHORT).show();

    }
}
