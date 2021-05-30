package tw.edu.pu.weather;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class FavNewAdapter extends FirebaseRecyclerAdapter<Fav_Constructor, FavNewAdapter.FavViewHolder> {


    public FavNewAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Fav_Constructor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull FavViewHolder holder, int position, @NonNull @NotNull Fav_Constructor model) {
        int mainIcon = 0;
        int d1Icon = 0;
        int d2Icon = 0;
        int d3Icon = 0;

        if (model.getIconMain() != null && model.getIconD1() != null && model.getIconD2() != null && model.getIconD3() != null) {
            mainIcon = Integer.parseInt(model.getIconMain());
            d1Icon = Integer.parseInt(model.getIconD1());
            d2Icon = Integer.parseInt(model.getIconD2());
            d3Icon = Integer.parseInt(model.getIconD3());
        }

        //head
        holder.tempTextView.setText(model.getTemp());
        holder.cityTextView.setText(model.getCity());
        holder.countryTextView.setText(model.getCountry());
        holder.stateTextView.setText(model.getState());
        holder.iconMainView.setImageResource(mainIcon);

        //body
        holder.lhTempTextView.setText(model.getLowHigh());
        holder.flTempTextView.setText(model.getFeelLike());
        holder.humidTextView.setText(model.getHumid());
        holder.pressureTextView.setText(model.getPressure());
        holder.visibilityTextView.setText(model.getVisibility());
        holder.windTextView.setText(model.getWind());

        //leg
        holder.nowTempTextView.setText(model.getTempNow());
        holder.d1TempTextView.setText(model.getD1Temp());
        holder.d2TempTextView.setText(model.getD2Temp());
        holder.d3TempTextView.setText(model.getD3Temp());
        holder.d1DateTextView.setText(model.getD1Date());
        holder.d2DateTextView.setText(model.getD2Date());
        holder.d3DateTextView.setText(model.getD3Date());
        holder.iconNowView.setImageResource(mainIcon);
        holder.iconD1View.setImageResource(d1Icon);
        holder.iconD2View.setImageResource(d2Icon);
        holder.iconD3View.setImageResource(d3Icon);

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpanded(!model.isExpanded());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.cvMain.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext(), R.style.AlertDialog_AppCompat_Rounded)
                        .setTitle("Alert!")
                        .setMessage("Selected: " + model.getCity())
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = firebaseDatabase.getReference("data").child(model.getCity());
                                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                                        Toast.makeText(v.getContext(), "Remove Data Success!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            }
        });

        boolean isExpanded = model.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @NotNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
        return new FavViewHolder(view);
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cvMain;

        MaterialTextView tempTextView, cityTextView, countryTextView, stateTextView;

        MaterialTextView lhTempTextView, flTempTextView, humidTextView, pressureTextView,
                visibilityTextView, windTextView;

        MaterialTextView nowTempTextView, d1TempTextView, d2TempTextView, d3TempTextView;

        MaterialTextView d1DateTextView, d2DateTextView, d3DateTextView;

        ImageView iconMainView, iconNowView, iconD1View, iconD2View, iconD3View;

        ConstraintLayout expandableLayout;

        public FavViewHolder(@NonNull @NotNull View itemView) {
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
        }
    }
}
