import service.{Email, GenericError, Service, User}

/**
 * Print all users
 */
def printUsers(users: List[User]): Unit =
  users.foreach(println)


/**
 * Return only the users which their email ends in `.ca`
 */
def filterCanadianUsers(users: List[User]): List[User] =
  users.filter(u => u.email.getOrElse("").endsWith(".ca"))


/**
 * Sends an email to given users
 */
def sendEmails(users: List[User]): List[Either[GenericError, Email]] =
  users
    .filter(u => u.email.isDefined)
    .map(u => Email(u.email.getOrElse(""), s"Welcome ${u.name}"))
    .map(Service.sendEmail)

/**
 * Print a report for every user indicating their:
 * - The user name
 * - The user email
 * - Email status indicating if the email was sent or if there was an error
 *
 * Note that `users` and `emailsResponses` are correlated by their index.
 * In other words, the email response in the index `n` corresponds to the user in index `n`
 * Example: `user(1)` has its emails response in `emailResponse(1)`
 *
 * @param users
 * @param emailResponses
 */
def printReportUsingForLoop(users: List[User], emailResponses: List[Either[GenericError, Email]]): Unit =
  /*
   * Use a for loop for this implementation. Examples:
   ```
   val nums = Seq("a","b","c")
     for (n <- nums) println(n)
   ```
   *
   ```
   val nums = Seq("a","b","c")
     for (n <- nums.indices) println(nums(n))
   ```

   Also not the `emailResponses` contains an Either so we will need to match on Right/Left
   ```
   def division(a: Int, b: Int): Either[String, Int] =
     if b == 0 then Left("Cannot divide by 0") else Right(a / b)
   val divisionResult = division(6, 2) match
     case Right(r) => r
     case Left(l) => s"Error reason $l"
   println(divisionResult)
   ```
   */
  for (i <- users.indices)
    val user = users(i)
    val status = emailResponses(i) match
      case Right(r) => "Sent"
      case Left(l) => s"Failed: $l"
    println(s"name: ${user.name} \temail: ${user.email.getOrElse("None")} \tstatus: $status")


/**
 * Same description as `printReportUsingForLoop()`
 */
def printReportUsingZip(users: List[User], emailResponses: List[Either[GenericError, Email]]): Unit =
  /*
   * Use the `zip()` function to combine `users` and `emailResponses` lists into a single list.
   ```
   val numbers = List(1,2,3)
   val letters = List("a","b","c")
   val numbersAndLetters = numbers.zip(letters)
   numbersAndLetters.foreach((n, l) => println(s"number: $n, letter: $l"))
   ```

   Also, when handling `emailSent: Either` try to use the `fold()` instead a match case.
   ```
   ```
   */
  def division(a: Int, b: Int): Either[String, Int] =
    if b == 0 then Left("Cannot divide by 0") else Right(a / b)
  val divisionResult = division(6, 2).fold(l => s"Got error: $l", r => s"Success: $r")
  println(divisionResult)


  users.zip(emailResponses)
    .foreach((user: User, emailSent: Either[GenericError, Email]) =>
      val status = emailSent.fold(l => s"Failed: $l", _ => "Sent")
      println(s"name: ${user.name} \temail: ${user.email.getOrElse("None")} \tstatus: $status")
    )


def redoAppUsingOneStream: Unit =
  Service
    .queryUsers
    // Filter users.email ending in `.ca`
    .filter(_.email.getOrElse("").endsWith(".ca"))
    // Map to Email object
    .map(u => (u, Email(u.email.getOrElse("None"), s"Welcome ${u.name}")))
    // Send email
    .map((u, e) => (u, Service.sendEmail(e)))
    // Print the report
    .foreach((u: User, s: Either[GenericError, Email]) =>
      val status = s.fold(l => s"Failed: $l", _ => "Sent")
      println(s"name: ${u.name} \temail: ${u.email.getOrElse("None")} \tstatus: $status")
    )


@main def app: Unit =
  val users: List[User] = Service.queryUsers
  println("\n-- 1. Print a list of users")
  printUsers(users)


  println("\n-- 2. Print a sublist list of users with Canadian emails")
  val canadianUsers = filterCanadianUsers(users)
  printUsers(canadianUsers)


  println("\n-- 3. Send emails to users with Canadian emails")
  val emailResponses: List[Either[GenericError, Email]] = sendEmails(canadianUsers)
  emailResponses.foreach(println)


  println("\n---- REPORTING -----")
  println("-" * 20)


  println("\n-- 4. Report the results using a 'for loop'")
  printReportUsingForLoop(canadianUsers, emailResponses)


  println("\n-- 5. Report the results using a 'zip' function")
  printReportUsingZip(canadianUsers, emailResponses)


  println("\n-- 6. Re-write the exercise using a single stream")
  redoAppUsingOneStream
