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
        holder.populateRow(scoreList.get(position));
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

        public ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.textName);
            scoreView = (TextView) view.findViewById(R.id.textScore);
            dateView = (TextView) view.findViewById(R.id.textDate);
        }

        public void populateRow(Score score){
            nameView.setText(score.getName());
            scoreView.setText(Integer.toString(score.getScore()));
            dateView.setText(score.getDate());
        }
    }
}
