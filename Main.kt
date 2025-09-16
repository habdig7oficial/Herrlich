import lib.LinkedList.*
import java.util.Stack /* LEMBRAR DE REMOVER ISSO */

open class Interpreter{
    val reservedTokens: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Char> = arrayOf('+', '-', '*', '/', 'ˆ', '%', '(', ')', '=')

    fun tokenize(textStream : String) : LinkedList<String> {

        val tokens: LinkedList<String> = LinkedList()   

        var i : Int = 0
        var shouldEnd: Boolean = true

        while(i < textStream.length){
            if(textStream[i] in reservedSymbols){
                if(shouldEnd)
                    tokens.append(textStream[i].toString())
                else
                    tokens.getLast()?.element += textStream[i].toString()

                shouldEnd = true
            }
            else if(textStream[i].isLetterOrDigit() || textStream[i] == '.'){
                //tokens[tokens.size - 2] = tokens[ptr] + textStream[i]
                //print("${textStream[i]}")
                if(shouldEnd){
                    tokens.append(textStream[i].toString())
                    shouldEnd = false
                }   
                else
                    tokens.getLast()?.element += textStream[i].toString()
            }
            else if(textStream[i].isWhitespace()){
                shouldEnd = true
            }
            i++
        }

        return tokens
    }

    fun parser(expr: LinkedList<String>){
        var stack: Stack<String> = Stack<String>()
        var polishNotation: LinkedList<String> = LinkedList<String>()
        var stmt: Node<String>? = expr.getLast()
        //requireNotNull(stmt)
        while(stmt != null){
            polishNotation.append(stmt.element)
           // print(stmt.element!!.get(0) !in reservedSymbols)
           if(true){
                print("\n")
           }
           stmt = stmt.next
        }
        print(polishNotation)

    }
}

class Repl : Interpreter() {
    fun run() : Unit {
        while(true) {
            print("> ")
            val str = this.tokenize(readln())
            //print(str.length)
            print(str)

            this.parser(str)
        }
    }
}

fun main(){
    val instance : Repl = Repl()
    instance.run()
}