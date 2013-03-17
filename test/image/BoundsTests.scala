package image

import org.scalatest.FunSuite

class BoundsTests extends FunSuite {
  val e1 = EllipseFilled(Color.black, 60, 20)
  val e2 = EllipseFilled(Color.blue, 30, 50)
  
  /*test("bound translation") {
    assert(e1.bounds.translate(20, 30) === Bounds(Point(20, 30), Point(80, 50)))
    assert(e2.bounds.translate(-10, -20) === Bounds(Point(-10, -20), Point(20, 30)))
    assert(e1.stackOn(e2).bounds.translate(5, 10) === Bounds(Point(5, 10), Point(65, 60)))
  }
  
  test("centered") {
    assert(e1.stackOn(e2).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, 10, 0).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, 20, 0).bounds === Bounds(Point(0, 0), Point(65, 50)))
    assert(e1.stackOn(e2, 30, 0).bounds === Bounds(Point(0, 0), Point(75, 50)))
    assert(e1.stackOn(e2, 0, 10).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, 0, 20).bounds === Bounds(Point(0, 0), Point(60, 55)))
  }
  
  test("left aligned") {
    assert(e1.stackOn(e2, XAlign.left, YAlign.center).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, XAlign.left, YAlign.center, 10, 0).bounds === Bounds(Point(0,0), Point(70, 50)))
    assert(e1.stackOn(e2, XAlign.left, YAlign.center, 20, 0).bounds === Bounds(Point(0,0), Point(80, 50)))
    assert(e1.stackOn(e2, XAlign.left, YAlign.center, -10, 0).bounds === Bounds(Point(0,0), Point(60, 50)))
  }
  
  test("right aligned") {
    assert(e1.stackOn(e2, XAlign.right, YAlign.center).bounds === Bounds(Point(0,0), Point(60, 50)))
    assert(e1.stackOn(e2, XAlign.right, YAlign.center, 10, 0).bounds === Bounds(Point(0,0), Point(60, 50)))
    assert(e1.stackOn(e2, XAlign.right, YAlign.center, -10, 0).bounds === Bounds(Point(0,0), Point(70, 50)))
    assert(e1.stackOn(e2, XAlign.right, YAlign.center, -20, 0).bounds === Bounds(Point(0,0), Point(80, 50)))
  }
  
  // switching up e1 and e2 stack order so the ellipse with the greater vertical axis is offset
  test("top aligned") {
    assert(e2.stackOn(e1, XAlign.center, YAlign.top).bounds === Bounds(Point(0,0), Point(60, 50)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.top, 0, 10).bounds === Bounds(Point(0,0), Point(60, 60)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.top, 0, 20).bounds === Bounds(Point(0,0), Point(60, 70)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.top, 0, -10).bounds === Bounds(Point(0,0), Point(60, 50)))
  }
  
  test("bottom aligned") {
    assert(e2.stackOn(e1, XAlign.center, YAlign.bottom).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.bottom, 0, 10).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.bottom, 0, -10).bounds === Bounds(Point(0, 0), Point(60, 60)))
    assert(e2.stackOn(e1, XAlign.center, YAlign.bottom, 0, -20).bounds === Bounds(Point(0, 0), Point(60, 70)))
  }*/
}