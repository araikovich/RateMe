package araikovichinc.ratemeconcept2.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tigran on 20.11.2017.
 */

public class ServerResponse {
    @SerializedName("result")
    @Expose
    private int result;

    public int getResult(){
        return result;
    }
}
