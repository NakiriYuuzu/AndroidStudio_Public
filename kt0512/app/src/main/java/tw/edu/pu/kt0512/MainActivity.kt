package tw.edu.pu.kt0512

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.net.URLConnection
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var i = 0
    var images = arrayListOf<String>("https://source.unsplash.com/400x400?female,portrait",
        "https://source.unsplash.com/400x400?female,portrait","https://source.unsplash.com/400x400?female,portrait")
    inner class mytask: AsyncTask<String, String, Bitmap?>()
    {
        override fun doInBackground(vararg params: String?): Bitmap? {
            //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            var url: URL = URL(params[0])
            var myconn: HttpURLConnection =url.openConnection() as HttpURLConnection
            if ( myconn != null)
            {
                myconn.allowUserInteraction=false
                myconn.instanceFollowRedirects=true
                myconn.requestMethod="GET"
                myconn.connect()
                if (myconn.responseCode == HTTP_OK) {
                    var bmp:Bitmap = BitmapFactory.decodeStream(myconn.inputStream)
                    return  bmp
                }
            }
            return  null
        }
        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            var  imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(result)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mytask().execute( images[0])
        var btn = findViewById<Button>(R.id.btn)
        var wv = findViewById<WebView>(R.id.webView)
        wv.settings.javaScriptEnabled = true
        wv.loadUrl("https://www.pu.edu.tw/")
        btn.setOnClickListener(View.OnClickListener {
                mytask().execute(images[i])
                i++
            if (i <= 2) {
                i = 0;
            }
        })
    }
}