package br.com.nyan.marvel.characterdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.nyan.marvel.R;
import br.com.nyan.marvel.data.Character;
import br.com.nyan.marvel.data.CharacterService;

public class CharacterDetailFragment extends Fragment
{

    public static final String ARG_ITEM_ID = "item_id";
    private Character mItem;

    public CharacterDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            String id = getArguments().getString(ARG_ITEM_ID);
            mItem = CharacterService.getInstance().getCharacter(id);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getName());

                ImageView imageView = (ImageView) appBarLayout.findViewById(R.id.picture);
                Picasso.with(getContext()).load(mItem.getThumbnail().getLarge()).into(imageView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.character_detail, container, false);

        if (mItem != null)
        {
            String text = mItem.getDescription();
            if (text == null || text.isEmpty())
            {
                text = getResources().getString(R.string.no_description);
            }

            ((TextView) rootView.findViewById(R.id.character_detail)).setText(text);
        }

        return rootView;
    }
}