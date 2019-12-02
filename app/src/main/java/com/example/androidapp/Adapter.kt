package com.example.androidapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class Adapter(private val context: Context,
                    private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {


    //this variable is used to inflate the layout
    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //get the data in dataitem and inflate the item layout and store in rowView
        var dataitem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_view_item, parent, false)

        //take the title, description and image from data item and pass it to the rowView to display in a row
        rowView.findViewById<TextView>(R.id.row_title).text = dataitem.get("title")
        rowView.findViewById<TextView>(R.id.row_description).text = dataitem.get("description")

        //change http to https 
        val url = dataitem.get("imageHref").toString().replace("http","https")
        Log.i("image url",url)

        //use glide to lazy load images into imageview
        Glide.with(context)  //2
            .load(url) //3
            .centerCrop() //4
            .placeholder(R.drawable.place_holder) //5
            .into(rowView.findViewById<ImageView>(R.id.row_image)) //8



        rowView.tag = position
        return rowView
    }

    //retrieve the item based on position
    override fun getItem(position: Int): Int { return position }

    override fun getItemId(position: Int): Long { return position.toLong() }

    //get the count of items
    override fun getCount(): Int { return dataList.size }
}