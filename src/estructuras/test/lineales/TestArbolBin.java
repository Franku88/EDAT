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
    }
    
    public static void cargarArbolBin(ArbolBin arbol) {
        arbol.insertar(1, null, false);

        arbol.insertar(2, 1, false);
        arbol.insertar(3, 1, true);

        arbol.insertar(4, 2, false);
        arbol.insertar(5, 2, true);

        arbol.insertar(6, 3, false);
        arbol.insertar(7, 3, true);

        arbol.insertar(8, 4, false);
        arbol.insertar(9, 4, true);

        arbol.insertar(10, 5, false);
        arbol.insertar(11, 5, true);
    }


}
