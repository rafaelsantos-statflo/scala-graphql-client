import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder
import graphqlzero.Client.Album
import graphqlzero.Client.Query

case class AlbumView(id: String, title: String)

@main def app: Unit =
  println("--------------------")
  println("Starting the application")
  println("--------------------")

  println("Running GraphQL query:")

  val album: SelectionBuilder[Album, AlbumView] =
      (Album.id ~ Album.title)
        .mapN(AlbumView)
