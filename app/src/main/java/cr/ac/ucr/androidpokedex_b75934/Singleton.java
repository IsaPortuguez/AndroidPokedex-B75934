package cr.ac.ucr.androidpokedex_b75934;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton {

    public static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
