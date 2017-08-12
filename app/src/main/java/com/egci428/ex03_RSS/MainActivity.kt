package com.egci428.ex03_RSS


import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.egci428.ex03_RSS.Adapter.FeedAdapter
import com.egci428.ex03_RSS.Common.HTTPDataHandler
import com.egci428.ex03_RSS.Model.RSSObject
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private val RSS_link = "http://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    private val RSS_to_JSON_API = " https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar.title = "NEWS"
        setSupportActionBar(toolBar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager

        loadRSS()


    }

    private fun loadRSS() {
        val loadRSSAsync = object: AsyncTask<String, String, String>() {

            override fun onPreExecute() {
                //Toast.makeText(this@MainActivity,"Please wait", Toast.LENGTH_SHORT).show()
            }

            override fun onPostExecute(result: String?) {
                var rssObject: RSSObject
                rssObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java)
                val adapter = FeedAdapter(rssObject, baseContext)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                Log.d("Tag","Load to Adapter")
            }

            override fun doInBackground(vararg params: String): String {
                val result: String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }

        }

        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh)
            loadRSS()
        return true
    }
}