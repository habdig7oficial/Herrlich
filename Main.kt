import lib.LinkedList.*

open class Interpreter{
    val reserved: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Char> = arrayOf('+', '-', '*', '/', 'ˆ', '%', '(', ')', '=')

    fun tokenize(textStream : String) : LinkedList<String> {

        val tokens: LinkedList<String> = LinkedList()   
        tokens.append("")

        var i : Int = 0
        while(i < textStream.length){
            if(textStream[i] in reservedSymbols){
                tokens.append(textStream[i].toString())
            }
            else if(textStream[i].isLetterOrDigit() || textStream[i] == '.'){
                //tokens[tokens.size - 2] = tokens[ptr] + textStream[i]
                //print("${textStream[i]}")
                tokens.append(textStream[i].toString())
            }
            else if(textStream[i].isWhitespace()){
                tokens.append("")
            }
            i++
        }

        return tokens
    }


}

class Repl : Interpreter() {
    fun run() : Unit {
        while(true) {
            print("> ")
            val str = tokenize(readln())
            //print(str.length)
            print(str)
        }
    }
}

fun main(){
    val instance : Repl = Repl()
    instance.run()
}