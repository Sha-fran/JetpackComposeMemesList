package com.example.jetpackcompose

data class MemesResponse (val data:Data)
data class Data(val memes:List<Memes>)
data class Memes(val name:String, val url:String)