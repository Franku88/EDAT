package estructuras.lineales.dinamicas;

public class Nodo {
    
    private Object elemento;
    private Object enlace;

    public Nodo(Object element, Object link) {
        this.elemento = element;
        this.enlace = link;
    }

    public void setElemento(Object element) {
        this.elemento = element;
    }

    public void setEnlace(Object link) {
        this.enlace = link;
    }
    
    public Object getElemento() {
        return this.elemento;
    }

    public Object getEnlace() {
        return this.enlace;
    }

}
