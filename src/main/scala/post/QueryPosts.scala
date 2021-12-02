package post

import graphqlzero.Client.{Post, Query}
import post.PostView
import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.HttpClient
import sttp.client3.httpclient.HttpClientSyncBackend
import sttp.model.Uri

class QueryPosts {
  private val client = HttpClient

  def queryPost(id: String): Either[CalibanClientError, Option[PostView]] =
    val selection: SelectionBuilder[Post, PostView] =
      (Post.id ~ Post.title)
        .mapN(PostView.apply)

    client.queryPost(id, selection)

}
