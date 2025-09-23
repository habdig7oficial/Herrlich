package lib.DataStructs

class Pair <Generic1, Generic2>(
    val t1 : Generic1,
    val t2 : Generic2
){
    override fun toString(): String {
        return "($t1, $t2)"
    }  
  
    override fun equals(other: Any?) : Boolean {
        if(other !is Pair<*, *>) 
            return false;
        else if(other === this)
            return true
        else if(this.t1 == other.t1)
            return true; 
        else 
            return false
    } 
}

class Hashmap <Generic1, Generic2> {
    final val arrLen = 26
    
    private val arr: Array<LinkedList<Pair<Generic1, Generic2>>> = Array(arrLen) { LinkedList() }

    fun append(key: Generic1, value: Generic2){
        var targetList = arr[key.hashCode() % arrLen]
        val obj = Pair(key, value)
        
        targetList.rmFirst(obj)
        targetList.append(obj) 
    }

    fun getValue(key: Generic1) : Generic2? {
        var searhList : LinkedList<Pair<Generic1, Generic2>> = arr[key.hashCode() % arrLen]

        var elem = searhList.getFirst()

        while(elem != null){
            if(elem.element.t1 == key){
                return elem.element.t2
            }
            elem = elem.next
        }
        throw Exception("Variable Not Declared") 
    
    }

    override fun toString() : String{
        var str = ""    

        for(i in arr){
            str += "\n${i.toString()}"
        }
 
        return str;

    }

}