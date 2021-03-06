package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    def set:Set = (x: Int) => x < 0
    assert(contains(set, -3))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("intersection of elements") {
    // 1 to 10
    def s1: Set = (x: Int) => x > 0 && x < 11
    assert(contains(s1, 1))
    assert(contains(s1, 10))
    assert(!contains(s1, 0))
    assert(!contains(s1, 11))
    assert(!contains(s1, -1))
    
    // 5 to 13    
    def s2: Set = (x: Int) => x > 4 && x < 14
    assert(contains(s2, 5))
    assert(contains(s2, 13))
    assert(!contains(s2, 0))
    assert(!contains(s2, 14))
    assert(!contains(s2, -1))
    
    val s = intersect(s1, s2)
    assert(contains(s, 5))
    assert(contains(s, 9))
    assert(contains(s, 10))
    assert(!contains(s, 1))
    assert(!contains(s, 13))
  }
  
  test("diff of elements") {
    // 1 to 10
    def s1: Set = (x: Int) => x > 0 && x < 11
    assert(contains(s1, 1))
    assert(contains(s1, 10))
    assert(!contains(s1, 0))
    assert(!contains(s1, 11))
    assert(!contains(s1, -1))
    
    // 5 to 13    
    def s2: Set = (x: Int) => x > 4 && x < 14
    assert(contains(s2, 5))
    assert(contains(s2, 13))
    assert(!contains(s2, 0))
    assert(!contains(s2, 14))
    assert(!contains(s2, -1))
    
    val s = diff(s1, s2)
    assert(contains(s, 1))
    assert(contains(s, 4))
    assert(!contains(s, 5))
    assert(!contains(s, 13))
    assert(!contains(s, 14))
  }
  
  test("filter of elements") {
    // 1 to 10
    def s1: Set = (x: Int) => x > 0 && x < 11
    assert(contains(s1, 1))
    assert(contains(s1, 10))
    assert(!contains(s1, 0))
    assert(!contains(s1, 11))
    assert(!contains(s1, -1))
    
    val f = filter(s1, (x: Int) => x > 5)
    assert(contains(f, 6))
    assert(contains(f, 10))
    assert(!contains(f, 5))
    assert(!contains(f, 4))
    assert(!contains(f, 11))
  }
  
  test("forAll of elements") {
    // -1000 to 1000
    def s1: Set = (x: Int) => x > -1001 && x < 1001
    assert(contains(s1, -1000))
    assert(contains(s1, -500))
    assert(contains(s1, 500))
    assert(contains(s1, 1000))
    assert(!contains(s1, -1001))
    assert(!contains(s1, 1001))
    assert(!contains(s1, 1003))
    
    assert(forall(s1, (x: Int) => x > -1001 && x < 1001))
    assert(!forall(s1, (x: Int) => x > 0 && x < 11))
  }
  
  test("exists of elements") {
    // -1 to 10
    def s1: Set = (x: Int) => x > 0 && x < 11
    assert(contains(s1, 1))
    assert(contains(s1, 5))
    assert(contains(s1, 9))
    assert(contains(s1, 10))
    assert(!contains(s1, 0))
    assert(!contains(s1, 11))
    assert(!contains(s1, 1000))
    
    assert(exists(s1, x => x == 1))
    assert(exists(s1, (x: Int) => x > 3 && x < 7))
    assert(!exists(s1, x => x == 11))
  }
  
  test("map of elements") {
    // -1 to 10
    def s1: Set = (x: Int) => x > 0 && x < 11
    assert(contains(s1, 1))
    assert(contains(s1, 5))
    assert(contains(s1, 9))
    assert(contains(s1, 10))
    assert(!contains(s1, 0))
    assert(!contains(s1, 11))
    assert(!contains(s1, 1000))
    
    def s2: Set = map(s1, (x: Int) => x + 2)
    
    assert(contains(s2, 5))
    assert(contains(s2, 9))
    assert(contains(s2, 10))
    assert(contains(s2, 12))
    assert(!contains(s2, 1))
    assert(!contains(s2, 0))
    assert(!contains(s2, 13))
    
  }
}
