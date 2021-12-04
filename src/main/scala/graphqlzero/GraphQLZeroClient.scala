package graphqlzero

import caliban.client.FieldBuilder.{ListOf, OptionOf}
import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.*
import graphqlzero.{PostRecord, UsersPageRecord}
import sttp.client3.httpclient.HttpClientSyncBackend
import sttp.model.Uri

object GraphQLZeroClient {
  private val backend = HttpClientSyncBackend()
  private val uri = Uri(
    scheme = "http",
    host = "graphqlzero.almansi.me",
    path = List("api")
  )

  def queryPost(id: String): Either[CalibanClientError, Option[PostRecord]] =
    val selection: SelectionBuilder[Post, PostRecord] =
      (Post.id ~ Post.title)
        .mapN(PostRecord.apply)

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

  def queryUsers: Either[CalibanClientError, List[UserRecord]] =
    val userSelection: SelectionBuilder[User, Option[UserRecord]] =
      (User.id ~ User.name ~ User.email)
        // We get compilation error if I try the short version:  ".mapN(UserView.apply)"
        .mapN((id: Option[String], name: Option[String], email: Option[String]) =>
          Some(UserRecord.apply(id, name, email)))

    val usersPageSelection: SelectionBuilder[UsersPage, List[Option[Option[UserRecord]]]] =
      (UsersPage.data(userSelection))
        .map(data => data.getOrElse(List.empty))


    val result = Query.users(options = None)
      (usersPageSelection)
      .toRequest(uri)
      .send(backend)
      .body

    result match {
      // Some(List(Some(Some(UserView(...
      case Right(r: Option[List[Option[Option[UserRecord]]]]) => Right(r.map(v => v.flatten).map(v => v.flatten).getOrElse(List.empty))
      case Left(l) => Left(l)
    }

}
