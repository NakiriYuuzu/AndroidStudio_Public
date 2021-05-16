package tw.edu.pu.kotlin0414

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.toDegrees
class MainActivity : AppCompatActivity() , SensorEventListener {
    lateinit var sm:SensorManager
    lateinit var acc: Sensor
    lateinit var mag: Sensor
    lateinit var tv:TextView
    lateinit var image: ImageView
    private var accvalue:FloatArray= FloatArray(3)
    private var magvalue:FloatArray= FloatArray(3)
    var haveAcc:Boolean=false
    var haveMag:Boolean=false
    var curr : Float= 0.0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sm=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mag=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        tv=findViewById<TextView>(R.id.text)as TextView
        image = findViewById<ImageView>(R.id.imageView3) as ImageView
    }
    override fun onResume() {
//will be executed onResume
        super.onResume()
        sm.registerListener(this,acc,SensorManager.SENSOR_DELAY_NORMAL)
        sm.registerListener(this,mag,SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
//will be executed onResume
        sm.unregisterListener(this,acc)
        sm.unregisterListener(this,mag)
    }
    override fun onSensorChanged(event: SensorEvent?) {
// TODO("Not yet implemented")
        when (event!!.sensor){
            acc->{
                accvalue[0]= event!!.values[0]
                accvalue[1]= event!!.values[1]
                accvalue[2]= event!!.values[2]
                haveAcc=true
            }
            mag->{
                magvalue[0]= event!!.values[0]
                magvalue[1]= event!!.values[1]
                magvalue[2]= event!!.values[2]
                haveMag=true
            }
        }
        if ( haveAcc && haveMag){
            var R=FloatArray(9)
            SensorManager.getRotationMatrix(R,null, accvalue, magvalue )
            var device=FloatArray(3)
            SensorManager.getOrientation(R,device )
            var rot=(toDegrees(device[0].toDouble()).toFloat()+360)%360
            tv.text=rot.toString()
            val rotateAnimation=RotateAnimation (curr,
                rot,
                RELATIVE_TO_SELF,
                0.5f,
                RELATIVE_TO_SELF,
                0.5f)
            rotateAnimation.duration = 1000
            rotateAnimation.fillAfter = true
            image.startAnimation(rotateAnimation)
            curr= -rot
            haveAcc=false
            haveMag=false
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
// TODO("Not yet implemented")
    }
}