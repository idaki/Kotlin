package academy.learnprogramming.oochallenge

 open class KotlinBicycle  (var cadence: Int,var speed: Int ,var gear:Int )

 {
     fun applyBrake(decrement: Int) {
         speed -= decrement
     }

     fun speedUp(increment: Int) {
         speed += increment
     }

    open fun printDescription() {
         println(
             "Bike is in gear $gear with a cadence $cadence" +
                     " traveling at $speed."
         )
     }
 }


class KotlinMountainBike (var seatHeight: Int, cadence: Int, speed: Int , gear:Int) :
KotlinBicycle(cadence, speed, gear) {

    override fun printDescription(){
    super.printDescription()
    println(" Mountain bike is in gear $gear")

}
}

class KotlinRoadBike (val tireWidth: Int, cadence: Int, speed: Int, gear:Int) :
        KotlinBicycle(cadence, speed, gear) {

    override fun printDescription(){
        super.printDescription()
      println(" Road bike is in gear $gear")
    }
        }

