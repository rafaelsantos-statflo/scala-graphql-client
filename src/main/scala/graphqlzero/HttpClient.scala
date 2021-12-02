package graphqlzero

import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.{PageQueryOptions, PaginateOptions, Post, Query, User, UsersPage}
import post.PostView
import sttp.client3.httpclient.HttpClientSyncBackend
import sttp.model.Uri

object HttpClient {
  private val backend = HttpClientSyncBackend()
  private val uri = Uri(
    scheme = "http",
    host = "graphqlzero.almansi.me",
    path = List("api")
  )

  def queryPost(id: String, selection: SelectionBuilder[Post, Any]): Either[CalibanClientError, Option[PostView]] =
    val httpResponse = Query.post(id = id)(selection).toRequest(uri).send(backend)
    httpResponse.body.fold(
      l => Left(l),
      r => Right(r.asInstanceOf[Option[PostView]])
    )
}
