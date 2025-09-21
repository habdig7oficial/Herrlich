package lib.DataStructs

class Node <Generic> (
    element: Generic,
    next: Node<Generic>? = null
){
    var element: Generic = element
    var next: Node<Generic>? = next
}


public class LinkedList <Generic> {

    private var root: Node<Generic>? = null
    private var leaf: Node<Generic>? = null
    var length: Int = 0
    
    fun append(element: Generic){
        if(root == null){
            root = Node<Generic>(element)
            leaf = root
            this.length++
            return
        }
        var n = Node<Generic>(element)
        leaf?.next = n
        leaf = n 

        this.length++ 
    }

    fun prepend(element: Generic){  
        root = Node<Generic>(element, root)
        if(this.length == 0)
            this.leaf = root
        this.length++
    }

    fun getLast(): Node<Generic>? {
        return this.leaf
    }

    fun getFirst(): Node<Generic>? {
        return this.root
    }

    fun rmFromStart(index: Int){
        var stmt = root
        var i = 0
        while(stmt != null && i < index){

            i++
            stmt = stmt.next
        }
        stmt?.next = stmt?.next?.next
    }

    override fun toString(): String {
        var str: String = ""
        var e: Node<Generic>? = root
        for(i in 0..< this.length){
            requireNotNull(e)
            str += "\"${e.element}\" "
            e = e.next
        }
        return "[$str]"
    }

}