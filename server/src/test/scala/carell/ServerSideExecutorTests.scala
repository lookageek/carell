package carell

import munit.CatsEffectSuite
import cats.Id

import carell.Executor
class ServerSideExecutorTests extends CatsEffectSuite {
  // using Id monad as the executor effect type
  val exec = ServerSideExecutor.instance[Either[Throwable, *]]

  test("Build and Run Empty Image") {
    assertEquals(
      exec
        .build(ServerCommand.Build(carell.Build.empty))
        .flatMap(h => exec.run(ServerCommand.Run(h)))
        .map(_.getAll),
      Right(Map.empty),
    )
  }
}
