package graphqlzero

import caliban.client.FieldBuilder.{ListOf, OptionOf}
import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.*
import graphqlzero.{PostView, UsersPageView}
import sttp.client3.httpclient.HttpClientSyncBackend
import sttp.model.Uri

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


  /*
  --- GraphQL Definition ---

  type User {
    id: ID
    name: String
    username: String
    email: String
    address: Address
    todos(options: PageQueryOptions): TodosPage
    ...
  }

  type UsersPage {
    data: [User]
    ...
  }

  query {
    users(options: PageQueryOptions): UsersPage
  }

  */

  def queryUsers: Either[CalibanClientError, List[UserView]] =
    val userSelection: SelectionBuilder[User, Option[UserView]] =
      (User.id ~ User.name ~ User.email)
        // We get compilation error if I try the short version:  ".mapN(UserView.apply)"
        .mapN((id: Option[String], name: Option[String], email: Option[String]) =>
          Some(UserView.apply(id, name, email)))

    val usersPageSelection: SelectionBuilder[UsersPage, List[Option[Option[UserView]]]] =
      (UsersPage.data(userSelection))
        .map(data => data.getOrElse(List.empty))


    val result = Query.users(options = None)
      (usersPageSelection)
      .toRequest(uri)
      .send(backend)
      .body

    result match {
      // Some(List(Some(Some(UserView(...
      case Right(r: Option[List[Option[Option[UserView]]]]) => Right(r.map(v => v.flatten).map(v => v.flatten).getOrElse(List.empty))
      case Left(l) => Left(l)
    }

}
