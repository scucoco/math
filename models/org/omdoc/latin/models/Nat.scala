package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

trait Nat extends Literals {
  val Literals_lit = new RealizedType {
     type univ = BigInt
     override def valid(u: BigInt) = u >= 0
     def fromString(s: String) = BigInt(s)
     override def lex = Some(new NumberLiteralLexer(false,false))
  }
}

object Nat extends Nat