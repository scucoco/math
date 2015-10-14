package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

object IEEEDouble extends Float {
  val Literals_lit = new RealizedType {
     type univ = Double
     def fromString(s: String) = s.toDouble
     override def lex = Some(new NumberLiteralLexer(true,false))
  }

  def Arithmetic_add(x0: Literals_lit.univ, x1: Literals_lit.univ): Literals_lit.univ = x0 + x1
  def Arithmetic_sub(x0: Literals_lit.univ, x1: Literals_lit.univ): Literals_lit.univ = x0 - x1
  def Arithmetic_neg(x0: Literals_lit.univ): Literals_lit.univ = -x0
  def Arithmetic_mult(x0: Literals_lit.univ, x1: Literals_lit.univ): Literals_lit.univ = x0 * x1
  def Arithmetic_div(x0: Literals_lit.univ, x1: Literals_lit.univ): Literals_lit.univ  = x0 / x1
  def Float_NaN: Literals_lit.univ = Double.NaN
  def Float_infinity: Literals_lit.univ = Double.PositiveInfinity
}