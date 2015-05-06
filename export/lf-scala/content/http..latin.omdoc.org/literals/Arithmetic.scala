package org.omdoc.latin.lf.literals
import info.kwarc.mmt.api._
import objects._
import uom._
import ConstantScala._

/** The type of realizations of the theory http://latin.omdoc.org/literals?Arithmetic */
trait Arithmetic extends RealizationInScala {
  override val _domain: TheoryScala = Arithmetic

  val Arithmetic_num : RealizedType
  realizes {universe(Arithmetic.num.path)(Arithmetic_num)}

  def Arithmetic_add(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ
  realizes {function(Arithmetic.add.path, Arithmetic_num, Arithmetic_num, Arithmetic_num)(Arithmetic_add)}

  def Arithmetic_sub(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ
  realizes {function(Arithmetic.sub.path, Arithmetic_num, Arithmetic_num, Arithmetic_num)(Arithmetic_sub)}

  def Arithmetic_neg(x0: Arithmetic_num.univ): Arithmetic_num.univ
  realizes {function(Arithmetic.neg.path, Arithmetic_num, Arithmetic_num)(Arithmetic_neg)}

  def Arithmetic_mult(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ
  realizes {function(Arithmetic.mult.path, Arithmetic_num, Arithmetic_num, Arithmetic_num)(Arithmetic_mult)}

  def Arithmetic_div(x0: Arithmetic_num.univ, x1: Arithmetic_num.univ): Arithmetic_num.univ
  realizes {function(Arithmetic.div.path, Arithmetic_num, Arithmetic_num, Arithmetic_num)(Arithmetic_div)}

}

/** Convenience functions for the MMT URIs of the declarations in the theory http://latin.omdoc.org/literals?Arithmetic
    along with apply/unapply methods for them */
object Arithmetic extends TheoryScala {
  val _base = DPath(utils.URI("http", "latin.omdoc.org") / "literals")
  val _name = LocalName("Arithmetic")

  object num extends ConstantScala {
    val parent = _path
    val name = "num"
  }

  object add extends ConstantScala {
    val parent = _path
    val name = "add"
  }

  object sub extends ConstantScala {
    val parent = _path
    val name = "sub"
  }

  object neg extends ConstantScala {
    val parent = _path
    val name = "neg"
  }

  object mult extends ConstantScala {
    val parent = _path
    val name = "mult"
  }

  object div extends ConstantScala {
    val parent = _path
    val name = "div"
  }

}
