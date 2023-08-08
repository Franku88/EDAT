package estructuras.grafos;

public class testGrafo {
    public static void main(String[] args) {
        GrafoEtiquetado grafo = new GrafoEtiquetado();

        grafo.insertarVertice("A");
        grafo.insertarVertice("B");
        grafo.insertarVertice("C");
        grafo.insertarVertice("D");
        
        grafo.insertarVertice("F");
        grafo.insertarVertice("G");
        grafo.insertarVertice("E");
        grafo.insertarVertice("H");
        

        grafo.insertarArco("A", "B", 100);
        grafo.insertarArco("C", "D", 100);
        grafo.insertarArco("B", "C", 100);
        grafo.insertarArco("B", "D", 100);
        
        grafo.insertarArco("C", "E", 100);
        grafo.insertarArco("D", "B", 100);
        grafo.insertarArco("G", "D", 100);
        grafo.insertarArco("F", "H", 100);
        grafo.insertarArco("E", "H", 100);
        grafo.insertarArco("F", "G", 100);
        grafo.insertarArco("C", "G", 100);
        grafo.insertarArco("E", "G", 1);


        System.out.println("Grafo: \n "+grafo.toString());
        System.out.println("Lista en profundidad: "+grafo.listarEnProfundidad().toString());
        System.out.println("Lista en anchura: "+grafo.listarEnAnchura().toString());
        System.out.println("Existe camino "+grafo.existeCamino("F", "G"));
        System.out.println("Camino corto: "+grafo.caminoMasCorto("C", "D"));
        System.out.println("Camino largo: "+grafo.caminoMasLargo("C", "D"));
        
    }
}