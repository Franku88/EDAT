package estructuras.test.lineales;

import estructuras.jerarquicas.dinamicas.ArbolGen;

public class PruebaArbolGen {
    
    public static void main(String[] args) {
        ArbolGen a = new ArbolGen();
        cargarArbol(a);
        System.out.println(a.toString());
        System.out.println(a.listarPorNiveles().toString());    
    }

    public static void cargarArbol(ArbolGen a) {
        a.insertar(1,null);

        a.insertar(4,1);
        a.insertar(3,1);
        a.insertar(2,1);

        a.insertar(5,2);

        a.insertar(7,4);
        a.insertar(6,4);
    }
}