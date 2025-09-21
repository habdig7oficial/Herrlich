package lib.DataStructs

class Pair <Generic1, Generic2>(
    val t1 : Generic1,
    val t2 : Generic2
){
    override fun toString(): String {
        return "($t1, $t2)"
    } 
}

class Hashmap <Generic1, Generic2> {
    private val arr: Array<LinkedList<Pair<Generic1, Generic2>>> = Array(26) { LinkedList() }

    

}