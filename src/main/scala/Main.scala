import caliban.client.Operations.RootQuery
import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.{Album, Post, Query}
import graphqlzero.{GraphQLZeroClient, PostRecord, UserRecord}
import sttp.model.Uri
import sttp.client3.httpclient.HttpClientSyncBackend

def p(str: Any): Unit =
  println(str)

def printUsers(users: List[UserRecord]): Unit =
  users.foreach(p)

def filterBizUsers(users: List[UserRecord]): List[UserRecord] =
  users.filter(u => u.email.getOrElse("").endsWith(".biz"))

@main def app: Unit =
  val client = GraphQLZeroClient

  val users: Either[CalibanClientError, List[UserRecord]] = client.queryUsers
  users match {
    case Right(r) =>
      p("All the users:")
      printUsers(r)
      val bizUsers = filterBizUsers(r)
      p("Biz users:")
      printUsers(bizUsers)
    case Left(l) => l.printStackTrace()
  }
