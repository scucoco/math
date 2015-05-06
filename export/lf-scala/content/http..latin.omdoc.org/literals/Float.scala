package org.omdoc.latin.lf.literals
import info.kwarc.mmt.api._
import objects._
import uom._
import ConstantScala._

/** The type of realizations of the theory http://latin.omdoc.org/literals?Float */
trait Float extends RealizationInScala with org.omdoc.latin.lf.literals.Arithmetic {
  override val _domain: TheoryScala = Float

  def Float_NaN: Arithmetic_num.univ
  realizes {function(Float.NaN.path, Arithmetic_num)(Float_NaN)}

  def Float_infinity: Arithmetic_num.univ
  realizes {function(Float.infinity.path, Arithmetic_num)(Float_infinity)}

}

/** Convenience functions for the MMT URIs of the declarations in the theory http://latin.omdoc.org/literals?Float
    along with apply/unapply methods for them */
object Float extends TheoryScala {
  val _base = DPath(utils.URI("http", "latin.omdoc.org") / "literals")
  val _name = LocalName("Float")

  object NaN extends ConstantScala {
    val parent = _path
    val name = "NaN"
  }

  object infinity extends ConstantScala {
    val parent = _path
    val name = "infinity"
  }

}
