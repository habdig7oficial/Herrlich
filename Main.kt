class Node <Generic> (
    element: Generic,
    next: Node<Generic>?
){
    var element: Generic = element
    var next: Node<Generic>? = next
}


class LinkedList <Generic> {

    var root: Node<Generic>? = null
    var length: Int = 0
    
    fun append(element: Generic){
        root = Node<Generic>(element, root)
        this.length++
    }
    
    fun print(){
        var e: Node<Generic>? = root
        for(i in 0..< this.length){
            requireNotNull(e)
            println(e.element)
            e = e.next
        }
    }
}


open class Interpreter{
    val reserved: Array<String> = arrayOf("VARS", "RESET", "REC", "STOP", "PLAY", "ERASE","EXIT")
    val reservedSymbols: Array<Char> = arrayOf('+', '-', '*', '/', 'ˆ', '%', '(', ')', '=')

    fun tokenize(textStream : String) : LinkedList<String> {

        val tokens: LinkedList<String> = LinkedList()


        var i : Int = 0
        var ptr : Int = 0

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
            str.print()
        }
    }
}

fun main(){
    val instance : Repl = Repl()
    instance.run()
}