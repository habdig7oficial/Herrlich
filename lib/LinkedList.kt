package lib.LinkedList

class Node <Generic> (
    element: Generic,
    next: Node<Generic>? = null
){
    var element: Generic = element
    var next: Node<Generic>? = next
}


public class LinkedList <Generic> {

    private var root: Node<Generic>? = null
    var length: Int = 0
    
    fun append(element: Generic){

        if(root == null){
            root = Node<Generic>(element)
        }

        var e: Node<Generic>? = root
        while(e?.next != null){
            //print(e.element)
            e = e.next
        }
       // print("\n")
        e?.next = Node<Generic>(element)

    }

    fun prepend(element: Generic){ 
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