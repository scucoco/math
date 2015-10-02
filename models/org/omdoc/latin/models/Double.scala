package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

object IEEEDouble extends Float {
  val Arithmetic_num = new RealizedType {
     type univ = Double
     def fromString(s: String) = s.toDouble
     override def lex = Some(new NumberLiteralLexer(true,false))
  }

  def Arithmetic_add(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ = x0 + x1
  def Arithmetic_sub(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ = x0 - x1
  def Arithmetic_neg(x0: Arithmetic_num.univ): Arithmetic_num.univ = -x0
  def Arithmetic_mult(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ = x0 * x1
  def Arithmetic_div(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ  = x0 / x1
  def Float_NaN: Arithmetic_num.univ = Double.NaN
  def Float_infinity: Arithmetic_num.univ = Double.PositiveInfinity
}