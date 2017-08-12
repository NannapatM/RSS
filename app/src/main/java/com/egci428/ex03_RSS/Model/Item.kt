package com.egci428.ex03_RSS.Model

/**
 * Created by lalita on 8/12/2017 AD.
 */
data class Item(val title:String, val pubDate:String, val link:String, val guid:String, val author:String, val thumbnail:String, val description:String, val content:String, val enclouser:Object, val categories:List<String>)