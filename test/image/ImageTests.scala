package image

import org.scalatest.FunSuite

class ImageTests extends FunSuite {
  val cat = Bitmap.fromWorkspace("/bitmaps/cat.png")
  val pizza = Bitmap.fromWorkspace("/bitmaps/pizza.png")
  
  test("flip") {
    assert(cat.flipHorizontal.sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-flip-hor.png")))
    assert(cat.flipHorizontal.flipHorizontal.sameBitmap(cat))
    assert(cat.flipVertical.sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-flip-ver.png")))
    
    assert(pizza.flipHorizontal.sameBitmap(Bitmap.fromWorkspace("/bitmaps/pizza-flip-hor.png")))
    assert(pizza.flipVertical.sameBitmap(Bitmap.fromWorkspace("/bitmaps/pizza-flip-ver.png")))
    assert(pizza.flipVertical.flipVertical.sameBitmap(pizza)) 
  }
  
  test("scale") {
    assert(cat.scale(3,3).sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-scale3x3y.png")))
    assert(cat.scale(.5,.5).sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-scale.5x.5y.png")))
    assert(cat.scaleX(3).sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-scalex3.png")))
    assert(cat.scaleY(3).sameBitmap(Bitmap.fromWorkspace("/bitmaps/cat-scaley3.png")))
  }
  
}