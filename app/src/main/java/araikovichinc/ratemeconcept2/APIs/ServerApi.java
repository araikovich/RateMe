package araikovichinc.ratemeconcept2.APIs;

import araikovichinc.ratemeconcept2.Utils.Images;
import araikovichinc.ratemeconcept2.Utils.ServerResponse;
import araikovichinc.ratemeconcept2.Utils.StatResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Tigran on 16.12.2017.
 */

public interface ServerApi {
    @FormUrlEncoded
    @POST("/RateMe/getPhotos.php")
    Call<Images> getPhotos(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("/RateMe/sign.up.php")
    Call<ServerResponse> addUser(@Field("name") String userName, @Field("email") String email, @Field("password") String userPass);

    @FormUrlEncoded
    @POST("/RateMe/log.in.php")
    Call<ServerResponse> logIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/RateMe/uploadPhoto.php")
    Call<ServerResponse> save(@Field("image_path") String photo, @Field("userId") int userId, @Field("question") String question);

    @FormUrlEncoded
    @POST("/RateMe/GetStatProfile.php")
    Call<StatResponse> getStat(@Field("user_id") int userId);
}
