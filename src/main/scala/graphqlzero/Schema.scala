package graphqlzero

def str(v: Option[Any]) =
  v.orElse(Some("None")).get

def str(v: Int|String) =
  String.valueOf(v)

case class PostRecord(id: Option[String], title: Option[String]) {
  override def toString(): String =
    s"id: ${str(id)}, title: ${str(title)}"
}

case class UserRecord(id: Int, name: String, email: Option[String]) {
  override def toString(): String =
    s"id: ${str(id)}, name: ${str(name)}, email: ${str(email)}"
}

case class UsersPageRecord(data: Option[List[UserRecord]])

case class EmailRecord(email:String, body: String)

case class RepositoryError(message: String)
