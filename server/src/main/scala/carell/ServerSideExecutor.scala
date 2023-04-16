package carell

import carell.Hash
import carell.ServerCommand
import cats.ApplicativeThrow
import cats.implicits.*

object ServerSideExecutor {
  // todo really do not know what is this lol
  def apply[F[_]](using F: Executor[F]): Executor[F] = F

  // ApplicativeThrow is type alias for ApplicativeError[F[_], Throwable]
  def instance[F[_]: ApplicativeThrow]: Executor[F] =
    new Executor[F] {
      private val emptyHash: Hash = Hash(Array())

      override def build(build: ServerCommand.Build): F[Hash] = (build.build == Build.empty)
        .guard[Option]  // todo <- do not know this API either
        .as(emptyHash)
        .liftTo[F](new Throwable("Unsupported build!"))

      override def run(run: ServerCommand.Run): F[SystemState] = (run == ServerCommand.Run(emptyHash))
        .guard[Option]
        .as(KVState(Map.empty))
        .liftTo[F](new Throwable("Unsupported Hash!"))

    }

  // tracking the state of the executing as a transactional map
  private final case class KVState(getAll: Map[String, String]) extends SystemState

}

