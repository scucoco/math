package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

object IEEEDouble extends Float {
  val Numbers_num = new RealizedType {
     type univ = Double
     def fromString(s: String) = s.toDouble
     override def lex = Some(new NumberLiteralLexer(true,false))
  }

  def Arithmetic_add(x0: Numbers_num.univ, x1: Numbers_num.univ): Numbers_num.univ = x0 + x1
  def Arithmetic_sub(x0: Numbers_num.univ, x1: Numbers_num.univ): Numbers_num.univ = x0 - x1
  def Arithmetic_neg(x0: Numbers_num.univ): Numbers_num.univ = -x0
  def Arithmetic_mult(x0: Numbers_num.univ, x1: Numbers_num.univ): Numbers_num.univ = x0 * x1
  def Arithmetic_div(x0: Numbers_num.univ, x1: Numbers_num.univ): Numbers_num.univ  = x0 / x1
  def Float_NaN: Numbers_num.univ = Double.NaN
  def Float_infinity: Numbers_num.univ = Double.PositiveInfinity
}