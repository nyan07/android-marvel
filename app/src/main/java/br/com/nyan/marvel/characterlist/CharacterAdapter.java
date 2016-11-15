package br.com.nyan.marvel.characterlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.nyan.marvel.R;
import br.com.nyan.marvel.data.Character;

class CharacterAdapter
        extends RecyclerView.Adapter<CharacterViewHolder>
{
    private final List<Character> mValues;
    private OnCharacterClickListener mListener;

    public CharacterAdapter(List<Character> items, OnCharacterClickListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_content, parent, false);

        CharacterViewHolder vh = new CharacterViewHolder(view);

        int height = parent.getMeasuredHeight() / 3;
        vh.mImageView.setMinimumHeight(height);

        return vh;
    }

    @Override
    public void onBindViewHolder(final CharacterViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());

        Context context = holder.itemView.getContext();

        Picasso.with(context)
                .load(holder.mItem.getThumbnail().getSmall())
                .tag(context)
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mListener != null)
                {
                    mListener.onClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public interface OnCharacterClickListener
    {
        void onClick(Character character);
    }
}