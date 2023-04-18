package carell

// todo - why is this object lowercased
object protocol {
  import sttp.tapir._
  import sttp.tapir.json.circe._
  import sttp.tapir.generic.auto._

  // todo tapir basics - from somewhere?
  private val base = infallibleEndpoint.in("api")

  // endpoints for build and run operation from client to server
  // tapir allows endpoints to be declared as values, and allows for auto-derivation of 
  // some common things needed for creating web service
  val build = base.put.in("build").in(jsonBody[Build]).out(jsonBody[Hash])
  val run = base.post.in("run").in(jsonBody[Hash]).out(jsonBody[SystemState])

}
