package com.nilmani.coronavirustracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CoronaVirusTrackerApplication

fun main(args: Array<String>) {
	runApplication<CoronaVirusTrackerApplication>(*args)
}
