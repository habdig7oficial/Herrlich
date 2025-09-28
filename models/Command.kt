package models.Commands
import lib.DataStructs.Hashmap as HashMap
import lib.DataStructs.Queue as Queue
import kotlin.system.exitProcess

abstract class Command <Generic1, Generic2> (
    val name: String,  
    var memory: HashMap<Generic1, Generic2>
){ 
    abstract fun call(): Unit

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
    override fun call(){
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
    override fun call() : Unit {
        this.memory.cleanAll()
    }
}

class Rec <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3> 
) : Command<Generic1, Generic2>(name, memory){

    var recMode : Boolean = false;

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

    override fun call() : Unit{
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

    override fun call() : Unit{
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
    val newSize: Int
) : Command<Generic1, Generic2>(name, memory){

    override fun call() : Unit{
        queue.clean()
    }
}

class Play <Generic1, Generic2, Generic3>(
    name: String,
    memory: HashMap<Generic1, Generic2>,
    var queue: Queue<Generic3>,
    var mode: Rec<Generic1, Generic2, Generic3>
) : Command<Generic1, Generic2>(name, memory){

    override fun call() : Unit{
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
    override fun call() : Unit{
        println("Cleaning Memory...")
        this.memory.cleanAll()
        println("Thank you for using Herrlich Interpreter\nAuf wiedersehen!")

        exitProcess(0)
    }
}
 
/*
arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
*/