package carell

import munit.CatsEffectSuite
import cats.Id

class ExecutorTests extends CatsEffectSuite {
  // using Id monad as the executor effect type
  val exec = Executor.instance[Either[Throwable, *]]

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
