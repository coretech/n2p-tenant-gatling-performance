import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Requests {

  def getCountries = {
    exec(
    http("getCountries")
      .get("/api/countries")
      .check(status.is(200))
    )
  }

  def getCountryRegionsByNodeAndId = {
    exec(
      http("getCountryRegionsByNodeAndId")
        .get("/api/countries/regions")
        .queryParam("tenant", "0")
        .queryParam("node", "1")
        .check(status.is(200))
    )
  }

  def getCountryRegions = {
    exec(
      http("getCountryRegions")
        .get("/api/countries/%s/regions".format("2"))
        .check(status.is(200))
    )
  }

  def getCountryRegionById = {
    exec(
      http("getCountryRegionById")
        .get("/api/countries/%s/regions/%s".format("2", "28"))
        .check(status.is(200))
    )
  }

  def postValidateCountries = {
    exec(
      http("postValidateCountries")
        .post("/api/countries/locations/validate")
        .body(StringBody(
          """
            |{
            |"countryCriteria": "1",
            |"regionCriteria": "7",
            |"municipalityCriteria": "2448",
            |"localityCriteria": "89911"
            |}
            |""".stripMargin))
        .header("content-Type", "application/json")
        .check(status.is(200))
    )
  }

  def getLocationsProfiles = {
    exec(
      http("getLocationsProfiles")
        .get("/api/countries/locations/profiles")
        .queryParam("CountryCriteria", "1")
        .queryParam("RegionCriteria", "24")
        .queryParam("MunicipalityCriteria", "6068")
        .queryParam("LocalityCriteria", "120939")
        .check(status.is(200))
    )
  }

}
