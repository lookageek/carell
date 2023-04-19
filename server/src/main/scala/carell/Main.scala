package carell

import cats.effect.IOApp
import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import com.comcast.ip4s.port
import com.comcast.ip4s.host
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.server.ServerEndpoint

object Main extends IOApp.Simple {
  // todo - ask ctrl + C on run does not kill the server?
  def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHost(host"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp {
        val exec = ServerSideExecutor.instance[IO]
        // forcibly attach a type to endpoints with proper types for "toRoutes"
        // in ServerInterpreter to work
        val endpoints: List[ServerEndpoint[Any, IO]] = List(
          // build API
          protocol.build.serverLogicSuccess(exec.build),
          // run API
          protocol.run.serverLogicSuccess(exec.run)
        )

        Http4sServerInterpreter[IO]().toRoutes(endpoints).orNotFound
      }
      .build
      .useForever

}
