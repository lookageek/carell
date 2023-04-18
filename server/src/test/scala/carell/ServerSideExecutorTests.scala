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
        .build(carell.Build.empty)
        .flatMap(exec.run)
        .map(_.getAll),
      Right(Map.empty),
    )
  }
}
