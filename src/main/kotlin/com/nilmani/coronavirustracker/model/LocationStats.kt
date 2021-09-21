package com.nilmani.coronavirustracker.model

data class LocationStats(
    var state:String?="",
    var country:String?="",
    var latestTotalCase:Int=0,
    var differFromPrevDay:Int=0
)
