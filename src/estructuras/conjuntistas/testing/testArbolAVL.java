package estructuras.conjuntistas.testing;
import estructuras.conjuntistas.dinamicas.*;

public class testArbolAVL {
    public static void main(String[] args) {
        ArbolAVL tree = new ArbolAVL();
        
        System.out.println("<-- Rotacion simple a Derecha -->");
        llenarArbol(tree, new int[] {10, 9, 5, 4});
        System.out.println("Original: \n"+tree.toString());
        tree.insertar(3);
        System.out.println(tree.toString());
        tree.vaciar();

        System.out.println("<-- Rotacion simple a Izquierda -->");
        llenarArbol(tree, new int[] {2,1,3,4});
        System.out.println("Original: \n"+tree.toString());
        tree.insertar(5);
        System.out.println(tree.toString());
        tree.vaciar();

        System.out.println("<-- Rotacion doble Derecha-Izquierda -->");
        llenarArbol(tree, new int[] {10, 5, 15, 12, 17});
        System.out.println("Original: \n"+tree.toString());
        tree.insertar(13);
        System.out.println(tree.toString());
        tree.vaciar();
        
        System.out.println("<-- Rotacion doble Izquierda-Derecha -->");
        llenarArbol(tree, new int[] {12, 5, 23, 3, 8});
        System.out.println("Original: \n"+tree.toString());
        tree.insertar(10);
        System.out.println(tree.toString());
        tree.vaciar();

        System.out.println("<-- Eliminar hoja -->");
        llenarArbol(tree, new int[] {15, 9, 20, 6, 14, 17, 35, 3});
        System.out.println("Original: \n"+tree.toString());
        tree.eliminar(14);
        System.out.println(tree.toString());
        tree.vaciar();

        System.out.println("<-- Eliminar nodo con 1 hijo -->");
        llenarArbol(tree, new int[] {15, 9, 20, 6, 14, 17, 35, 3});
        System.out.println("Original: \n"+tree.toString());
        tree.eliminar(6);
        System.out.println(tree.toString());
        tree.vaciar();

        System.out.println("<-- Eliminar nodo con 2 hijos -->");
        llenarArbol(tree, new int[] {15, 9, 20, 6, 14, 17, 35, 3});
        System.out.println("Original: \n"+tree.toString());
        tree.eliminar(15);
        System.out.println(tree.toString());
    }

    public static void llenarArbol(ArbolAVL tree, int[] arr){
        //Inserta todos los elementos del array en orden a un ArbolAVL
        int j, limite = arr.length;
        for (j = 0; j < limite; j++){
            tree.insertar(arr[j]);
        }
    }
}
