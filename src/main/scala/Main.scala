import repository.{Email, GenericError, Repository, User}

def printUsers(users: List[User]): Unit =
  users.foreach(println)

def filterCanadianUsers(users: List[User]): List[User] =
  users.filter(u => u.email.getOrElse("").endsWith(".ca"))

def sendEmails(users: List[User]): List[Either[GenericError, Email]] =
  users
    .filter(u => u.email.isDefined)
    .map(u => Email(u.email.getOrElse(""), s"Welcome ${u.name}"))
    .map(Repository.sendEmail)

@main def app: Unit =
  val users: List[User] = Repository.queryUsers
  println("\n-- All users:")
  printUsers(users)

  val canadianUsers = filterCanadianUsers(users)
  println("\n-- Users with Canadian emails:")
  printUsers(canadianUsers)

  println("\n-- Sending welcome emails to Canadian emails:")
  val emailsSent: List[Either[GenericError, Email]] = sendEmails(canadianUsers)

  println("\n---- Emails sent successfully")
  emailsSent.filter(e => e.isRight).map(e => e.getOrElse("None")).foreach(println)

  println("\n---- Emails that failed to send")
  emailsSent.filter(e => e.isLeft).map(e => e.left.getOrElse("None")).foreach(println)

  println("\n---- The same logic in a single stream")
  val summary = Repository
    .queryUsers
    .filter(_.email.getOrElse("").endsWith(".ca"))
    .map(u => (u, Email(u.email.getOrElse("None"), s"Welcome ${u.name}")))
    .map((u, e) => (u, Repository.sendEmail(e)))
    .foreach((u: User, s: Either[GenericError, Email]) =>
      val status = s match {
        case Right(r) => "Sent"
        case Left(l) => s"Failed: $l"
      }
      println(s"name: ${u.name} \temail: ${u.email.getOrElse("None")} \tstatus: $status")
    )
