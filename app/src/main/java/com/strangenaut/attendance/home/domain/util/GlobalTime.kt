package com.strangenaut.attendance.home.domain.util

import org.apache.commons.net.time.TimeTCPClient
import java.io.EOFException
import java.time.LocalDateTime
import java.util.TimeZone

object GlobalTime {

    private const val DEFAULT_TIMEOUT_MILLIS = 5000

    private val time_server_urls = arrayOf(
        "time.nist.gov",
        "time-a-g.nist.gov",
        "utcnist.colorado.edu",
        "utcnist2.colorado.edu",
        "utcnist3.colorado.edu",
        "time-a-wwv.nist.gov"
    )
    private val maxAttempts = time_server_urls.size * 5

    fun now(): LocalDateTime {
        var attemptsCount = 0
        var serverUrlIndex = 0

        while(attemptsCount++ < maxAttempts) {
            try {
                val client = TimeTCPClient()

                try {
                    client.defaultTimeout = DEFAULT_TIMEOUT_MILLIS
                    client.connect(time_server_urls[serverUrlIndex])

                    val response = client.date.toInstant()
                    val zoneId = TimeZone.getDefault().toZoneId()

                    return LocalDateTime.ofInstant(response, zoneId)
                } catch(e: EOFException) {
                    e.printStackTrace()
                    serverUrlIndex = (serverUrlIndex + 1) % time_server_urls.size
                } finally {
                    client.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return LocalDateTime.now()
    }
}