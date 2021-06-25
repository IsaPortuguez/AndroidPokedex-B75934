package cr.ac.ucr.androidpokedex_b75934;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cr.ac.ucr.androidpokedex_b75934.apipokedex.ApiPokedexServices;
import cr.ac.ucr.androidpokedex_b75934.models.PokedexAnswer;
import cr.ac.ucr.androidpokedex_b75934.models.Pokemon;
import cr.ac.ucr.androidpokedex_b75934.models.PokemonDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowPokemonInformationActivity extends AppCompatActivity {

    private ImageView pokemonImage;
    private TextView pokemonId;
    private TextView pokemonName;
    private TextView pokemonHeight;
    private TextView pokemonWeight;
    private TextView pokemonBaseExperience;
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pokemon_information);

        context = this;

        pokemonImage = (ImageView) findViewById(R.id.specificPokemonImage);
        pokemonId = (TextView) findViewById(R.id.specificPokemonId);
        pokemonName = (TextView) findViewById(R.id.specificPokemonName);
        pokemonHeight = (TextView) findViewById(R.id.specificPokemonHeight);
        pokemonWeight = (TextView) findViewById(R.id.specificPokemonWeight);
        pokemonBaseExperience = (TextView) findViewById(R.id.specificPokemonBaseExperience);
        retrofit = Singleton.getRetrofit();

        Bundle data = getIntent().getExtras();
        int id = data.getInt("Id");

        ApiPokedexServices api = retrofit.create(ApiPokedexServices.class);

        Call<PokemonDetails> pokemonsdetails= api.getPokemonDetails(id);
        pokemonsdetails.enqueue(new Callback<PokemonDetails>() {

            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if(response.isSuccessful()){
                    PokemonDetails pokemonDetails = response.body();
                    Glide.with(context)
                            .load(pokemonDetails.sprites.getFront_default())
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(pokemonImage);
                    pokemonId.setText(String.valueOf(pokemonDetails.getId()));
                    pokemonName.setText(pokemonDetails.getName());
                    pokemonHeight.setText(String.valueOf(pokemonDetails.getHeight()));
                    pokemonWeight.setText(String.valueOf(pokemonDetails.getWeight()));
                    pokemonBaseExperience.setText(String.valueOf(pokemonDetails.getBase_experience()));
                }else{
                    Log.e(TAG," onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                Log.e(TAG," onFailure: "+ t.getMessage());
            }
        });

    }
}