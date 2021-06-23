package cr.ac.ucr.androidpokedex_b75934;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cr.ac.ucr.androidpokedex_b75934.models.Pokemon;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonListAdapter(Context context){

        this.context = context;
        dataset = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Pokemon p = dataset.get(position);
        holder.nameTextView.setText(p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);

    }

    @Override
    public int getItemCount() {

        return dataset.size();

    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList){

        dataset.addAll(pokemonList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoImageView;
        private TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }
    }

}
