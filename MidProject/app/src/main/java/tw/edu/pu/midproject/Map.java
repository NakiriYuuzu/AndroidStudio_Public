package tw.edu.pu.midproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Map extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAddr;
    private TextView tvLoca;
    private Button btn;

    private void findView() {
        tvName = findViewById(R.id.tvSpotName);
        tvAddr = findViewById(R.id.tvSpotAddr);
        tvLoca = findViewById(R.id.tvSpotLoca);
        btn = findViewById(R.id.button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        findView();

        Bundle bundle = this.getIntent().getExtras();

        String getNames = bundle.getString("name");
        String getAddress = bundle.getString("address");
        String getLocation = bundle.getString("location");

        tvName.setText(getNames);
        tvAddr.setText(getAddress);
        tvLoca.setText(getLocation);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}