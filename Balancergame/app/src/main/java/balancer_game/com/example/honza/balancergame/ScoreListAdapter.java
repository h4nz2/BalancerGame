package balancer_game.com.example.honza.balancergame;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ViewHolder> {
    private final List<Score> scoreList;

    public ScoreListAdapter(List<Score> newList){
        this.scoreList = newList;
    }

    @Override
    public ScoreListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreListAdapter.ViewHolder holder, int position) {
        holder.populateRow(scoreList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public void updateList(List<Score> newlist) {
        // Set new updated list
        scoreList.clear();
        scoreList.addAll(newlist);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameView;
        private TextView scoreView;
        private TextView dateView;
        private TextView latView;
        private TextView longView;
        private TextView positionView;

        public ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.textName);
            scoreView = (TextView) view.findViewById(R.id.textScore);
            dateView = (TextView) view.findViewById(R.id.textDate);
            latView = (TextView) view.findViewById(R.id.textLatitude);
            longView = (TextView) view.findViewById(R.id.textLongitude);
            positionView = (TextView) view.findViewById(R.id.textPosition);
        }

        public void populateRow(Score score, int position){
            nameView.setText(score.getName());
            scoreView.setText(Integer.toString(score.getScore()));
            dateView.setText(score.getDate());
            if(score.getLatitude() == 0 && score.getLongitude() == 0){
                latView.setText(R.string.unknown);
                longView.setText(R.string.unknown);
            }
            else {
                latView.setText(Float.toString(score.getLatitude()));
                longView.setText(Float.toString(score.getLongitude()));
            }
            positionView.setText(Integer.toString(position + 1));
        }
    }
}
