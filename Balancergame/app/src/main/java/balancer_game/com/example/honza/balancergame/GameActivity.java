package balancer_game.com.example.honza.balancergame;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static balancer_game.com.example.honza.balancergame.MainActivity.url;

public class GameActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        //submitScore(new Score("Android", 888));
    }

    private void submitScore(Score score){
        RestAdapter radapter=new RestAdapter.Builder().setEndpoint(url).build();

        HTTPInterface restInt = radapter.create(HTTPInterface.class);

        restInt.postScore(score, new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                Log.d("postScore", "Submission successful.");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("postScore", "Submission failed." + error.getMessage());
            }
        });
    }
}
