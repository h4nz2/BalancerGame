package balancer_game.com.example.honza.balancergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ScoreboardActivity extends AppCompatActivity {
    private TextView textView;

    String url = "http://213.124.209.37:3056";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        textView = (TextView) findViewById(R.id.textView);

        RestAdapter radapter=new RestAdapter.Builder().setEndpoint(url).build();

        HTTPInterface restInt = radapter.create(HTTPInterface.class);

        restInt.getUser(new Callback<Score>() {
            @Override
            public void success(Score model, Response response) {
                textView.setText(
                                model.getName() + " " +
                                Integer.toString(model.getScore()) + " " +
                                model.getDate()
                );
            }

            @Override
            public void failure(RetrofitError error) {
                String err = error.getMessage();
            }
        });

    }
}

