package image

import org.scalatest.FunSuite

class StackTests extends FunSuite {
  val e1 = EllipseFilled(Color.deepSkyBlue, 70, 40)
  val e2 = EllipseFilled(Color.darkGray, 20, 80)
  
  test("centered") {
    assert(e1.stackOn(e2).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2.png")))
    assert(e2.slideUnder(e1).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2.png")))
    assert(e2.stackOn(e1).sameBitmap(Bitmap.fromWorkspace("/stacked/e2-e1.png")))
    assert(e1.slideUnder(e2).sameBitmap(Bitmap.fromWorkspace("/stacked/e2-e1.png")))
    assert(e1.stackOn(e2, 20, 10).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2-20-10.png")))
  }
  
  test("aligns") {
    assert(e1.stackOn(e2, XAlign.left, YAlign.bottom).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2-left-bottom.png")))
    assert(e2.stackOn(e1, XAlign.center, YAlign.top).sameBitmap(Bitmap.fromWorkspace("/stacked/e2-e1-center-top.png")))
    assert(e1.stackOn(e2, XAlign.right, YAlign.center).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2-right-center.png")))
  }

}