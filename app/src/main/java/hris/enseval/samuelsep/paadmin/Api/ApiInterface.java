package hris.enseval.samuelsep.paadmin.Api;

import java.util.List;

import hris.enseval.samuelsep.paadmin.Model.TokenAuthModel.UserLogin;
import hris.enseval.samuelsep.paadmin.Model.TokenAuthModel.UserLoginResponse;
import hris.enseval.samuelsep.paadmin.Model.User.User;
import hris.enseval.samuelsep.paadmin.Model.User.userBodyParameter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    //================================================ GET TOKEN =======================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);
    //==================================================================================================================================

    //================================================= TRAINING API ===================================================================
    //===========================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    //@GET("User/1")
    @HTTP(method = "POST", path = "User", hasBody = true)
    Call<User> getUserDetail(@Body userBodyParameter userId, @Header("Authorization") String auth);
    //===========================================================
    //===========================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Call<List<Home>> getAllHomeNews(@Path("NIK") String nik, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}/{id}")
//    Call<List<Home>> getHomeNews(@Path("NIK") String NIK, @Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("eventPeserta")
//    Call<EventUser> postUserEvents(@Body EventUser user_events, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("QuestionAnswer/{id}")
//    Call<QuestionAnswerResponse> getQuestionAnswerSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //=============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("survey/{id}")
//    Call<Survey> getSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("eventSession/{id}/{nik}")
//    Call<List<EventSession>> getEventSessionID(@Path("id") String id, @Path("nik") String nik, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("eventPesertaAbsen")
//    Call<EventAbsentUser>postEventAbsentUser(@Body EventAbsentUser eventAbsentUser, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("eventPesertaAbsen/{id}")
//    Call<List<EventAbsentUser>>getEventAbsentUserID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("surveyAnswerPeserta")
//    Call<List<UserAnswer>>postUserAnswer(@Body List<UserAnswer> lUserAnswer, @Header("Authorization") String auth);
    //============================================================
    //==================================================================================================================================

    //==================================================== PK API ======================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Call<List<KPIApproveList>> getEmpPJList(@Path("NIK") String nik, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Call<KPIHeaderPJ> getKPIHeaderPJ(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("event")
//    Call<KPIUserAnswerListPJ> postKPIAnswerPJ(@Body KPIUserAnswerListPJ kpiUserAnswerListPJ, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================

    //=============================================== PA TAHUNAN API ===================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Call<List<KPIApproveList>> getEmpList(@Path("NIK") String nik, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Call<KPIHeader> getKPIHeader(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("event")
//    Call<KPIUserAnswerList> postKPIAnswer(@Body KPIUserAnswerList kpiUserAnswerList, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================



//======================================================================================
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("chat")
    Call<String> postWhasappBlast(@Query("token") String token, @Query("uid") String uid, @Query("to") String nomor,@Query("custom_uid") String msgId, @Query("text") String message);
}
