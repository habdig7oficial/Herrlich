import lib.DataStructs.*

class Symbol(
    val op: Char,
    val priority: Int 
)

open class Interpreter{
    val reservedTokens: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Symbol> = arrayOf(Symbol('+', 1), Symbol('-', 1), Symbol('*', 2), Symbol('/', 2), Symbol('^', 3), Symbol('%', 2), Symbol('(', 4), Symbol(')', 4), Symbol('=', 1))

    operator fun Array<Symbol>.contains(value: Char) : Boolean {
        return this.any { it.op == value }
    }


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
        var stack: Stack<Symbol> = Stack<Symbol>()
        var polishNotation: LinkedList<String> = LinkedList<String>()
        var stmt: Node<String>? = expr.getFirst()
        var i: Int;
        //requireNotNull(stmt)
        while(stmt != null){

            i = stmt.element.let {
                reservedSymbols.indexOfFirst { symbl: Symbol -> 
                    println(it[0])
                    it[0] == symbl.op  
                }
            }

           if( i != -1 ){
                print("${stmt.element},")
                polishNotation.append(stmt.element)
           }
           else{
                stack.push(reservedSymbols[i])
           }
        
           stmt = stmt.next
        }
        print(polishNotation)
        print(stack)

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