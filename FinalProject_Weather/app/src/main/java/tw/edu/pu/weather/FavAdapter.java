package tw.edu.pu.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavVH> {

    ArrayList<Fav_Constructor> favList;

    public FavAdapter(ArrayList<Fav_Constructor> favList) {
        this.favList = favList;
    }

    @NonNull
    @NotNull
    @Override
    public FavVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
        return new FavVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavVH holder, int position) {
        Fav_Constructor favC = favList.get(position);
        int mainIcon = 0;
        int d1Icon = 0;
        int d2Icon = 0;
        int d3Icon = 0;

        if (favC.getIconMain() != null && favC.getIconD1() != null && favC.getIconD2() != null && favC.getIconD3() != null) {
            mainIcon = Integer.parseInt(favC.getIconMain());
            d1Icon = Integer.parseInt(favC.getIconD1());
            d2Icon = Integer.parseInt(favC.getIconD2());
            d3Icon = Integer.parseInt(favC.getIconD3());
        }

        //head
        holder.tempTextView.setText(favC.getTemp());
        holder.cityTextView.setText(favC.getCity());
        holder.countryTextView.setText(favC.getCountry());
        holder.stateTextView.setText(favC.getState());
        holder.iconMainView.setImageResource(mainIcon);

        //body
        holder.lhTempTextView.setText(favC.getLowHigh());
        holder.flTempTextView.setText(favC.getFeelLike());
        holder.humidTextView.setText(favC.getHumid());
        holder.pressureTextView.setText(favC.getPressure());
        holder.visibilityTextView.setText(favC.getVisibility());
        holder.windTextView.setText(favC.getWind());

        //leg
        holder.nowTempTextView.setText(favC.getTempNow());
        holder.d1TempTextView.setText(favC.getD1Temp());
        holder.d2TempTextView.setText(favC.getD2Temp());
        holder.d3TempTextView.setText(favC.getD3Temp());
        holder.d1DateTextView.setText(favC.getD1Date());
        holder.d2DateTextView.setText(favC.getD2Date());
        holder.d3DateTextView.setText(favC.getD3Date());
        holder.iconNowView.setImageResource(mainIcon);
        holder.iconD1View.setImageResource(d1Icon);
        holder.iconD2View.setImageResource(d2Icon);
        holder.iconD3View.setImageResource(d3Icon);

        boolean isExpanded = favList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        if (favList != null) {
            return favList.size();
        } else {
            return 0;
        }
    }

    public class FavVH extends RecyclerView.ViewHolder {

        MaterialCardView cvMain;

        MaterialTextView tempTextView, cityTextView, countryTextView, stateTextView;

        MaterialTextView lhTempTextView, flTempTextView, humidTextView, pressureTextView,
                visibilityTextView, windTextView;

        MaterialTextView nowTempTextView, d1TempTextView, d2TempTextView, d3TempTextView;

        MaterialTextView d1DateTextView, d2DateTextView, d3DateTextView;

        ImageView iconMainView, iconNowView, iconD1View, iconD2View, iconD3View;

        ConstraintLayout expandableLayout;

        public FavVH(@NonNull @NotNull View itemView) {
            super(itemView);

            cvMain = itemView.findViewById(R.id.cv_FavMain);
            tempTextView = itemView.findViewById(R.id.tv_FavTemperature);
            cityTextView = itemView.findViewById(R.id.tv_FavCity);
            countryTextView = itemView.findViewById(R.id.tv_FavCountry);
            iconMainView = itemView.findViewById(R.id.iv_FavIconMain);
            stateTextView = itemView.findViewById(R.id.tv_FavState);

            //text view body
            lhTempTextView = itemView.findViewById(R.id.tv_Fav_HighLow);
            flTempTextView = itemView.findViewById(R.id.tv_Fav_FeelLike);
            humidTextView = itemView.findViewById(R.id.tv_Fav_Humid);
            pressureTextView = itemView.findViewById(R.id.tv_Fav_Pressure);
            visibilityTextView = itemView.findViewById(R.id.tv_Fav_Visibility);
            windTextView = itemView.findViewById(R.id.tv_Fav_Wind);

            //text view leg
            nowTempTextView = itemView.findViewById(R.id.tv_Fav_TempNow);
            d1TempTextView = itemView.findViewById(R.id.tv_Fav_TempD1);
            d2TempTextView = itemView.findViewById(R.id.tv_Fav_TempD2);
            d3TempTextView = itemView.findViewById(R.id.tv_Fav_TempD3);

            d1DateTextView = itemView.findViewById(R.id.tv_Fav_DateD1);
            d2DateTextView = itemView.findViewById(R.id.tv_Fav_DateD2);
            d3DateTextView = itemView.findViewById(R.id.tv_Fav_DateD3);

            //image view
            iconNowView = itemView.findViewById(R.id.fav_IconNow);
            iconD1View = itemView.findViewById(R.id.fav_IconD1);
            iconD2View = itemView.findViewById(R.id.fav_IconD2);
            iconD3View = itemView.findViewById(R.id.fav_IconD3);

            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fav_Constructor favC = favList.get(getAdapterPosition());
                    favC.setExpanded(!favC.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
