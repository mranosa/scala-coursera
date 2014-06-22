package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  
  //print all even numbers from s1
  def s1: Set = (x: Int) => x > 0 && x < 11
  def s2: Set = (x: Int) => forall(s1, y => x==y && y%2 == 0)
  def s3: Set = (x: Int) => exists(s1, y => x==y && y%2 == 0)
  
  printSet(s1)
  printSet(s2)
  printSet(s3)
}
