import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder
import graphqlzero.Client.{Album, Query}
import graphqlzero.HttpClient
import sttp.model.Uri
import sttp.client3.httpclient.HttpClientSyncBackend

case class AlbumView(id: Option[String], title: Option[String])

@main def app: Unit =
  println("--------------------")
  println("Starting the application")
  println("--------------------")

  val client = HttpClient

  client.queryPost("1") match
    case Right(r) => println(s"Found post $r")
    case Left(l) => println(s"Error when querying posts $l")