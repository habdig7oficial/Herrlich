import lib.LinkedList.*

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
                tokens.append(textStream[i].toString())
                op = true
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
                tokens.append("")
            }
            i++
        }

        return tokens
    }

    fun parse(){

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