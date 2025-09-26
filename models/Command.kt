package models.Commands
import lib.DataStructs.Hashmap as HashMap

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
  

class Vars <Generic1, Generic2> (name: String, memory: HashMap<Generic1, Generic2>) : Command<Generic1, Generic2>(name, memory){
    override fun call(){
        print(memory)
    }
} 


/*
arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
*/