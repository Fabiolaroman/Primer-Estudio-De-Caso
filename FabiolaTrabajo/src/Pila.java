public class Pila {
    private Nodo cima; // referencia al último elemento ingresado

    public Pila() {
        this.cima = null;
    }

    // Inserta un nuevo elemento (token) en la pila
    public void push(String dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
    }

    // Elimina y devuelve el elemento superior de la pila
    public String pop() {
        if (estaVacia()) {
            throw new RuntimeException("Error: la pila está vacía.");
        }
        String valor = cima.dato;
        cima = cima.siguiente;
        return valor;
    }

    // Devuelve el elemento superior sin eliminarlo
    public String peek() {
        if (estaVacia()) {
            throw new RuntimeException("Error: la pila está vacía.");
        }
        return cima.dato;
    }

    // Verifica si la pila está vacía
    public boolean estaVacia() {
        return cima == null;
    }
}