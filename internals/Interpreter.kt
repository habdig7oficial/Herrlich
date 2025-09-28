package internals

import lib.DataStructs.*
import models.Symbols.*
import models.Commands.*
import java.lang.Exception


open class Interpreter {   
    var memory: Hashmap<String, Double> = Hashmap(); private set
    var cmdQueue: Queue<LinkedList<String>> = Queue(10); private set 

    val recWrapper = Rec("REC", memory, cmdQueue, arrayOf("REC","STOP", "PLAY", "ERASE"))

    val reservedTokens: Array<Command<String, Double>> = arrayOf(
        Vars("VARS", memory),
        Reset("RESET", memory),
        recWrapper,
        Stop("STOP",memory, cmdQueue, recWrapper), 
        Erase("ERASE", memory, cmdQueue, 10),  
        Play("PLAY", memory, cmdQueue, recWrapper),
        Exit("EXIT", memory)
    ) 
    val reservedSymbols: Array<Symbol> = arrayOf(Add('+'), Sub('-'), Mul('*'), Div('/'), Pow('^'), Mod('%'), BracketOpen('('), BracketClose(')'),  Attribute('='))
 
    operator fun Array<Command<String, Double>>.contains(value: String) : Boolean {
        return this.any {it.name == value} 
    }

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
                       throw Exception("${err.message}")
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

        var execStack: Stack<Double> = Stack()   

        /* A REMOVER */

        var attrTo: String? = null

        var stmt = expr.getFirst() 

        while(stmt != null){
            var (i, j) = stmt.element.let { 
                arrayOf(
                    reservedSymbols.indexOfFirst { symbl: Symbol ->
                        it[0] == symbl.op
                    },
                    reservedTokens.indexOfLast { tk: Command<String, Double> -> 
                        it.uppercase() == tk.name  
                    }
                )
            }

            if(i != -1 && !recWrapper.recMode){
                if(reservedSymbols[i] is Attribute && attrTo != null){
                    //(reservedSymbols[i] as Attribute).operate(attrTo, execStack.pop(), memory)  
                    //println("\n EXEC ${execStack.top()}")
                     
                    var variable = try{
                        execStack.pop()
                    }
                    catch(err: Throwable){
                        throw Exception("Not enough elements to operate")
                    }
                    memory.append(attrTo, variable)
                    //println(memory)
                    return variable
                } 
                else if(reservedSymbols[i] is Sub && (execStack.size() == 1)){
                    execStack.push((reservedSymbols[i] as Sub).operate(execStack.pop()))
                    stmt = stmt.next
                    continue
                }  

                var (v2, v1) = try{
                    arrayOf(execStack.pop(), execStack.pop()) 
                }
                catch(err: Throwable){
                    //print(attrTo)
                    throw Exception("Not enough elements to operate\ncaused by: ${err.message}")  
                }
                execStack.push(reservedSymbols[i].operate(v1, v2))
                println("\n${execStack.top()}") 
            } 
            else if(j != -1){
                try{
                    if(recWrapper.recMode &&
                        reservedTokens[j] !is Rec<*,*,*> &&
                        reservedTokens[j] !is Play<*,*,*> &&
                        reservedTokens[j] !is Erase<*,*,*>
                        ){

                        this.recWrapper.load(expr)
                        break
                    }

                    if(reservedTokens[j] is Play<*,*,*>){
                        cmdQueue.dequeue()
                        while(!cmdQueue.isEmpty()){
                            println("\n${this.interprete(cmdQueue.dequeue())}")
                        }
                    }

                    reservedTokens[j].call() 
                }
                catch(err: Throwable){
                    println(err.message)
                }

                stmt = stmt.next
                continue 
            }
            else { 
                if(recWrapper.recMode){
                    this.recWrapper.load(expr)
                    break
                }

                var getValue: Double? = stmt.element.toDoubleOrNull()
                if(getValue == null){ 
                    if(expr.getLast()?.element == "="){
                        attrTo = stmt.element.uppercase()  
                    }  
                    getValue = try{
                        memory.getValue(stmt.element.uppercase())
                    }
                    catch(err: Throwable){
                        if(attrTo == null || stmt.next == null){
                            throw Exception("\nErro: variável ${stmt.element} não definida")
                        } 
                        stmt = stmt.next
                        continue 
                    }
                }
                execStack.push(getValue)
            }
            stmt = stmt.next
        }
        try{
            return execStack.top() 
        }
        catch(err: Throwable){
            throw Exception("Nothing to Return; Stack is empty")
        }
    }

}