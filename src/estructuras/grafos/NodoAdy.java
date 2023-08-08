package estructuras.grafos;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private double etiqueta;

    public NodoAdy(NodoVert nodo, NodoAdy ady, double etiq){
        this.vertice = nodo;
        this.sigAdyacente = ady;
        this.etiqueta = etiq;
    }

    public NodoVert getVertice(){
        return vertice;
    }

    public NodoAdy getSigAdyacente(){
        return sigAdyacente;
    }

    public double getEtiqueta(){
        return etiqueta;
    }

    public void setVertice(NodoVert vertice){
        this.vertice = vertice;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente){
        this.sigAdyacente = sigAdyacente;
    }

    public void setEtiqueta(double etiq){
        this.etiqueta = etiq;
    }
}