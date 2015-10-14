package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

object RationalArithmetic extends Arithmetic {
  val Literals_lit = new StandardRat with RealizedType

  def Arithmetic_add(x: Literals_lit.univ, y: Literals_lit.univ) = (x._1*y._2 + x._2*y._2, x._2*y._2)
  def Arithmetic_sub(x: Literals_lit.univ, y: Literals_lit.univ) = (x._1*y._2 - x._2*y._2, x._2*y._2)
  def Arithmetic_neg(x: Literals_lit.univ): Literals_lit.univ = (-x._1,x._2)
  def Arithmetic_mult(x: Literals_lit.univ, y: Literals_lit.univ) = (x._1*y._1, x._2*y._2)
  def Arithmetic_div(x: Literals_lit.univ, y: Literals_lit.univ) = (x._1*y._2, x._2*y._1)
}