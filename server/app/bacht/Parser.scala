package bacht

import scala.util.parsing.combinator._
import scala.util.matching.Regex

class BachTParsers extends RegexParsers {

  def id 	: Parser[String] = ("[a-z][0-9a-zA-Z_]*").r ^^ {_.toString}
  def title : Parser[String] = ("[a-z][0-9a-zA-Z_]*").r ^^ {_.toString}
  def body : Parser[String] = ("[a-z][0-9a-zA-Z_]*").r ^^ {_.toString}
  def separator : Parser[String] = (",").r ^^ {_.toString}

  val opChoice  : Parser[String] = "+" 
  val opPara    : Parser[String] = "||"
  val opSeq     : Parser[String] = ";" 
 
  def primitive : Parser[Expr]   = "tell("~id~separator~title~body~")" ^^ {
        case _ ~ vId ~ vseparator ~ vTitle ~ vBody ~ _ => bacht_ast_primitive("tell", vId.toInt, vTitle, vBody) }  | 
                                   "ask("~id~")" ^^ {
        case _ ~ vId ~ _  => bacht_ast_primitive("ask", vId.toInt) }   | 
                                   "get("~id~")" ^^ {
        case _ ~ vId ~ _  => bacht_ast_primitive("get", vId.toInt) }   | 
                                   "nask("~id~")" ^^ {
        case _ ~ vId ~ _  => bacht_ast_primitive("nask", vId.toInt) }

  def agent = compositionChoice

  def compositionChoice : Parser[Expr] = compositionPara~rep(opChoice~compositionChoice) ^^ {
        case ag ~ List() => ag
        case agi ~ List(op~agii)  => bacht_ast_agent(op,agi,agii) }

  def compositionPara : Parser[Expr] = compositionSeq~rep(opPara~compositionPara) ^^ {
        case ag ~ List() => ag
        case agi ~ List(op~agii)  => bacht_ast_agent(op,agi,agii) }

  def compositionSeq : Parser[Expr] = simpleAgent~rep(opSeq~compositionSeq) ^^ {
        case ag ~ List() => ag
        case agi ~ List(op~agii)  => bacht_ast_agent(op,agi,agii) }

  def simpleAgent : Parser[Expr] = primitive | parenthesizedAgent

  def parenthesizedAgent : Parser[Expr] = "("~>agent<~")"

}

object BachTSimulParser extends BachTParsers {

  def parse_primitive(prim: String) = parseAll(primitive,prim) match {
        case Success(result, _) => result
        case failure : NoSuccess => scala.sys.error(failure.msg)
  }

  def parse_agent(ag: String) = parseAll(agent,ag) match {
        case Success(result, _) => result
        case failure : NoSuccess => scala.sys.error(failure.msg)
  }

}
