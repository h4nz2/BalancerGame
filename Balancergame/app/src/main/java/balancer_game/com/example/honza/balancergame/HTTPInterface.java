package balancer_game.com.example.honza.balancergame;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;


public interface HTTPInterface {
    @GET("/scores")
    void getScores(Callback<List<Score>> cb);

    @POST("/scores/post")
    void postScore(@Body Score score, Callback<Object> cb);

}
