package tw.edu.pu.guessnumber_kl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    //
    var ans = "1234"
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        var mBtnGuess = findViewById<MaterialButton>(R.id.mBtnGuess)
        var mEdText = findViewById<TextInputEditText>(R.id.mEdText)
        var mTvText = findViewById<MaterialTextView>(R.id.mTvText)
        var usrName = findViewById<TextView>(R.id.usrName)
        //
        var value = intent.extras?.getString("user")
        usrName.setText("Welcome: " + value)
        //
        mBtnGuess.setOnClickListener {
            var guess = mEdText.text.toString()
            var nA = 0;
            var nB = 0;
            //
            for (i in 0..3) {
                for (j in 0..3) {
                    if (ans.get(i) == guess.get(j)) {
                        if (i == j)
                            nA++
                        else
                            nB++
                    }
                }
            }
            mTvText.text = nA.toString() + "A" + nB.toString() + "B"
        }
    }
}