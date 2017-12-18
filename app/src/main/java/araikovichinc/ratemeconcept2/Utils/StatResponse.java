package araikovichinc.ratemeconcept2.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tigran on 17.12.2017.
 */

public class StatResponse {
    @SerializedName("votes")
    @Expose
    int votes;
    @SerializedName("winner")
    @Expose
    int winner;
    @SerializedName("points")
    @Expose
    int points;
    @SerializedName("photo")
    @Expose
    String photo;

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
