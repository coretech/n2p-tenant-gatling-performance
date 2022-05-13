import io.gatling.core.Predef.scenario
import Requests._
import io.gatling.core.structure.ScenarioBuilder

object Simulation {
  def getCountriesScen: ScenarioBuilder = scenario("getCountries scenario")
    .exec(getCountries)
    .exec(postValidateCountries)
    .exec(getCountryRegions)
    .exec(getCountryRegionById)
    .exec(getCountryRegionsByNodeAndId)
    .exec(getLocationsProfiles)
}
