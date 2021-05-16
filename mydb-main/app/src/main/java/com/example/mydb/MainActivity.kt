package com.example.mydb

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.AsyncTask.execute
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    //var dbhelper : MyDBHelper =MyDBHelper(this);
    lateinit var dbhelper: UserDao
    lateinit var db: MyRoom
    var userid: EditText? = null
    var name: EditText? = null
    var age: EditText? = null
    var resultv: TextView? = null
    var listv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            db = Room.databaseBuilder(
                applicationContext,
                MyRoom::class.java, "usermodel"
            ).build()
            dbhelper = db.userDao()
            var user = UserModel("123", "James", "20")
            dbhelper.insertUser(user)
        } catch (err: Exception) {
            println(err)
        }
        userid = findViewById(R.id.userid)
        name = findViewById(R.id.name)
        age = findViewById(R.id.age)
        resultv = findViewById(R.id.result)
        listv = findViewById(R.id.listview)
        var b1 = findViewById<Button>(R.id.button1)
        var b2 = findViewById<Button>(R.id.button2)
        var b3 = findViewById<Button>(R.id.button3)
        b1.setOnClickListener { this.addUser() }
        b2.setOnClickListener { this.deleteUser() }
        b3.setOnClickListener { this.showAllUsers() }
    }

    fun addUser() {
        var userid = this.userid?.text.toString()
        var name = this.name?.text.toString()
        var age = this.age?.text.toString()
        Thread {
            var result = dbhelper.insertUser(UserModel(userid = userid, name = name, age = age))
        }.start()
//clear all edittext s
        this.age?.setText("")
        this.name?.setText("")
        this.userid?.setText("")
// this.resultv?.text = "Added user : "+result
        this.listv?.text = ""
    }

    fun deleteUser() {
        var userid = this.userid?.text.toString()
        Thread {
            val result = dbhelper.deleteUser(UserModel(userid, "", ""))
        }.start()
// this.resultv?.text = "Deleted user : "+result
        this.listv?.text = ""
    }

    fun showAllUsers() {
        var tv_user = ""
        Thread {
            var users = dbhelper.readAllUsers()
// this.ll_entries.removeAllViews()
            users.forEach {
                tv_user += it.name + ","
//tv_user = it.name.toString() + " - " + it.age.toString()
            }
            runOnUiThread {
                this.listv?.text = tv_user
            }
        }.start()
// this.resultv?.text = "Fetched " + users.size + " users"
    }
}