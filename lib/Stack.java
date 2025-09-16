package lib.DataStructs;

public class Stack <T> {
    // atributos
    private final static int TAM_DEFAULT = 100;
    private int topoStack;
    private T elementos[];

    // construtores
    public Stack(int tamanho) {
        elementos = (T[]) new Object[tamanho];
        topoStack = -1;
    }

    public Stack() {
        this(TAM_DEFAULT);
    }

    // métodos
    public void push(T e) throws Exception {
        if (!isFull()) {
            elementos[++topoStack] = e;
        }
        else {
            throw new Exception("Overflow - Estouro de Stack");
        }
    }

    public T pop() throws Exception {
        if (!isEmpty()) {
            return elementos[topoStack--];
        }
        else {
            throw new Exception("Underflow - Esvaziamento de Stack");
        }
    }

    public T topo() throws Exception {
        if (!isEmpty()) {
            return elementos[topoStack];
        }
        else {
            throw new Exception("Underflow - Esvaziamento de Stack");
        }
    }

    public int sizeElements() {
        return topoStack + 1;
    }

    public boolean isEmpty() {
        return topoStack == -1;
    }

    public boolean isFull() {
        return topoStack == elementos.length - 1;
    }

    @Override
    public String toString(){
        String builder = "";
        for(int i = 0; i < topoStack; i++){
            builder += elementos[i];
        }
        return builder;
    }

}

/*
class Stack <Generic>(size: Int){
    push
} */