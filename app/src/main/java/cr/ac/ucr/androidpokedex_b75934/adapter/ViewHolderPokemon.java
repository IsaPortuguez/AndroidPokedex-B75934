package cr.ac.ucr.androidpokedex_b75934.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import cr.ac.ucr.androidpokedex_b75934.R;
import cr.ac.ucr.androidpokedex_b75934.ShowPokemonInformationActivity;

public class ViewHolderPokemon extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView photoImageView;
    public TextView nameTextView;
    private int id;

    public ViewHolderPokemon(View itemView) {
        super(itemView);

        photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
        nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ShowPokemonInformationActivity.class);
        intent.putExtra("Id",this.id);
        v.getContext().startActivity(intent);
    }

    public void setOnClickListener(int Id) {
        this.id = Id;
        photoImageView.setOnClickListener(this);
    }
}
