package com.nilmani.coronavirustracker.controller

import com.nilmani.coronavirustracker.model.LocationStats
import com.nilmani.coronavirustracker.services.CoronaVirusDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.function.ToIntFunction


@Controller
class HomeController {
    @Autowired
    private lateinit var  coronaVirusDataService: CoronaVirusDataService
    @GetMapping("/")
    fun home(model: Model):String{
        val allStats : List<LocationStats>? =coronaVirusDataService.getAllStats()
        val totalReportedCases =
            allStats?.stream()?.mapToInt { stat : LocationStats -> stat.latestTotalCase }?.sum()
        val totalNewCases =
            allStats?.stream()?.mapToInt { stat: LocationStats -> stat.differFromPrevDay }?.sum()
        model.addAttribute("locationStats",allStats)
        model.addAttribute("totalReportedCases",totalReportedCases)
        model.addAttribute("totalNewCases",totalNewCases)
        return "home"
    }
}
