package balancer_game.com.example.honza.balancergame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static balancer_game.com.example.honza.balancergame.MainActivity.url;

public class GameActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private int mScore;
    private Directions mLastChoice;
    private List<Directions> mOptions;
    private TextView instructionsText;
    private TextView scoreText;
    private enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        instructionsText = (TextView) findViewById(R.id.instructionsText);
        scoreText = (TextView) findViewById(R.id.scoreText);

        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                TextView textView = (TextView) findViewById(R.id.instructionsText);
                textView.setText(R.string.set);
            }

            @Override
            public void onFinish() {
                startGame();
            }
        };
        countDownTimer.start();
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

    private void startGame(){
        mScore = 0;
        scoreText.setText(Integer.toString(mScore));
        mLastChoice = null;
        mOptions = new LinkedList<Directions>();
        for(int i = 0; i < Directions.values().length; i++)
            mOptions.add(Directions.values()[i]);
        mLastChoice = mOptions.get((int) (Math.random() * mOptions.size()));
        mOptions.remove(mLastChoice);
        gameStarted(pickDirection());
    }

    private Directions pickDirection(){
        final Directions newChoice = mOptions.get( (int) (Math.random() * mOptions.size()));
        mOptions.add(mLastChoice);
        mOptions.remove(newChoice);
        mLastChoice = newChoice;
        return newChoice;
    }

    private void gameStarted(Directions direction){
        instructionsText.setText(direction.toString());
        mSensorManager.registerListener(new GameSensorListener(direction), mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private class GameSensorListener implements SensorEventListener {
        private final double threshold = 2;
        private Directions direction;

        public GameSensorListener(Directions direction){
            this.direction = direction;
        }

        private void thresholdTriggered(){
            mScore += 100;
            scoreText.setText(Integer.toString(mScore));
            mSensorManager.unregisterListener(this);
            gameStarted(pickDirection());
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (direction){
                case UP:{
                    if(event.values[1] < -threshold){
                        thresholdTriggered();
                    }
                    break;
                }
                case DOWN:{
                    if(event.values[1] > threshold){
                        thresholdTriggered();
                    }
                    break;
                }
                case LEFT:{
                    if(event.values[0] > threshold){
                        thresholdTriggered();
                    }
                    break;
                }
                case RIGHT:{
                    if(event.values[0] < -threshold){
                        thresholdTriggered();
                    }
                    break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //whatever, I do not need this method
        }
    }
}
