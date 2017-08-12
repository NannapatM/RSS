package com.egci428.ex03_RSS.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.egci428.ex03_RSS.Interface.ItemClickListener
import com.egci428.ex03_RSS.Model.RSSObject
import com.egci428.ex03_RSS.R

/**
 * Created by lalita on 8/12/2017 AD.
 */
class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
{

    var txtTitle:TextView
    var txtPubdate:TextView
    var txtContent:TextView

    private var itemClickListener: ItemClickListener? = null


    init {

        txtTitle = itemView.findViewById(R.id.txtTitle)
        txtPubdate = itemView.findViewById(R.id.txtPubdate)
        txtContent = itemView.findViewById(R.id.txtContent)

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)

    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v,adapterPosition,false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v,adapterPosition,true)
        return true
    }


}

class FeedAdapter(private val rssObject: RSSObject, private val mContext:Context):RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater:LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }


    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtContent.text = rssObject.items[position].content
        holder.txtPubdate.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->
            if(!isLongClick){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
            }
        })

    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row,parent,false)
        return FeedViewHolder(itemView)
    }

}