package carell

// auto-derivation of codecs for some of the models we have
import io.circe.Codec

// Build representing the docker container manifest
final case class Build(
  base: Build.Base,
  commands: List[Build.Command],
) derives Codec.AsObject

object Build {

  // this "derives" syntax is new for scala 3 and it derives the typeclass for the element where this has been added
  enum Base derives Codec.AsObject {
    case EmptyImage
    case ImageReference(hash: Hash)
  }

  enum Command derives Codec.AsObject {
    case Upsert(key: String, value: String)
    case Delete(key: String)
  }

  val empty: Build = Build(Base.EmptyImage, Nil)
}

// Hash is the hash created for a built image
final case class Hash(value: Array[Byte]) derives Codec.AsObject

// tracking the state of the executing as a transactional map
private final case class SystemState(all: Map[String, String]) derives Codec.AsObject

// all the commands you can send to server from a client
enum ServerCommand derives Codec.AsObject {
    // build from a manifest
    case Build(build: carell.Build)
    case Run(hash: Hash)
}

// both build and run may encounter errors, but not encoding that into types here
// at the moment, we will catch it from the effect type


