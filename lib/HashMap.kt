package lib.DataStructs

class Pair <Generic1, Generic2>(
    val t1 : Generic1,
    val t2 : Generic2
){
    /*
    override fun toString(): String {
        return "($t1, $t2)"
    }  
  */

    override fun toString(): String{
        return "${t2!!::class.simpleName} $t1 = $t2" 
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
    
    private var arr: Array<LinkedList<Pair<Generic1, Generic2>>> = Array(arrLen) { LinkedList() }
    private var length: Int = 0; private set

    fun append(key: Generic1, value: Generic2){
        var targetList = arr[key.hashCode() % arrLen]
        val obj = Pair(key, value)

        if(!targetList.rmFirst(obj)){
            this.length++
        }
        targetList.append(obj) 
    }

    fun getValue(key: Generic1) : Generic2? {
        var searhList : LinkedList<Pair<Generic1, Generic2>> = arr[key.hashCode() % arrLen]

        var elem = searhList.getFirst()

        while(elem != null){
            if(elem.element.t1 == key){
                this.length--
                return elem.element.t2
            }
            elem = elem.next
        }
        throw Exception("Variable Not Declared") 
    
    }

    fun cleanAll(): Unit{
        this.arr = Array(arrLen) { LinkedList() }
        this.length = 0
    }

    override fun toString() : String{
        var str = ""    

        for(i in arr){
            if(i.length > 0)
                str += "\n${i.toCleanString()}"
        }
        return str;

    }

}