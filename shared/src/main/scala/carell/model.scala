package carell

// Build representing the docker container manifest
final case class Build(
  base: Build.Base,
  commands: List[Build.Command],
)

object Build {

  enum Base {
    case EmptyImage
    case ImageReference(hash: Hash)
  }

  enum Command {
    case Upsert(key: String, value: String)
    case Delete(key: String)
  }

  val empty: Build = Build(Base.EmptyImage, Nil)
}

// Hash is the hash created for a built image
final case class Hash(value: Array[Byte])

// all the commands you can send to server from a client
enum ServerCommand {
    // build from a manifest
    case Build(build: carell.Build)
    case Run(hash: Hash)
}

// both build and run may encounter errors, but not encoding that into types here
// at the moment, we will catch it from the effect type


