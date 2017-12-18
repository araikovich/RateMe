package araikovichinc.ratemeconcept2.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tigran on 14.12.2017.
 */

public class Image {
    @SerializedName("imageUrl")
    @Expose
    String imageUrl;
    @SerializedName("_id")
    @Expose
    int id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
