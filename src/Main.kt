import solution.*
import java.lang.RuntimeException
import kotlin.collections.ArrayList


fun str_to_array(pos: String): ArrayList<Int>{
    if (pos.length != 16){
        println("Wrong sequence")
        return arrayListOf(0)
    }
    var ar = arrayListOf<Int>()
    for (i in 0..15){
        when (pos[i]){
            'A' -> ar.add(10)
            'B' -> ar.add(11)
            'C' -> ar.add(12)
            'D' -> ar.add(13)
            'E' -> ar.add(14)
            'F' -> ar.add(15)
            else -> ar.add(pos[i].toString().toInt())
        }
    }
    return ar
}

fun main(args: Array<String>){
    print("Enter start position:")
    val pos = readLine()!!.toString()
    val pyatnashki_a = Solution(str_to_array(pos),0,null)
    val pyatnashki_ida = Solution(str_to_array(pos),0,null)
    var start = System.nanoTime()
    //var start_mem = Runtime.getRuntime().freeMemory()
    println("A* algorithm: ")
    pyatnashki_a.get_right_position()!!.print_way()
    var finish = System.nanoTime()
   // var finish_mem = Runtime.getRuntime().freeMemory()
    //println((start_mem - finish_mem / 1048576.0).toString() + " MB")
    println(((finish - start)/1000000000.0).toString() + " sec")


    println()
    println("IDA* algorithm: ")
    start = System.nanoTime()
    //start_mem = Runtime.getRuntime().freeMemory()
    pyatnashki_a.get_right_position_ida()!!.print_way()
    finish = System.nanoTime()
    //finish_mem = Runtime.getRuntime().freeMemory()
   // println((start_mem - finish_mem / 1048576.0).toString() + " MB")
    println(((finish - start)/1000000000.0).toString() + " sec")
}