package lib.DataStructs;
import java.util.Iterator;

public class Stack <T> {
    // atributos
    private final static int TAM_DEFAULT = 100;
    private int topoStack;
    private T elements[];

    // construtores
    public Stack(int tamanho) {
        elements = (T[]) new Object[tamanho];
        topoStack = -1;
    }

    public Stack() {
        this(TAM_DEFAULT);
    }

    // métodos
    public void push(T e) throws Exception {
        if (!isFull()) {
            elements[++topoStack] = e;
        }
        else {
            throw new Exception("Overflow - Estouro de Stack");
        }
    }

    public T pop() throws Exception {
        if (!isEmpty()) {
            return elements[topoStack--];
        }
        else {
            throw new Exception("Underflow - Esvaziamento de Stack");
        }
    }

    public T top() throws Exception {
        if (!isEmpty()) {
            return elements[topoStack];
        }
        else {
            throw new Exception("Underflow - Esvaziamento de Stack");
        }
    }

    public int size() {
        return topoStack + 1;
    }

    public boolean isEmpty() {
        return topoStack == -1;
    }

    public boolean isFull() {
        return topoStack == elements.length - 1;
    }

    @Override
    public String toString(){
        String builder = "";
        for(int i = 0; i < topoStack; i++){
            builder += elements[i];
        }
        return builder;
    }

}
