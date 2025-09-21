import kotlin.math.pow 
import lib.DataStructs.Hashmap

abstract class Symbol(val op: Char){
    abstract val priority: Int
    abstract fun operate(v1: Double, v2: Double) : Double 

    override fun toString(): String {
        return "($op, $priority)"
    } 
}

class Add(op: Char) : Symbol(op){    
    override val priority: Int = 1
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 + v2 
    }
}

class Sub(op: Char) : Symbol(op){    
    override val priority: Int = 1
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 - v2 
    }
}

class Mul(op: Char) : Symbol(op){     
    override val priority: Int = 2
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 * v2 
    }
}

class Div(op: Char) : Symbol(op){     
    override val priority: Int = 2
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 / v2 
    }
}

class Mod(op: Char) : Symbol(op){     
    override val priority: Int = 2
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 % v2 
    }
}

class Pow(op: Char) : Symbol(op){     
    override val priority: Int = 3
    override fun operate(v1: Double, v2: Double) : Double {
        return v1.pow(v2)  
    }
}

class BracketOpen(op: Char) : Symbol(op){     
    override val priority: Int = 0
    override fun operate(v1: Double, v2: Double) : Double {
        throw Exception("Open Bracket can not operate") 
    }
}

class BracketClose(op: Char) : Symbol(op){     
    override val priority: Int = 4
    override fun operate(v1: Double, v2: Double) : Double {
        throw Exception("Open Bracket can not operate") 
    }
}

class NullSymbl(op: Char) : Symbol(op){     
    override val priority: Int = 0
    override fun operate(v1: Double, v2: Double) : Double {
        throw Exception("Some Error has ocurred")  
    }
}

class Attribute(op: Char) : Symbol(op){    
    override val priority: Int = 1
    override fun operate(v1: Double, v2: Double) : Double {
        return v1 + v2 
    } 
    public fun operate(name: String, value: Double, ptrHash: Hashmap<String, Double>){ 
        ptrHash.append(name, value)
    } 
}


//Symbol('+', 1), Symbol('-', 1), Symbol('*', 2), Symbol('/', 2), Symbol('^', 3), Symbol('%', 2), Symbol('(', 0), Symbol(')', 4), Symbol('=', 1)