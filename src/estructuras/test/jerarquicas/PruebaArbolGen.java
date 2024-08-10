package estructuras.test.jerarquicas;

import estructuras.jerarquicas.dinamicas.ArbolGen;

public class PruebaArbolGen {
    
    public static void main(String[] args) {
        ArbolGen a = new ArbolGen();
        ArbolGen clon = new ArbolGen();
        //cargarArbol(a);
    
        System.out.println("--- Arbol ---");
        System.out.println(a.toString());
        
        clon = a.clone();
        System.out.println("--- Clon ---");
        System.out.println(clon.toString());

        System.out.println("--- Arbol y Clon"+((a.equals(clon))?" son iguales ---":" no son iguales ---"));
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