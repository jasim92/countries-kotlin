package com.example.kotlinlearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearn.viewModel.MyViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var adapter: MyAdapter
    private lateinit var rc: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rc = findViewById(R.id.profile_rc)
        rc.layoutManager = LinearLayoutManager(this)



        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        viewModel.fetchDataFromApi(
            onDataLoaded = { data ->
                if (data != null) {
                    adapter = MyAdapter(data)
                    rc.adapter = adapter
                } else {
                    Log.e("error", "No Data At All")
                }
            },
            onError = { error ->
                Log.e("error", error.message.toString())
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)

                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}


