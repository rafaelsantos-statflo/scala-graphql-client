package service

def str(v: Option[Any]) =
  v.orElse(Some("None")).get

def str(v: Int|String) =
  String.valueOf(v)

case class User(id: Int, name: String, email: Option[String])

case class Email(address:String, body: String) {
  override def toString: String =
    s"address: ${str(address)}, body: ${str(body)}"
}

case class GenericError(message: String) {
  override def toString: String =
    message
}
