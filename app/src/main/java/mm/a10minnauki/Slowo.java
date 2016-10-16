package mm.a10minnauki;

/**
 * Created by Mateusz on 2016-07-23.
 */
public class Slowo {

    private int nr;
    private String word_pl;
    private String word_en;
    private int rating;

    public Slowo(){}

    public Slowo(String word_pl,String word_en,int rating){
        this.word_pl=word_pl;
        this.word_en=word_en;
        this.rating=rating;

    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getword_pl() {
        return word_pl;
    }

    public int getrating() {
        return rating;
    }

    public void setrating(int rating) {
        this.rating = rating;
    }

    public void setword_pl(String word_pl) {
        this.word_pl = word_pl;
    }

    public String getword_en() {
        return word_en;
    }

    public void setword_en(String word_en) {
        this.word_en = word_en;
    }
}
