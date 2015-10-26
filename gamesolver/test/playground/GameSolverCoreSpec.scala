package playground

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import playground.domain.{Point, Field}

@RunWith(classOf[JUnitRunner])
class GameSolverCoreSpec extends Specification {
  "Field" should {
    "have properly implemented equals, hashCode and toString methods" in {
      val field1 = Field(List(List(0, 0, 1), List(1, 0, 1), List(0, 0, 0)))
      val field2 = Field(List(List(1, 1, 1), List(1, 0, 1), List(0, 0, 0)))
      val field3 = Field(List(List(0, 0, 1), List(1, 0, 1), List(0, 0, 0)))
      (field1 == field3) must beTrue
      (field1 == field2) must beFalse
      (field1.## == field3.## && field1.## != field2.##) must beTrue
      field1.toString mustEqual """Field {
                                  |  0 0 1
                                  |  1 0 1
                                  |  0 0 0
                                  |}""".stripMargin
    }

    "properly insert new points" in {
      val field = Field(List(List(1, 1, 1), List(1, 0, 1), List(0, 0, 0)))

      field.insert(Point((0, 0), 1)) must beNone
      field.insert(Point((2, 1), 1)) must
        beSome(Field(List(List(1, 1, 1), List(1, 0, 1), List(0, 1, 0))))

      val afterInsert1 = field.insert(List(Point((0, 1), 1), Point((1, 1), 1)))
      val afterInsert2 = field.insert(List(Point((1, 1), 1), Point((2, 0), 1),
        Point((2, 1), 1), Point((2, 2), 1)))

      afterInsert1 must beNone
      afterInsert2 must beSome(Field(List(List(1, 1, 1), List(1, 1, 1), List(1, 1, 1))))
    }
  }

  "Util methods" >> {
    "moveRight and moveDown do their job" in {
      val figure = List(Point((0, 0), 1), Point((0, 1), 1), Point((1, 0), 1))
      val movedRight = util.moveRight(figure)
      val andDown = util.moveDown(movedRight)
      movedRight mustEqual List(Point((0, 1), 1), Point((0, 2), 1), Point((1, 1), 1))
      andDown mustEqual List(Point((1, 1), 1), Point((1, 2), 1), Point((2, 1), 1))
    }
  }
}
