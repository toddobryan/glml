package image

import org.scalatest.FunSuite

import math.Pi

import java.io.File


class ShapeTests extends FunSuite {
  test("circles") {
    assert(CircleFilled(Color.aquamarine, 50).sameBitmap( 
        Bitmap.fromWorkspace("/shapes/circle-50-aquamarine.png")))
    assert(CircleOutlined(Color.black, 10).sameBitmap(
        Bitmap.fromWorkspace("/shapes/circle-10-black.png")))
    assert(CircleOutlined(Pen(Color.blue, 5), 25).sameBitmap(
        Bitmap.fromWorkspace("/shapes/circle-25-blue.png")))
  }
  
  test("ellipses") {
    assert(EllipseFilled(Color.blueViolet, 40, 80).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ellipse-40-80-blueViolet.png")))
    assert(EllipseOutlined(Color.goldenrod, 80, 20).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ellipse-80-20-goldenrod.png")))
    assert(EllipseOutlined(Pen(Color.pink, 20), 100, 50).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ellipse-100-50-pink.png")))
  }
  
  test("wedges") {
    assert(CircularArc(Pen(Color.green, 3), 50, 30.degrees, 120.degrees).sameBitmap(
        Bitmap.fromWorkspace("/shapes/arc-50-30-120-green.png")))
    assert(CircularSegment(Color.cyan, 25, -20.degrees, 220.degrees).sameBitmap(
        Bitmap.fromWorkspace("/shapes/seg-25-20-220-cyan.png")))
    assert(CircularWedge(Color.red, 30, (Pi / 6).radians, (2 * Pi / 3).radians).sameBitmap(
        Bitmap.fromWorkspace("/shapes/wedge-30-30-120-red.png")))
  }
  
  test("elliptical wedges") {
    assert(EllipticalArc(Pen(Color.fireBrick, 3), 50, 80, 50.degrees, 120.degrees).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ell-arc-50-80-50-120-brick.png")))
    assert(EllipticalSegment(Color.tomato, 35, 25, -20.degrees, 270.degrees).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ell-seg-35-25-20-270-tomato.png")))
    assert(EllipticalWedge(Color.orange, 30, 100, (Pi / 6).radians, (3 * Pi / 4).radians).sameBitmap(
        Bitmap.fromWorkspace("/shapes/ell-wedge-30-100-30-135-orange.png")))
  }
  
  test("polygonal") {
    assert(Polygon(Color.burlywood, Point(0, 0), Point(-10, 20), Point(60, 0), Point(-10, -20)).sameBitmap(
        Bitmap.fromWorkspace("/shapes/polygon-arrow.png")))
    assert(Polygon(Color.plum, Point(0, 0), Point(0, 40), Point(20, 40), 
            Point(20, 60), Point(40, 60), Point(40, 20), Point(40, 20), 
            Point(20, 20), Point(20, 0)).sameBitmap(
        Bitmap.fromWorkspace("/shapes/polygon-plum-tetris.png")))
  }
  
  test("rectangle") {
    assert(RectangleFilled(Color.aquamarine, 100, 70).sameBitmap(
        Bitmap.fromWorkspace("/shapes/rectangle-100-70-aquamarine.png")))
    assert(RectangleOutlined(Color.red, 30, 50).sameBitmap(
        Bitmap.fromWorkspace("/shapes/rectangle-30-50-red.png")))
    assert(RectangleFilled(Color.maroon, 60, 80).sameBitmap(
        Bitmap.fromWorkspace("/shapes/rectangle-60-80-maroon.png")))
  }
  
  test("square") {
    assert(SquareFilled(Color.green, 30).sameBitmap(
        Bitmap.fromWorkspace("/shapes/square-30-green.png")))
    assert(SquareFilled(Color.purple, 60).sameBitmap(
        Bitmap.fromWorkspace("/shapes/square-60-purple.png")))
    assert(SquareOutlined(Color.black, 70).sameBitmap(
        Bitmap.fromWorkspace("/shapes/square-70-black.png")))
  }
  
}