package com.nilmani.coronavirustracker.services

import com.nilmani.coronavirustracker.model.LocationStats
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.StringReader
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.annotation.PostConstruct

const val VIRUS_DATA_URL =
    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv"


@Service
class CoronaVirusDataService {

    private var allStats : List<LocationStats> = ArrayList()

    fun getAllStats(): List<LocationStats>? {
        println("When you  use above URl to call it will show error 404")
        return allStats
    }
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    @Throws(IOException::class, InterruptedException::class)
    fun fetchVirusData() {
        val newStats: MutableList<LocationStats> = ArrayList()
        val client: HttpClient = HttpClient.newHttpClient()
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create(VIRUS_DATA_URL))
            .build()
        val httpResponse: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())
        val csvBodyReader = StringReader(httpResponse.body())
        val records: Iterable<CSVRecord> = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader)
        for (record in records) {
            val locationStat = LocationStats()
            locationStat.state=record["Province/State"]
            locationStat.country=record["Country/Region"]
            val latestCases = record[record.size() - 1].toInt()
            val prevDayCases = record[record.size() - 2].toInt()
            locationStat.latestTotalCase=latestCases
            locationStat.differFromPrevDay=latestCases-prevDayCases
            newStats.add(locationStat)
        }
        this.allStats = newStats
    }
}

