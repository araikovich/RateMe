package araikovichinc.ratemeconcept2.Utils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tigran on 14.12.2017.
 */

public class Images {
    @SerializedName("result")
    ArrayList<Image> images;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
