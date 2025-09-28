package internals 

import java.io.File
import internals.Interpreter

class Repl : Interpreter() {

    val LOG_PATH = "./log"
    val log = File(LOG_PATH)

    fun run() : Unit {
        while(true) {

            if(this.recWrapper.recMode){
               print("REC ${this.recWrapper.length()} / ${this.recWrapper.maxSize()}") 
            } 

            print("> ")
            val str = this.tokenize(readln())
            //print(str.length)

            log.appendText(str.toString())   

            val polish = this.parser(str)
            log.appendText("\n\n${polish.toString()}\n") 

            val res = try{
                //this.interprete(polish)
            }
            catch(err: Throwable){
                println("\n${err.message}") 
                continue
            }
            println(res) 
            log.appendText(res.toString()) 
            log.appendText("\n-------------------\n")
        } 
    }
}