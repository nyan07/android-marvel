package br.com.nyan.marvel.characterlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.nyan.marvel.R;
import br.com.nyan.marvel.characterdetail.CharacterDetailActivity;
import br.com.nyan.marvel.characterdetail.CharacterDetailFragment;
import br.com.nyan.marvel.data.Character;
import br.com.nyan.marvel.data.CharacterService;
import br.com.nyan.marvel.ui.EndlessRecyclerViewScrollListener;
import br.com.nyan.marvel.ui.GridSpacingItemDecoration;

public class CharacterListActivity extends AppCompatActivity
        implements CharacterAdapter.OnCharacterClickListener, CharacterService.OnDataSetChangedListener
{
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private CharacterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.character_list);
        assert recyclerView != null;
        mRecyclerView = (RecyclerView) recyclerView;

        if (findViewById(R.id.character_detail_container) != null)
        {
            mTwoPane = true;
        }

        setupRecyclerView();

        CharacterService.getInstance().setOnDataSetChangedListener(this);
        CharacterService.getInstance().loadCharacters();
    }

    private void setupRecyclerView()
    {
        int columns = 2;
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(columns, spacingInPixels, true));

        mAdapter = new CharacterAdapter(CharacterService.getInstance().getCharacters(), this);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(layoutManager);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager)
        {
            @Override
            public void onLoadMore(RecyclerView view)
            {
                loadData();
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);
    }

    private void loadData()
    {
        CharacterService.getInstance().loadCharacters();
    }

    @Override
    protected void onDestroy()
    {
        CharacterService.getInstance().setOnDataSetChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void OnDataSetChanged()
    {
        mAdapter.notifyDataSetChanged();
    }

    public void onClick(Character character)
    {
        if (mTwoPane)
        {
            Bundle arguments = new Bundle();
            arguments.putString(CharacterDetailFragment.ARG_ITEM_ID, String.valueOf(character.getId()));
            CharacterDetailFragment fragment = new CharacterDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.character_detail_container, fragment)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(this, CharacterDetailActivity.class);
            intent.putExtra(CharacterDetailFragment.ARG_ITEM_ID, String.valueOf(character.getId()));
            startActivity(intent);
        }
    }
}
