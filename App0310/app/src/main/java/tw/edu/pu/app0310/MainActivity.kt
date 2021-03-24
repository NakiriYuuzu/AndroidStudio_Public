package tw.edu.pu.app0310

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        fun MaterialButton.mytext(s: String) {
            this.text = s;
        }
        //
        var mButton: MaterialButton = findViewById(R.id.btnTest01)
        var mTextVi: MaterialTextView = findViewById(R.id.tvTest)
        var edText:  TextInputEditText = findViewById(R.id.edText01)
        //
        mButton.mytext("Click Me")
        //
        mButton.setOnClickListener {
            Toast.makeText(this, "Hi, 蔡淇鴻", Toast.LENGTH_LONG).show()
            //
            var str = edText.text.toString()
            //
            mTextVi.text = str
        }
    }
}


