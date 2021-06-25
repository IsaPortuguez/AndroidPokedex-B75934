package cr.ac.ucr.androidpokedex_b75934.apipokedex;

import cr.ac.ucr.androidpokedex_b75934.models.PokedexAnswer;
import cr.ac.ucr.androidpokedex_b75934.models.PokemonDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiPokedexServices {

    @GET("pokemon")
    Call<PokedexAnswer> getPokemonList(@Query("limit") int limit,@Query("offset") int offset);

    @GET("pokemon/{Id}")
    Call<PokemonDetails> getPokemonDetails(@Path("Id") int Id);

}
