package graphqlzero

case class PostView(id: Option[String], title: Option[String])
case class UserView(id: Option[String], name: Option[String], email: Option[String])
case class UsersPageView(data: Option[List[UserView]])
