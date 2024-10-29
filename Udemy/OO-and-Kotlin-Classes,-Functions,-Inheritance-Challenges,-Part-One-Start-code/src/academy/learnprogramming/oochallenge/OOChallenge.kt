package academy.learnprogramming.oochallenge

 open class KotlinBicycle  (var cadence: Int,var speed: Int ,var gear:Int =5 )


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


class KotlinMountainBike (var seatHeight: Int, cadence: Int, speed: Int , gear:Int=5) :
KotlinBicycle(cadence, speed, gear) {
    constructor (seatHeight: Int, cadence: Int, speed: Int , gear: Int =5, color: String ):
            this(cadence, cadence, speed, gear) {}

    companion object {
        val availableColor= listOf("red", "green", "blue")
    }
    override fun printDescription(){
    super.printDescription()
    println(" Mountain bike is in gear $gear")

}
}

class KotlinRoadBike (val tireWidth: Int, cadence: Int, speed: Int, gear:Int=5) :
        KotlinBicycle(cadence, speed, gear) {

    override fun printDescription(){
        super.printDescription()
      println(" Road bike is in gear $gear")
    }
        }


fun main(args: Array<String>) {

    val kotlinBicycle =  KotlinBicycle(50,200,6)
    val kotlinMountainBike = KotlinMountainBike(30, 70,155,4)
    val kotlinRoadBike = KotlinRoadBike(40, 60,250,4)

    val kotlinBicycle2 =  KotlinBicycle(50,200)
    val kotlinMountainBike2 = KotlinMountainBike(30, 70,155)
    val kotlinRoadBike2 = KotlinRoadBike(40, 60,250)

    val kotlinMountainBike3 = KotlinMountainBike(30, 70,55, color="Red")
KotlinMountainBike.availableColor.forEach { println(it) }
    kotlinBicycle.printDescription()
    kotlinMountainBike.printDescription();
    kotlinRoadBike.printDescription();

    kotlinBicycle2.printDescription();
    kotlinMountainBike2.printDescription();
    kotlinRoadBike2.printDescription();
}