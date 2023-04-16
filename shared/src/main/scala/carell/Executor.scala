package carell

trait Executor[F[_]] {
  // build and result is a final hash, wrapped in a effect type
  def build(build: ServerCommand.Build): F[Hash]
  // run is running the docker container, it will not have any return
  def run(run: ServerCommand.Run): F[SystemState]
}

object Executor {
  // todo really do not know what is this lol
  def apply[F[_]](using F: Executor[F]): Executor[F] = F
}

// Maintain an internal state of the executing process
trait SystemState {
  def getAll: Map[String, String]
}

