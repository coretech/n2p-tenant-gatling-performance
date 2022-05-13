import io.gatling.core.Predef._
import Simulation.getCountriesScen
import io.gatling.http.Predef.http
import com.typesafe.config.ConfigFactory

class LoadTest extends Simulation {

//  val config = ConfigFactory.load("application.conf")

  var users  =  Integer.parseInt(System.getProperty("users"))
  var during = Integer.parseInt(System.getProperty("during"))
  var baseUrl = String.valueOf(System.getProperty("baseUrl"))

  val httpConfig = http.baseUrl(baseUrl)

  setUp(
    getCountriesScen.inject(
      constantUsersPerSec(users) during during
    ).protocols(httpConfig)
  )
}