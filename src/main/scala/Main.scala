import caliban.client.Operations.RootQuery
import caliban.client.{CalibanClientError, SelectionBuilder}
import graphqlzero.Client.{Album, Post, Query}
import graphqlzero.{EmailRecord, GraphQLZeroClient, PostRecord, Repository, RepositoryError, UserRecord}
import sttp.model.Uri
import sttp.client3.httpclient.HttpClientSyncBackend

val repository = Repository

def p(str: Any): Unit =
  println(str)

def printUsers(users: List[UserRecord]): Unit =
  users.foreach(p)

def filterCanadianUsers(users: List[UserRecord]): List[UserRecord] =
  users.filter(u => u.email.getOrElse("").endsWith(".ca"))

def sendEmails(users: List[UserRecord]): Either[RepositoryError, EmailRecord] =
  users.foreach(u => repository.sendEmail(EmailRecord(u.email.getOrElse(""), u.name)))
  Left(RepositoryError("my bad"))


@main def app: Unit =
  val users: List[UserRecord] = repository.queryUsers
  p("\nAll users:")
  printUsers(users)
  val canadianUsers = filterCanadianUsers(users)
  p("\nUsers with Canadian emails:")
  printUsers(canadianUsers)
  p("\nSend welcome emails to Canadian emails:")
  sendEmails(canadianUsers)

