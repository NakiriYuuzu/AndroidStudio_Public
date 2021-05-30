package tw.edu.pu.weather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;

public class HomeFragment extends Fragment {

    private ShareViewModel_Home viewModel;

    MaterialTextView tvState, tvTemp, tvCity, tvCountry, tvTempNow;
    MaterialTextView tvFeelLike, tvMinMaxTemp, tvHumid, tvPressure, tvVisibility, tvWinds, tvTestingIcon;

    //d5 data
    MaterialTextView tvTempD1, tvTempD2, tvTempD3, tvDateD1, tvDateD2, tvDateD3;

    ImageView ivIcon, iv_d5IconNow, iv_d5Icon1, iv_d5Icon2, iv_d5Icon3;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //FindView
        tvState = view.findViewById(R.id.tv_State);
        tvTemp = view.findViewById(R.id.tv_Temperature);
        tvCity = view.findViewById(R.id.tv_City);
        tvCountry = view.findViewById(R.id.tv_country);
        ivIcon = view.findViewById(R.id.Iv_Icon);
        tvFeelLike = view.findViewById(R.id.tv_feelLikeTemp);
        tvMinMaxTemp = view.findViewById(R.id.tv_highAndLow);
        tvHumid = view.findViewById(R.id.tv_Humid);
        tvPressure = view.findViewById(R.id.tv_pressure);
        tvVisibility = view.findViewById(R.id.tv_visibility);
        tvWinds = view.findViewById(R.id.tv_wind);
        tvTempNow = view.findViewById(R.id.tv_TempNow);
        tvTestingIcon = view.findViewById(R.id.testing_Icon);

        //day 5
        tvTempD1 = view.findViewById(R.id.tv_TempFirstDay);
        tvTempD2 = view.findViewById(R.id.tv_TempSecondDay);
        tvTempD3 = view.findViewById(R.id.tv_TempThirdDay);
        iv_d5IconNow = view.findViewById(R.id.iv_Icon_Now);
        iv_d5Icon1 = view.findViewById(R.id.iv_Icon_1);
        iv_d5Icon2 = view.findViewById(R.id.iv_Icon_2);
        iv_d5Icon3 = view.findViewById(R.id.iv_Icon_3);
        tvDateD1 = view.findViewById(R.id.tv_date1);
        tvDateD2 = view.findViewById(R.id.tv_date2);
        tvDateD3 = view.findViewById(R.id.tv_date3);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mvvm Setup
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel_Home.class);

        //state data
        viewModel.getState().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvState.setText(s);
            }
        });

        //city data
        viewModel.getCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvCity.setText(s);
            }
        });

        //Country data
        viewModel.getCountry().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvCountry.setText(s);
            }
        });

        //temp data
        viewModel.getTemp().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTemp.setText(s);
            }
        });

        //feel like data
        viewModel.getFeelLike().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvFeelLike.setText(s);
            }
        });

        //minMaxTemp data
        viewModel.getMinMaxTemp().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvMinMaxTemp.setText(s);
            }
        });

        //Humid data
        viewModel.getHumid().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvHumid.setText(s);
            }
        });

        //Pressure data
        viewModel.getPressure().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvPressure.setText(s);
            }
        });

        //Visibility data
        viewModel.getVisibility().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvVisibility.setText(s);
            }
        });

        //Winds data
        viewModel.getWind().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvWinds.setText(s);
            }
        });

        viewModel.getTempNow().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTempNow.setText(s);
            }
        });

        //IconData
        viewModel.getIcon().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ivIcon.setImageResource(integer);
            }
        });

        //Day 5 data
        viewModel.getTempD1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTempD1.setText(s);
            }
        });

        viewModel.getTempD2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTempD2.setText(s);
            }
        });

        viewModel.getTempD3().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String ss) {
                tvTempD3.setText(ss);
            }
        });

        //icon
        viewModel.getIconNow().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iv_d5IconNow.setImageResource(integer);
            }
        });

        viewModel.getIconD1().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iv_d5Icon1.setImageResource(integer);
            }
        });

        viewModel.getIconD2().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iv_d5Icon2.setImageResource(integer);
            }
        });

        viewModel.getIconD3().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                iv_d5Icon3.setImageResource(integer);
            }
        });

        //date
        viewModel.getDateD1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvDateD1.setText(s);
            }
        });

        viewModel.getDateD2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvDateD2.setText(s);
            }
        });

        viewModel.getDateD3().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvDateD3.setText(s);
            }
        });
    }
}