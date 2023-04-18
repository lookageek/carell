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

      override def build(build: Build): F[Hash] = (build == Build.empty)
        .guard[Option]  // todo <- do not know this API either
        .as(emptyHash)
        .liftTo[F](new Throwable("Unsupported build!"))

      override def run(run: Hash): F[SystemState] = (run == emptyHash)
        .guard[Option]
        .as(SystemState(Map.empty))
        .liftTo[F](new Throwable("Unsupported Hash!"))
    }
}

