import lib.LinkedList.*
import java.util.Stack /* LEMBRAR DE REMOVER ISSO */

open class Interpreter{
    val reservedTokens: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Char> = arrayOf('+', '-', '*', '/', 'ˆ', '%', '(', ')', '=')

    fun tokenize(textStream : String) : LinkedList<String> {

        val tokens: LinkedList<String> = LinkedList()   
        tokens.append("")

        var i : Int = 0
        var op: Boolean = false

        while(i < textStream.length){
            if(textStream[i] in reservedSymbols){
                op = true
                if(tokens.getLeaf()?.element == ""){
                    tokens.getLeaf()?.element += textStream[i].toString()
                }
                else{
                    tokens.append(textStream[i].toString())
                }
            }
            else if(textStream[i].isLetterOrDigit() || textStream[i] == '.'){
                //tokens[tokens.size - 2] = tokens[ptr] + textStream[i]
                //print("${textStream[i]}")
                if(op == false){
                    tokens.getLeaf()?.element += textStream[i].toString()
                }
                else{
                    tokens.append(textStream[i].toString())
                    op = false
                }
            }
            else if(textStream[i].isWhitespace() && !textStream[i - 1].isWhitespace()){
                if(tokens.getLeaf()?.element !== ""){
                    tokens.append("")
                }
            }
            i++
        }

        return tokens
    }

    fun parser(expr: LinkedList<String>){
        var stack: Stack<String> = Stack<String>()
        var polishNotation: LinkedList<String> = LinkedList<String>()
        var stmt: Node<String>? = expr.getRoot()
        //requireNotNull(stmt)
        while(stmt != null){
            polishNotation.append(stmt.element)
            print(stmt.element!!.get(0) !in reservedSymbols)
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