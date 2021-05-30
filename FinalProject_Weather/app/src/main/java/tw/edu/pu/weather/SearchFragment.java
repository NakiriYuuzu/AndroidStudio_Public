package tw.edu.pu.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {

    final String forest_Url = "https://api.openweathermap.org/data/2.5/forecast?";
    final String key_API = "&appid=e9bead3ce6662fb07b5f377564f90b93";
    String searchEach = "&cnt=26";

    public interface onButtonPressListener {
        void onOKButtonPressed();
    }
    //FindView
    View view;
    MaterialTextView tvSearch;
    MaterialButton btnSearch;
    TextInputEditText etSearch;
    RecyclerView recyclerView;

    //Constructor
    String ioCity;

    //Class
    ShareViewModel_Search viewModel;

    private onButtonPressListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        //findView
        btnSearch = view.findViewById(R.id.btnSearch);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.recycleView);
        tvSearch = view.findViewById(R.id.textView2);

        //

        return view;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            mListener = (onButtonPressListener) getActivity();

        }catch (ClassCastException e) {
            throw new ClassCastException(String.valueOf(getActivity()));
        }
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel_Search.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOKButtonPressed();

                ioCity = String.valueOf(etSearch.getText());
                if (ioCity.matches("")) {
                    Toast.makeText(requireActivity(), "Please input the city!", Toast.LENGTH_SHORT).show();
                }
                else {
                    VolleyApi_Search volleyApi_search = new VolleyApi_Search(forest_Url + "q=" + ioCity + searchEach + key_API, requireActivity());
                    volleyApi_search.getWeatherSearchD5();
                    viewModel.setIoCity(ioCity);
                }
            }
        });
    }
}