package tw.edu.pu.kt0428

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    var db: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            db = this.openOrCreateDatabase("mydb", MODE_PRIVATE, null)
            //db?.execSQL("create table tblAMIGO(" +
            //        "recID integer PRIMARY KEY  autoincrement," +
            //        "name text," +
            //        "phone text );")
            db?.execSQL("insert into tblAMIGO(name, phone) values ('AAA', '555' );")
            val myCur: Cursor? = db?.rawQuery("select * from tblAMIGO", null)
            val idCol= myCur?.getColumnIndex("recID")
            val nameCol= myCur?.getColumnIndex("name")
            val phoneCol= myCur?.getColumnIndex("phone")
            var txtMsg=""
            while(myCur?.moveToNext()!!) {
                val v1 = ((myCur.getInt(idCol!!))).toString();
                val v2 = myCur.getString(nameCol!!);
                val v3 = myCur.getString(phoneCol!!);
                txtMsg+=("\n"+ v1 + " "
                        + v2 + " "
                        + v3 );
            }
            val tv=findViewById<TextView>(R.id.tv1)
            tv.text = txtMsg
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        }catch (e: SQLiteException){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
