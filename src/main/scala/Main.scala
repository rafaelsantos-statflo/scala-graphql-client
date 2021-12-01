import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder
import graphqlzero.Client.{Album, Query}
import sttp.model.Uri
import sttp.client3.httpclient.HttpClientSyncBackend

case class AlbumView(id: Option[String], title: Option[String])

@main def app: Unit =
  println("--------------------")
  println("Starting the application")
  println("--------------------")

  println("Running GraphQL query:")

  val backend = HttpClientSyncBackend()
  val uri = Uri(
    scheme = "http",
    host = "graphqlzero.almansi.me",
    path = List("api")
  )
  val albumFieldSelection: SelectionBuilder[Album, AlbumView] =
      (Album.id ~ Album.title)
        .mapN(AlbumView.apply)
  val httpResponse = Query.album(id = "5")(albumFieldSelection).toRequest(uri).send(backend)
  println("\nResponse Body:")
  println(httpResponse.body)
  println("\nRespones Headers:")
  println(httpResponse.headers)