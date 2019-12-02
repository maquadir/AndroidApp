package com.example.androidapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    //global variables declarations
    private lateinit var listView: ListView;
    var actionbar_title: String = "";
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //listview of the main layout
        listView = findViewById<ListView>(R.id.list_view)

        //this method call fetches data from the json url
        fetchJsonData().execute()

    }

    //To create a menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //To handle click events on menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_refresh -> {
            // do stuff
            Log.d("MainActivity","List is Refreshed ")

            //this method call fetches data from the json url
            fetchJsonData().execute()

            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    //this is a class to fetch json data from a url, it has a list of methods that get implemented
    inner class fetchJsonData() : AsyncTask<String, Void, String>() {

        //this method gets called before the background operation
        override fun onPreExecute() {
            super.onPreExecute()

            //display a progress bar before background operation starts
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        //this method performs the background operation where it reads json text from the url
        override fun doInBackground(vararg params: String?): String? {
            return URL("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.js").readText(
                Charsets.UTF_8
            )
        }

        //this method gets called post background operation
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            //hide the progress bar on operation complete to display the list
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            //the json data is read and stored in a json object
            val jsonObj = JSONObject(result)

            //the title of the json is captured in actionbar_title string and set as action bar title
            actionbar_title = jsonObj.get("title").toString()
            getSupportActionBar()?.setTitle(actionbar_title);

            //the rows of the json array are read  and stored in an array list called dataList
            val rowsArr = jsonObj.getJSONArray("rows")
            Log.d("Fetched rows", rowsArr.toString())
            dataList.clear()
            for(i in 0 until rowsArr.length()){
                val singleUser = rowsArr.getJSONObject(i)

                //read values from the json and add it to the data list array
                val map = HashMap<String, String>()
                map["title"] = singleUser.getString("title")
                map["description"] = singleUser.getString("description")
                map["imageHref"] = singleUser.getString("imageHref")
                dataList.add(map)
            }

            //pass the data list to the adapter
            findViewById<ListView>(R.id.list_view).adapter = Adapter(this@MainActivity, dataList)
        }
    }
}
