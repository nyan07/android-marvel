package br.com.nyan.marvel.characterlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.nyan.marvel.R;
import br.com.nyan.marvel.data.Character;

class CharacterViewHolder extends RecyclerView.ViewHolder
{
    public final View mView;
    public final TextView mNameView;
    public final ImageView mImageView;
    public Character mItem;

    public CharacterViewHolder(View view)
    {
        super(view);
        mView = view;
        mNameView = (TextView) view.findViewById(R.id.name);
        mImageView = (ImageView) view.findViewById(R.id.picture);
    }

    @Override
    public String toString()
    {
        return super.toString() + " '" + mNameView.getText() + "'";
    }
}