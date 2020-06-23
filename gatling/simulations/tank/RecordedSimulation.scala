package tank

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("de-DE,de;q=0.9,en-US;q=0.8,en;q=0.7")
		.doNotTrackHeader("1")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 Safari/537.36")

	val headers_0 = Map(
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "none",
		"Sec-Fetch-User" -> "?1")



	val scn = scenario("RecordedSimulation")
		.exec(http("start")
			.get("/tankcommander/start")
			.headers(headers_0))
		.pause(6)
		.exec(http("up")
			.get("/tankcommander/up")
			.headers(headers_0))
		.pause(6)
		.exec(http("end")
			.get("/tankcommander/end")
			.headers(headers_0))
		.pause(3)
		.exec(http("down")
			.get("/tankcommander/down")
			.headers(headers_0))
		.pause(2)
		.exec(http("end")
			.get("/tankcommander/end")
			.headers(headers_0))
		.pause(4)
		.exec(http("shoot")
			.get("/tankcommander/shoot")
			.headers(headers_0))
		.pause(4)
		.exec(http("save")
			.get("/tankcommander/save")
			.headers(headers_0))
		.pause(4)
		.exec(http("load")
			.get("/tankcommander/load")
			.headers(headers_0))
		.pause(4)
		.exec(http("end")
			.get("/tankcommander/end")
			.headers(headers_0))

	setUp(scn.inject(atOnceUsers(100))).protocols(httpProtocol)
}