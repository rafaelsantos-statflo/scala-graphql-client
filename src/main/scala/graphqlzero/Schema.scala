package graphqlzero

def str(v: Option[Any]) =
  v.orElse(Some("None")).get

case class PostRecord(id: Option[String], title: Option[String]) {
  override def toString(): String =
    s"id: ${str(id)}, title: ${str(title)}"
}

case class UserRecord(id: Option[String], name: Option[String], email: Option[String]) {
  override def toString(): String =
    s"id: ${str(id)}, name: ${str(name)}, email: ${str(email)}"
}

case class UsersPageRecord(data: Option[List[UserRecord]])
