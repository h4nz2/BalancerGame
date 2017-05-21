package balancer_game.com.example.honza.balancergame;

import retrofit.Callback;
import retrofit.http.GET;


public interface HTTPInterface {
    @GET("/player")
    void getUser(Callback<Score> uscb);
}
