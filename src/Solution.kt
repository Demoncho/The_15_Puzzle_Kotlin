package solution

import java.util.*
import kotlin.collections.ArrayList

fun ArrayList<Int>.swap(i: Int, j: Int): ArrayList<Int>{
    val result =  this
    val temp = result[i]
    result[i] = result[j]
    result[j] = temp
    return result
}

fun swap_pos(src: ArrayList<Int>, i: Int, j: Int): ArrayList<Int>{
    val result = src
    val temp = result[i]
    result[i] = result[j]
    result[j] = temp
    return result
}

fun ArrayList<Int>.toMyString():String{
    var result = ""
    for (i in 0..15){
        when (this[i]){
            10 -> result += "A"
            11 -> result += "B"
            12 -> result += "C"
            13 -> result += "D"
            14 -> result += "E"
            15 -> result += "F"
            else -> result += this[i].toString()
        }
    }
    return result
}

class Solution(val pos: ArrayList<Int>, val level: Int, val parent: Solution?): Comparable<Solution>{

    private fun check_position(): Boolean{
        if (pos.size != 16){
            println("Wrong sequence")
            return false
        }

        val a = pos.indexOf(0) / 4 + 1
        var sum = 0
        for (i in 0..15) {
            if (pos[i] != 0)
                sum += pos.takeLast(15 - i).filter { it!=0 }.count { it < pos[i] }
        }
        if ((sum + a) % 2 == 0)
            return true
        return false
    }

    private fun emp_func(pos: ArrayList<Int>): Int{
        var sum = 0
        for (i in 0..15){
            if (pos[i]!=i+1 && pos[i] != 0)
                sum += Math.abs((i / 4) - ((pos[i] - 1) / 4)) + Math.abs((i % 4) - ((pos[i] - 1) % 4))
        }
        return sum
    }

    override fun compareTo(other: Solution): Int {
        return  (emp_func(this.pos) + this.level) - (emp_func(other.pos) + other.level)
        /*if ((emp_func(this.pos) + this.level) > (emp_func(other.pos) + other.level)) return 1
        if ((emp_func(this.pos) + this.level) < (emp_func(other.pos) + other.level)) return -1
        return 0*/
    }

    override fun toString(): String {
        var result = ""
        for (i in 0..15){
            when (pos[i]){
                10 -> result += "A"
                11 -> result += "B"
                12 -> result += "C"
                13 -> result += "D"
                14 -> result += "E"
                15 -> result += "F"
                else -> result += pos[i].toString()
            }
        }
        return result

    }

     fun print_way(){
        if (this == null)
            return

        if (parent != null)
            parent.print_way()

        if (this.toString() != "123456789ABCDEF0")
            print(this.toString() + " -> ")
        else {
            println(this.toString())
            this.print_lenth()
        }
    }

    private fun print_lenth(){
        if (this == null)
            return
        var l = 0
        var a = this
        while (a.parent != null){
            l++
            a = a.parent!!
        }
        println("Count of steps: " + l.toString())
    }

    fun get_right_position(): Solution? {

        if (!this.check_position()) {
            println("No way")
            return null
        }

        if (this.toString() == "123456789ABCDEF0")
            return this

        val sequence_of_steps = PriorityQueue<Solution>()
        val repeated_steps = hashSetOf<ArrayList<Int>>()


        //repeated_steps.add(this.pos)

        val checklist = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0)

        var cur_step = this

        while(cur_step.pos != checklist) {
            if (!repeated_steps.contains(cur_step.pos)) {
                repeated_steps.add(cur_step.pos)
                val pos0 = cur_step.pos.indexOf(0)
                if (pos0 - 4 >= 0) {
                    val temp_pos1 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 - 4)
                    //val temp_pos1 = swap_pos(cur_step.pos, pos0, pos0 - 4)
                    if (!repeated_steps.contains(temp_pos1)) {
                        //repeated_steps.add(temp_pos1)
                        sequence_of_steps.add(Solution(temp_pos1, cur_step.level + 1, cur_step))
                    }
                }
                if (pos0 + 4 <= 15) {
                    val temp_pos2 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 + 4)
                    //val temp_pos2 = swap_pos(cur_step.pos, pos0, pos0 + 4)
                    if (!repeated_steps.contains(temp_pos2)) {
                        //repeated_steps.add(temp_pos2)
                        sequence_of_steps.add(Solution(temp_pos2, cur_step.level + 1, cur_step))
                    }
                }
                if (pos0 % 4 != 0) {
                    val temp_pos3 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 - 1)
                    //val temp_pos3 = swap_pos(cur_step.pos, pos0, pos0 - 1)
                    if (!repeated_steps.contains(temp_pos3)) {
                       // repeated_steps.add(temp_pos3)
                        sequence_of_steps.add(Solution(temp_pos3, cur_step.level + 1, cur_step))
                    }
                }
                if (pos0 % 4 != 3) {
                    val temp_pos4 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 + 1)
                    //val temp_pos4 = swap_pos(cur_step.pos, pos0, pos0 + 1)
                    if (!repeated_steps.contains(temp_pos4)) {
                        //repeated_steps.add(temp_pos4)
                        sequence_of_steps.add(Solution(temp_pos4, cur_step.level + 1, cur_step))
                    }
                }
            }
            cur_step = sequence_of_steps.poll()
        }
        return cur_step
    }

    fun get_right_position_ida(): Solution?{
        if (!this.check_position()) {
            println("No way")
            return null
        }

        if (this.toString() == "123456789ABCDEF0")
            return this

        val sequence_of_steps = PriorityQueue<Solution>()
        val repeated_steps = hashSetOf<ArrayList<Int>>()

        val checklist = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0)

        var depth = emp_func(this.pos)

        while (true){
            repeated_steps.clear()
            sequence_of_steps.add(this)
            while(!sequence_of_steps.isEmpty()){
                val cur_step = sequence_of_steps.poll()

                if (cur_step.pos == checklist) return cur_step

                if (emp_func(cur_step.pos) + cur_step.level <= depth) {

                    if (!repeated_steps.contains(cur_step.pos)) {
                        repeated_steps.add(cur_step.pos)
                        val pos0 = cur_step.pos.indexOf(0)
                        if (pos0 - 4 >= 0) {
                            val temp_pos1 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 - 4)
                            //val temp_pos1 = swap_pos(cur_step.pos, pos0, pos0 - 4)
                            if (!repeated_steps.contains(temp_pos1)) {
                                //repeated_steps.add(temp_pos1)
                                sequence_of_steps.add(Solution(temp_pos1, cur_step.level + 1, cur_step))
                            }
                        }
                        if (pos0 + 4 <= 15) {
                            val temp_pos2 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 + 4)
                            //val temp_pos2 = swap_pos(cur_step.pos, pos0, pos0 + 4)
                            if (!repeated_steps.contains(temp_pos2)) {
                                //repeated_steps.add(temp_pos2)
                                sequence_of_steps.add(Solution(temp_pos2, cur_step.level + 1, cur_step))
                            }
                        }
                        if (pos0 % 4 != 0) {
                            val temp_pos3 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 - 1)
                            //val temp_pos3 = swap_pos(cur_step.pos, pos0, pos0 - 1)
                            if (!repeated_steps.contains(temp_pos3)) {
                                // repeated_steps.add(temp_pos3)
                                sequence_of_steps.add(Solution(temp_pos3, cur_step.level + 1, cur_step))
                            }
                        }
                        if (pos0 % 4 != 3) {
                            val temp_pos4 = (cur_step.pos.clone() as ArrayList<Int>).swap(pos0, pos0 + 1)
                            //val temp_pos4 = swap_pos(cur_step.pos, pos0, pos0 + 1)
                            if (!repeated_steps.contains(temp_pos4)) {
                                //repeated_steps.add(temp_pos4)
                                sequence_of_steps.add(Solution(temp_pos4, cur_step.level + 1, cur_step))
                            }
                        }
                    }
                }



            }
            depth += 1
        }
    }
}