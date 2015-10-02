package org.omdoc.latin.models

import org.omdoc.latin.lf.literals._

import info.kwarc.mmt.api.uom._
import info.kwarc.mmt.api.parser._

object RationalArithmetic extends Arithmetic {
  val Arithmetic_num = new StandardRat with RealizedType

  def Arithmetic_add(x: Arithmetic_num.univ, y: Arithmetic_num.univ) = (x._1*y._2 + x._2*y._2, x._2*y._2)
  def Arithmetic_sub(x: Arithmetic_num.univ, y: Arithmetic_num.univ) = (x._1*y._2 - x._2*y._2, x._2*y._2)
  def Arithmetic_neg(x: Arithmetic_num.univ): Arithmetic_num.univ = (-x._1,x._2)
  def Arithmetic_mult(x: Arithmetic_num.univ, y: Arithmetic_num.univ) = (x._1*y._1, x._2*y._2)
  def Arithmetic_div(x: Arithmetic_num.univ, y: Arithmetic_num.univ) = (x._1*y._2, x._2*y._1)
}