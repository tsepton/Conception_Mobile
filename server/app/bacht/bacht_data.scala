package bacht

class Expr
case class bacht_ast_empty_agent() extends Expr
case class bacht_ast_primitive(
    primitive: String,
    id: Int,
    title: String = "",
    body: String = ""
) extends Expr
case class bacht_ast_agent(op: String, agenti: Expr, agentii: Expr) extends Expr
