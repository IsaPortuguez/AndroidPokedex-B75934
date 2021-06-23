package cr.ac.ucr.androidpokedex_b75934;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.androidpokedex_b75934.apipokedex.ApiPokedexServices;
import cr.ac.ucr.androidpokedex_b75934.models.PokedexAnswer;
import cr.ac.ucr.androidpokedex_b75934.models.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private int offset;
    private boolean suitableForCharging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(suitableForCharging){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG, "Llegamos al final.");
                            suitableForCharging = false;
                            offset += 20;
                            getInformation(offset);
                        }
                    }

                }

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        suitableForCharging = true;
        offset = 0;
        getInformation(offset);
    }

    private void getInformation(int offset){

        ApiPokedexServices service = retrofit.create(ApiPokedexServices.class);
        Call<PokedexAnswer> pokemonAnswerCall = service.getPokemonList(20,offset);

        pokemonAnswerCall.enqueue(new Callback<PokedexAnswer>() {
            @Override
            public void onResponse(Call<PokedexAnswer> call, Response<PokedexAnswer> response) {
                suitableForCharging = true;
                if(response.isSuccessful()){
                    PokedexAnswer pokedexAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = (ArrayList<Pokemon>) pokedexAnswer.getResults();

                    pokemonListAdapter.addPokemonList(pokemonList);
                }else{
                    Log.e(TAG," onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokedexAnswer> call, Throwable t) {
                suitableForCharging = true;
                Log.e(TAG," onFailure: "+ t.getMessage());
            }
        });

    }
}