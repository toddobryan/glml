package image

import org.scalatest.FunSuite
import math.Pi

class PointTests extends FunSuite {
	val p1 = Point(5,10)
	val p2 = Point(-3, 7)
	val p3 = PointPolar(2, 90.degrees)
	val p4 = PointPolar(4, (3 * scala.math.Pi).radians)
	
	test("point translation") {
	  assert(p1.translate(2.4, 8.1) === Point(7.4, 18.1))
	  assert(p2.translate(0, 21) === Point(-3, 28))
	  assert(p1.translate(-3.5, -5.1) === Point(1.5, 4.9))
	}
	
	test("polar point conversion") {
	  assert(p3.toCartesian === Point(0, 2))
	  assert(p4.toCartesian === Point(-4, 0))
	}
}