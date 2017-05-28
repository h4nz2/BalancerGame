package balancer_game.com.example.honza.balancergame;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static balancer_game.com.example.honza.balancergame.MainActivity.NAME_KEY;
import static balancer_game.com.example.honza.balancergame.MainActivity.URL;

public class GameActivity extends AppCompatActivity {
    private static final int NUMBER_OF_TURNS = 20;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private int mScore;
    private Directions mLastChoice;
    private List<Directions> mOptions;
    private TextView instructionsText;
    private TextView scoreText;
    private int mTurnsCount;
    private String mName;
    private long mTime;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        instructionsText = (TextView) findViewById(R.id.instructionsText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        mName = getIntent().getStringExtra(NAME_KEY);

        CountDownTimer countDownTimer = new CountDownTimer(2000, 950){
            private int ticks;
            @Override
            public void onTick(long millisUntilFinished) {
                if(ticks > 0) {
                    TextView textView = (TextView) findViewById(R.id.instructionsText);
                    textView.setText(R.string.set);
                }
                ticks++;
            }

            @Override
            public void onFinish() {
                startGame();
            }
        };
        countDownTimer.start();
    }

    private void submitScore(Score score){
        RestAdapter radapter=new RestAdapter.Builder().setEndpoint(URL).build();

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
        mTurnsCount = NUMBER_OF_TURNS;
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
        mTime = SystemClock.uptimeMillis();
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
            mScore += calculateScore(SystemClock.uptimeMillis() - mTime);
            scoreText.setText(Integer.toString(mScore));
            mSensorManager.unregisterListener(this);
            if(--mTurnsCount > 0)
                gameStarted(pickDirection());
            else
                endGame();
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

    private void endGame() {
        Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(R.string.gameOver);
        alertDialogBuilder.setMessage("Your score: " + Integer.toString(mScore));
        alertDialogBuilder.setNeutralButton(R.string.mainMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Get LocationManager object from System Service LOCATION_SERVICE
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                    // Create a criteria object to retrieve provider
                    Criteria criteria = new Criteria();

                    // Get the name of the best provider
                    String provider = locationManager.getBestProvider(criteria, true);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    locationManager.requestSingleUpdate(criteria, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            submitScore(new Score(
                                    mName,
                                    mScore,
                                    (float) location.getLatitude(),
                                    (float) location.getLongitude()
                            ));
                            startActivity(intent);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }, null);
                }
                else{
                    submitScore(new Score(
                            mName,
                            mScore,
                            0,
                            0
                    ));
                    startActivity(intent);
                }
            }
        });
        alertDialogBuilder.show();

    }

    /**
     *
     * @param time time in miliseconds
     * @return Returns score based on time, max score(=100) is for time <= 200
     * and min score(=0) is for time >= 2000.
     * Value scales down based on time, the formula is a power function,
     * which means that the values drops faster at the beginning than at the end
     */
    private int calculateScore(long time){
        if(time < 200)
            time = 200;
        else if(time > 2000)
            time = 2000;
        double result = (( 1.0/((((double)time)/1000)+1.0)-1.0/3.0 )*2000.0);
        return (int)(result);
    }
}