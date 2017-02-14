package example.com.teamc;

import android.content.Context;

import example.com.teamc.resp.Range;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ayijk on 2017/02/14.
 */

public class EkiSpertCommunitator {
    private final EkiSpertService service;
    private final String key;

    public EkiSpertCommunitator(Context context) {
        this.key = context.getString(R.string.ekispert_key);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ekispert.jp/v1/json/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(EkiSpertService.class);
    }

    public void range(int upperLimit, String name, int limit, final RangeListener listener) {
        service.range(key, upperLimit, name, limit).enqueue(new Callback<Range>() {
            @Override
            public void onResponse(Call<Range> call, Response<Range> response) {
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Range> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface RangeListener {
        void onResponse(Range range);

        void onFailure(Throwable throwable);
    }

    public void course_plain(String name, String to, final CoursePlainListener listener) {
        service.course_plain(key, name, to).enqueue(new Callback<Range>() {
            @Override
            public void onResponse(Call<Range> call, Response<Range> response) {
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<Range> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface CoursePlainListener {
        void onResponse(Range range);

        void onFailure(Throwable throwable);
    }

    private interface EkiSpertService {
        @GET("range")
        Call<Range> range(@Query("key") String key, @Query("upperLimit") int upperLimit,
                          @Query("name") String name, @Query("limit") int limit);

        @GET("course/plain")
        Call<Range> course_plain(@Query("key") String key, @Query("from") String from, @Query("to") String to);
    }
}