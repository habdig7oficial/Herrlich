package models.Commands
import lib.DataStructs.Hashmap as HashMap
import lib.DataStructs.Queue as Queue
import kotlin.system.exitProcess

abstract class Command <Generic1, Generic2, Generic3> (
    val name: String,  
    var memory: HashMap<Generic1, Generic2>
){ 
    abstract var overload_min: Int;
    abstract var overload_max: Int;

    fun checkAndCall(vararg args : Generic3){
        if(args.size > this.overload_max || args.size < overload_min){
            throw Exception("Invalid Overload!\nExpected between ${overload_min} AND ${overload_max} But received ${args.size}")
        }
        this.call(*args)
    }

    abstract fun call(vararg args : Generic3): Unit

    override fun equals(other: Any?) : Boolean{
        if(other !is Command<*,*>)
            return false
        else if(other === this)
            return true

        else if((other as Command<*,*>).name  == this.name)
            return true  
        else 
            return false
 
    }
}   
  

class Vars <Generic1, Generic2> (
    name: String,
    memory: HashMap<Generic1, Generic2>
) : Command <Generic1, Generic2> (
    name, 
    memory
){
    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        print(memory)
    }
} 

class Reset <Generic1, Generic2>(
    name: String,
    memory: HashMap<Generic1, Generic2>
) : Command <Generic1, Generic2>(
    name, 
    memory
){
    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        this.memory.cleanAll()
    }
}

class Rec <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3>,
) : Command<Generic1, Generic2>(name, memory){

    var recMode : Boolean = false;

    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    fun load(expr: Generic3){
        queue.enqueue(expr)
 
        if(queue.isFull()){
            recMode = false
            println("Queue is Full. Disabling REC MODE")
        }    
    }

    fun length() : Int {
        return queue.length()
    }

    fun maxSize() : Int{
        return queue.maxSize()
    }

    override fun call(vararg args : String): Unit {
        if(!queue.isFull())
            this.recMode = true
        else{
            throw Exception("REC queue is full")
        }
    }
}

class Stop <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3>,
    var mode: Rec<Generic1, Generic2, Generic3>
) : Command<Generic1, Generic2>(name, memory){

    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        if(mode.recMode == true)    
            mode.recMode = false
        else
            throw Exception("\nNot in Rec Mode")
    }
}

class Erase <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3>,
) : Command<Generic1, Generic2>(name, memory){

    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        queue.clean()
    }
}

class Play <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3>,
    var mode: Rec<Generic1, Generic2, Generic3>
) : Command<Generic1, Generic2>(name, memory){

    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        println("All Commands Played")
    }
}

class Exit <Generic1, Generic2>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
) : Command<Generic1, Generic2>(
    name,
    memory
){
    override var overload_min: Int = 0;
    override var overload_max: Int = 0;

    override fun call(vararg args : String): Unit {
        println("Cleaning Memory...")
        this.memory.cleanAll()
        println("Thank you for using Herrlich Interpreter\nAuf wiedersehen!")

        exitProcess(0)
    }
}
 
/*
arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
*/


class Load <Generic1, Generic2>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
) : Command<Generic1, Generic2>(
    name,
    memory
){
    override var overload_min = 1
    override var overload_max = 1
    override fun call(vararg args : String): Unit {
        print("${this.overload_min}")
    }
}