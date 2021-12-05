package graphqlzero

import caliban.client.CalibanClientError

object Repository {
  val users: List[UserRecord] = List(
    UserRecord( 1, "Ross Compton",     Some("Ross.Compton@gmail.com")),
    UserRecord( 2, "Maya Mcguire",     Some("Maya.Mcguire@yahoo.ca")),
    UserRecord( 3, "Sonia Hoffman",    Some("Sonia.Hoffman@yahoo.it")),
    UserRecord( 4, "Abdullah Nelson",  Some("Abdullah.Nelson@gmail.ca")),
    UserRecord( 5, "Kane Griffin",     None),
    UserRecord( 6, "Tomas Mcgee",      Some("Tomas.Mcgee@gmail.com")),
    UserRecord( 7, "Brynlee Glenn",    Some("Brynlee.Glenn@hotmail.com")),
    UserRecord( 8, "Terrence Case",    None),
    UserRecord( 9, "Lexie Maldonado",  Some("Lexie.Maldonado@gmail.com")),
    UserRecord(10, "Amelia Sexton",    Some("Amelia.Sexton@outlook.ca")),
    UserRecord(11, "Jase Hanson",      Some("Jase_Hanson@yahoo.com")),
    UserRecord(12, "Kamryn Rojas",     None),
    UserRecord(13, "Khloe Lopez",      Some("Khloe-Lopez@protonmail.ca")),
    UserRecord(14, "Lola Griffin",     Some("LolaG@yahoo.com")),
    UserRecord(15, "Aimee Glenn",      Some("Aimee.Glenn@yahoo.com")),
  )

  val emails: Array[EmailRecord] = Array.empty

  def queryUsers: List[UserRecord] =
    users

  def sendEmail(email: EmailRecord): Either[RepositoryError, EmailRecord] =
    emails.appended(email)
    Right(email)

}
