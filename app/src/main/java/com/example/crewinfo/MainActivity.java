package com.example.crewinfo;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.crewinfo.Adapter.CrewAdapter;
import com.example.crewinfo.RoomArchitecture.CrewEntity;
import com.example.crewinfo.RoomArchitecture.CrewViewModel;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements CrewAdapter.OnCrewClicked {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CrewAdapter mAdapter;
    private CrewViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setRecyclerView();
        setViewModel();
        eventHandler();
        swipeLeftDeleteFunction();

    }

    private void swipeLeftDeleteFunction() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                CrewEntity entity = (CrewEntity) viewHolder.itemView.getTag();
                viewModel.delete(entity);
//                mAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void eventHandler() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.swipeRefreshing();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            viewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CrewViewModel.class);
        viewModel.getAllCrews().observe(this, crewEntities -> {
//            Log.d("CREW_INFO", "onChanged: updated size = "+crewEntities.size());
            if (crewEntities != null)
                mAdapter.setCrewInfo(crewEntities);
        });
        Log.d("CREW_INFO", "setViewModel: ");
        viewModel.fetchJSONData();
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CrewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initialization() {
        mRecyclerView = findViewById(R.id.rvCrew);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
    }

    @Override
    public void OnItemClicked(CrewEntity entity) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(entity.getWikipedia()));
    }

    @Override
    public void OndeleteCrew(CrewEntity entity) {
        mAdapter.notifyDataSetChanged();
        viewModel.delete(entity);

    }
}