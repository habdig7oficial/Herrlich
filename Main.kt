open class Interpreter{
    val reserved: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Char> = arrayOf('+', '-', '*', '/', 'ˆ', '%', '(', ')', '=')

    fun tokenize(textStream : String) : MutableList<String> {

        val tokens: MutableList<String> = mutableListOf("") 

        var i : Int = 0
        var ptr : Int = 0

        while(i < textStream.length){
            if(textStream[i] in reservedSymbols){
                tokens.add(textStream[i].toString())
            }
            else if(textStream[i].isLetterOrDigit() || textStream[i] == '.'){
                //tokens[tokens.size - 2] = tokens[ptr] + textStream[i]
                //print("${textStream[i]}")
                tokens.add(textStream[i].toString())
            }
            else if(textStream[i].isWhitespace() && tokens[if(ptr == 0) 0 else(ptr - 1)] != ""){
                tokens.add("")
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
            print(str.joinToString())
        }
    }
}

fun main(){
    val instance : Repl = Repl()
    instance.run()
}