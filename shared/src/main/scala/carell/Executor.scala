package carell

trait Executor[F[_]] {
  // build and result is a final hash, wrapped in a effect type
  def build(build: Build): F[Hash]
  // run is running the docker container, it will not have any return
  def run(run: Hash): F[SystemState]
}

object Executor {
  // todo really do not know what is this lol
  def apply[F[_]](using F: Executor[F]): Executor[F] = F
}

