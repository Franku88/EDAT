package estructuras.test.lineales;

import estructuras.jerarquicas.dinamicas.ArbolBin;

public class TestArbolBin {
    public static void main(String[] args){

        ArbolBin arbol = new ArbolBin();
        

        cargarArbolBin(arbol);
        System.out.println("Arbol vacio: "+ arbol.esVacio());
        System.out.println("Altura del arbol. Espera 3: "+arbol.altura());
        System.out.println("Altura del nodo 3, espera 1: "+ arbol.nivel(3));
        System.out.println("Altura del nodo 4, espera 2: "+ arbol.nivel(4));
        System.out.println("Altura del nodo 6: espera 3: "+ arbol.nivel(8));
        System.out.println(arbol.toString());
        System.out.println("Listado (Por niveles): "+arbol.listarPorNiveles().toString());
        System.out.println("Frontera, espera |8,9,10,11,6,7|: " + arbol.frontera().toString());
        System.out.println("Descendientes de 2: "+arbol.listarDescendientes(2).toString());

        /*
        ArbolBin arbolClon = arbol.clone();
        System.out.println("Clon vacio: "+ arbolClon.esVacio());
        System.out.println("Altura del clon. Espera 3: "+ arbolClon.altura());
        System.out.println("Altura del nodo 3, espera 1: "+ arbolClon.nivel(3));
        System.out.println("Altura del nodo 4, espera 2: "+ arbolClon.nivel(4));
        System.out.println("Altura del nodo 6: espera 3: "+ arbolClon.nivel(8));
         */
    }
    
    public static void cargarArbolBin(ArbolBin arbol) {
        arbol.insertar(1, null, 'I');

        arbol.insertar(2, 1, 'I');
        arbol.insertar(3, 1, 'D');

        arbol.insertar(4, 2, 'I');
        arbol.insertar(5, 2, 'D');

        arbol.insertar(6, 3, 'I');
        arbol.insertar(7, 3, 'D');

        arbol.insertar(8, 4, 'I');
        arbol.insertar(9, 4, 'D');

        arbol.insertar(10, 5, 'I');
        arbol.insertar(11, 5, 'D');
    }


}
