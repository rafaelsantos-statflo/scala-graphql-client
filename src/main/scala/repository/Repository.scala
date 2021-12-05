package repository

object Repository {
  private val users: List[User] = List(
    User(1, "Ash Matarazo", Some("Ash.Matarazo@gmail.ca")),
    User(2, "Bella Glennm", Some("Bella.Glennm@yahoo.com")),
    User(3, "Chris Sexton", Some("Chris.Sexton@outlook.ca")),
    User(4, "David Glennm", Some("David.Glennm@hotmail.com")),
    User(5, "Emily Hanson", Some("Emily_Hanson@yahoo.com")),
    User(6, "Frank Rojasa", None),
    User(7, "Gabriel Grif", None),
    User(8, "Iris Lorensa", Some("Iris-Lorensa@gmail.it")),
    User(9, "James Nelson", Some("James.Nelson@gmail.ca")),
    User(10, "Lola Griffin", Some("LolaG@yahoo.com")),
    User(11, "Maya Mcguire", Some("Maya.Mcguire@yahoo.ca")),
    User(12, "Ross Compton", Some("Ross.Compton@gmail.com")),
    User(13, "Sonia Hophen", Some("Sonia.Hoffman@yahoo.it")),
    User(14, "Terrence Cas", None),
    User(15, "Tomas Mcgee", Some("Tomas.Mcgee@gmail.com")),
  )

  private var emailsSent: Array[Email] = Array.empty

  /**
   * All users in this repository
   */
  def queryUsers: List[User] =
    users

  /**
   * All emails sent by this repository
   */
  def queryEmails: List[Email] =
    emailsSent.toList

  def sendEmail(email: Email): Either[GenericError, Email] =
    if (email.address.endsWith("@gmail.ca"))
      Left(GenericError(s"Gmail canada is offline"))
    else
      emailsSent = emailsSent.appended(email)
      Right(email)

}
