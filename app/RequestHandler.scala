import javax.inject.Inject

import play.api.http._
import play.api.mvc._


/**
  * @author fabiomazzone
  * @version 1.0, 06.07.17
  */
class RequestHandler @Inject() (
                                 errorHandler: HttpErrorHandler,
                                 configuration: HttpConfiguration,
                                 filters: HttpFilters,
                                 userServiceRoutes: userService.Routes,
                                 sqlTrainerServiceRoutes: sqlTrainerService.Routes
                               ) extends DefaultHttpRequestHandler(userServiceRoutes, errorHandler, configuration, filters) {

  /*
	* Gets the subdomain: "admin" o "www"
	*/
  private def getSubdomain(domain: String) : String = {
    val tdlDot = domain.lastIndexOf('.')
    var tdl = ""
    var subDomainDot = 0



    if(tdlDot == -1) return ""

    tdl = domain.substring(tdlDot + 1)

    if(tdl.contentEquals("localhost")) subDomainDot = tdlDot

    domain.substring(0, subDomainDot)
  }

  /*
  * Delegates to each router depending on the corresponding subdomain
  */
  override def routeRequest(request: RequestHeader) : Option[Handler] = {
    println(getSubdomain(request.domain))
    getSubdomain(request.domain) match {
      case "sql" => sqlTrainerServiceRoutes.routes.lift(request)
      case _ => userServiceRoutes.routes.lift(request)
    }
  }
}