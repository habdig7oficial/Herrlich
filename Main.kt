import lib.DataStructs.*
import java.io.File
import Symbol



open class Interpreter{
    val reservedTokens: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Symbol> = arrayOf(Add('+'), Sub('-'), Mul('*'), Div('/'), Pow('^'), Mod('%'), BracketOpen('('), BracketClose(')'),  Attribute('='))
 
    operator fun Array<Symbol>.contains(value: Char) : Boolean {
        return this.any { it.op == value }
    }


    fun tokenize(textStream : String) : LinkedList<String> {

        val tokens: LinkedList<String> = LinkedList()   

        var i : Int = 0
        var shouldEnd: Boolean = true

        while(i < textStream.length){
            if(textStream[i] in reservedSymbols){
                tokens.append(textStream[i].toString())
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

    fun parser(expr: LinkedList<String>) : LinkedList<String> {
        var stack: Stack<Symbol> = Stack<Symbol>()
        var polishNotation: LinkedList<String> = LinkedList<String>()
        var stmt: Node<String>? = expr.getFirst()
        var i: Int 
        //requireNotNull(stmt)
        while(stmt != null){
            i = stmt.element.let { 
                reservedSymbols.indexOfFirst { symbl: Symbol ->
                    it[0] == symbl.op  
                 } 
             }
           if( i != -1 ){
               // println(stack) 

                if(reservedSymbols[i].op == '('){
                    stack.push(reservedSymbols[i]) 
                    stmt = stmt.next 
                    continue
                }
                else if(reservedSymbols[i].op == ')'){
                    println("\nsymbl ${stack}") 
                    if(stack.isEmpty())
                        throw Exception("Paretisis closed but not opend")  

                    var symbl = stack.top().op
                    while( symbl  != '('){ 
                        println("\nsymbl ${stack.top()}") 
                        polishNotation.append(stack.pop().op.toString())

                       if(!stack.isEmpty())
                            symbl = stack.top().op
                        else 
                            break
                    }
                    if(!stack.isEmpty() && stack.top().op == '(')
                        stack.pop()
                    println("\nFINAL ${stack}") 
                
                    stmt = stmt.next 
                    continue  
                }
        
                var top: Symbol = try{ 
                    stack.top()
                }
                catch(err: Throwable){
                    NullSymbl('\u0000') 
                }

                while(!stack.isEmpty() && top.priority >= reservedSymbols[i].priority){ 
                    //println(stack)
   
                    polishNotation.append(stack.pop().op.toString())
   
                    top  = try{ 
                        stack.top()
                    }
                    catch(err: Throwable){
                        NullSymbl('\u0000') 
                    }
                }
                stack.push(reservedSymbols[i])  
           }
           else{
               print("${stmt.element},") 
                polishNotation.append(stmt.element)
           }
            
           stmt = stmt.next
        }
        
        while(!stack.isEmpty())
            polishNotation.append(stack.pop().op.toString())  
        

        print("Polish Notation: ${polishNotation}")

        return polishNotation 

    }

    fun interprete(expr: LinkedList<String>) : Double {
        val memory: Hashmap<String, Double>
        var execStack: Stack<Double> = Stack()   

        var stmt = expr.getFirst() 
        var i: Int 
        while(stmt != null){
            i = stmt.element.let { 
                reservedSymbols.indexOfFirst { symbl: Symbol ->
                    it[0] == symbl.op  
                 } 
             }

            val checkOp = try{
                reservedSymbols[i].op.toString() 
            }
            catch(err : Throwable){
                "\u0000" 
            } 
             
            if(stmt.element == checkOp){
                var (v2, v1) = try{
                    arrayOf(execStack.pop(), execStack.pop()) 
                }
                catch(err: Throwable){
                    throw Exception("Not enough elements to operate")
                }
                execStack.push(reservedSymbols[i].operate(v1, v2))
                println("\n${execStack.top()}") 
            }
            else if(stmt.element !in reservedTokens){
                var getValue: Double? = stmt.element.toDoubleOrNull()
                if(getValue == null){
                    stmt = stmt.next
                    continue 
                }
                execStack.push(getValue)
            }
            stmt = stmt.next
        }
        try{
            return execStack.top() 
        }
        catch(err: Throwable){
            return 0.0  
        }
    }

}

class Repl : Interpreter() {

    val LOG_PATH = "./log"
    val log = File(LOG_PATH)

    fun run() : Unit {
        while(true) {
            print("> ")
            val str = this.tokenize(readln())
            //print(str.length)

            log.appendText(str.toString())   

            val polish = this.parser(str)
            log.appendText("\n\n${polish.toString()}\n") 
            log.appendText(this.interprete(polish).toString()) 
            log.appendText("\n-------------------\n")

            
            
        }
    }
}

fun main(){
    val instance : Repl = Repl()
    instance.run()
}