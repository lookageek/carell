package carell

object ClientSideExecutor {
  def instance[F[_]]: Executor[F] = new Executor[F] {

    override def build(build: Build): F[Hash] = ???

    override def run(run: Hash): F[SystemState] = ???
  }
}
