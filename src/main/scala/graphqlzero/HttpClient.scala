package graphqlzero

import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.{PageQueryOptions, PaginateOptions, Post, Query, User, UsersPage}
import sttp.client3.httpclient.HttpClientSyncBackend
import sttp.model.Uri
import graphqlzero.PostView
import graphqlzero.UserView

object HttpClient {
  private val backend = HttpClientSyncBackend()
  private val uri = Uri(
    scheme = "http",
    host = "graphqlzero.almansi.me",
    path = List("api")
  )

  def queryPost(id: String): Either[CalibanClientError, Option[PostView]] =
    val selection: SelectionBuilder[Post, PostView] =
      (Post.id ~ Post.title)
        .mapN(PostView.apply)

    Query.post(id = id)
      (selection)
      .toRequest(uri)
      .send(backend)
      .body

}
