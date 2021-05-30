package tw.edu.pu.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


import org.jetbrains.annotations.NotNull;



public class FavoriteFragment extends Fragment {

    //findView
    View view;
    RecyclerView recyclerView;
    FavNewAdapter favNewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //findView
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.Fav_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Fav_Constructor> options = new FirebaseRecyclerOptions.Builder<Fav_Constructor>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("data"), Fav_Constructor.class)
                .build();

        favNewAdapter = new FavNewAdapter(options);
        recyclerView.setAdapter(favNewAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        favNewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        favNewAdapter.stopListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        favNewAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        favNewAdapter.startListening();
    }
}