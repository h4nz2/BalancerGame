package balancer_game.com.example.honza.balancergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static balancer_game.com.example.honza.balancergame.MainActivity.URL;

public class ScoreboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Score> scoreList;
    private ScoreListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        recyclerView = (RecyclerView) findViewById(R.id.scoreList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        RestAdapter radapter=new RestAdapter.Builder().setEndpoint(URL).build();

        HTTPInterface restInt = radapter.create(HTTPInterface.class);

        restInt.getScores(new Callback<List<Score>>() {
                              @Override
                              public void success(List<Score> scores, Response response) {
                                  scoreList = scores;
                                  updateUI();
                              }

                              @Override
                              public void failure(RetrofitError error) {
                                  Log.d("getScores", error.getMessage());
                              }
                          });
    }


    public void updateUI(){
        if(adapter == null){
            adapter = new ScoreListAdapter(scoreList);
            recyclerView.setAdapter(adapter);
        }
        else
            adapter.notifyDataSetChanged();
    }


}

