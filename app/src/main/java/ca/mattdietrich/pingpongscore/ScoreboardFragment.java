package ca.mattdietrich.pingpongscore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreboardFragment extends Fragment implements View.OnClickListener {

    private Player[] players;

    private TextView[] tvPlayerNames;
    private TextView[] tvPoints;
    private Button[] btnAddPointPlayer;
    private Button btnResetPoints;

    public ScoreboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        tvPlayerNames = new TextView[2];
        tvPlayerNames[0] = (TextView) v.findViewById(R.id.tv_player_1_name);
        tvPlayerNames[1] = (TextView) v.findViewById(R.id.tv_player_2_name);

        tvPoints = new TextView[2];
        tvPoints[0] = (TextView) v.findViewById(R.id.tv_player_1_points);
        tvPoints[1] = (TextView) v.findViewById(R.id.tv_player_2_points);

        btnAddPointPlayer = new Button[2];
        btnAddPointPlayer[0] = (Button) v.findViewById(R.id.btn_add_point_player_1);
        btnAddPointPlayer[0].setOnClickListener(this);
        btnAddPointPlayer[1] = (Button) v.findViewById(R.id.btn_add_point_player_2);
        btnAddPointPlayer[1].setOnClickListener(this);
        btnResetPoints = (Button) v.findViewById(R.id.btn_reset_points);
        btnResetPoints.setOnClickListener(this);

        players = new Player[2];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Player " + (i + 1));
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_add_point_player_1:
                addPointToPlayer (players[0]);
                break;
            case R.id.btn_add_point_player_2:
                addPointToPlayer (players[1]);
                break;
            case R.id.btn_reset_points:
                resetPoints();
                break;
        }
    }

    /**
     * Add a point to the player, update the scoreboard, and check if the game is won
     */
    private void addPointToPlayer(Player player) {
        if (player == null)
            return;

        player.addPoint();
        updateScoreboard();

        if (isGameOver()) {
            setPointButtonsEnabled(false);
            showGameOverDialog(player);
        }
    }

    /**
     * Reset the score to 0-0 and update the scoreboard
     */
    private void resetPoints() {
        if (players == null)
            return;

        for (int i = 0; i < players.length; i++) {
            players[i].setPoints(0);
        }
        updateScoreboard();
        setPointButtonsEnabled(true);
    }

    /**
     * Update the scoreboard (points and player names)
     */
    private void updateScoreboard() {
        if (players == null || tvPoints == null || tvPlayerNames == null)
            return;

        if (players.length != tvPoints.length || players.length != tvPlayerNames.length)
            return;

        for (int i = 0; i < players.length; i++) {
            tvPoints[i].setText(""+players[i].getPoints());
            tvPlayerNames[i].setText(players[i].getName());
        }
    }

    /**
     * Check if the game win conditions are met
     */
    private boolean isGameOver() {
        if (players == null || players.length != 2)
            return false;

        // TODO: Replace with Shared Preferences (max points, win by 2)

        // If no one has enough points
        if (players[0].getPoints() < 11 && players[1].getPoints() < 11)
            return false;

        // If not winning by 2
        if (Math.abs(players[0].getPoints() - players[1].getPoints()) < 2)
            return false;

        return true;
    }

    /**
     * Enable or disable the "Add Point" buttons
     */
    private void setPointButtonsEnabled(boolean enabled) {
        for (int i = 0; i < btnAddPointPlayer.length; i++) {
            btnAddPointPlayer[i].setEnabled(enabled);
        }
    }

    /**
     * Show the a dialog declaring the winner
     */
    private void showGameOverDialog(Player winner) {
        String message = String.format(getString(R.string.dialog_game_over_message), winner.getName());

        Utility.showDialog(getActivity(), message);
    }
}
