package balancer_game.com.example.honza.balancergame;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("date")
    @Expose
    private String date;

    public Score(){};

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}