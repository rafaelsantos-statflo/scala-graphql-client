import service.{Email, GenericError, Service, User}

def printUsers(users: List[User]): Unit =
  users.foreach(println)

def filterCanadianUsers(users: List[User]): List[User] =
  users.filter(u => u.email.getOrElse("").endsWith(".ca"))

def sendEmails(users: List[User]): List[Either[GenericError, Email]] =
  users
    .filter(u => u.email.isDefined)
    .map(u => Email(u.email.getOrElse(""), s"Welcome ${u.name}"))
    .map(Service.sendEmail)

@main def app: Unit =
  val users: List[User] = Service.queryUsers
  println("\n-- All users:")
  printUsers(users)


  println("\n-- Users with Canadian emails:")
  val canadianUsers = filterCanadianUsers(users)
  printUsers(canadianUsers)


  println("\n-- Sending welcome emails to Canadian emails:")
  val emailsSent: List[Either[GenericError, Email]] = sendEmails(canadianUsers)
  emailsSent.foreach(println)


  println("\n---- REPORTING -----")
  println("-" * 20)


  println("\n-- Report a for loop")
  for (i <- canadianUsers.indices)
    val user = canadianUsers(i)
    val status = emailsSent(i) match {
      case Right(r) => "Sent"
      case Left(l) => s"Failed: $l"
    }
    println(s"name: ${user.name} \temail: ${user.email.getOrElse("None")} \tstatus: $status")


  println("\n-- Report using zip()")
  canadianUsers.zip(emailsSent)
    .foreach((user, emailSent) => {
      val status = emailSent match {
        case Right(r) => "Sent"
        case Left(l) => s"Failed: $l"
      }
      println(s"name: ${user.name} \temail: ${user.email.getOrElse("None")} \tstatus: $status")
    })


  println("\n---- Redo the entire logic with a single stream")
  val summary = Service
    .queryUsers
    .filter(_.email.getOrElse("").endsWith(".ca"))
    .map(u => (u, Email(u.email.getOrElse("None"), s"Welcome ${u.name}")))
    .map((u, e) => (u, Service.sendEmail(e)))
    .foreach((u: User, s: Either[GenericError, Email]) =>
      val status = s match {
        case Right(r) => "Sent"
        case Left(l) => s"Failed: $l"
      }
      println(s"name: ${u.name} \temail: ${u.email.getOrElse("None")} \tstatus: $status")
    )
