package estructuras.conjuntistas.testing;
import estructuras.conjuntistas.dinamicas.ArbolBB;

public class testArbolBB {
    public static void main(String[] args) {
        ArbolBB a = new ArbolBB();
        int[] arr = {10, 8, 12, 33, 2, 4, 7, 11, 1};
        llenarArbol2(a, arr);

        System.out.println("Arbol: ");
        System.out.println(a.toString());
        System.out.println("Pertenece 10? "+a.pertenece(10));
        System.out.println("Elem min "+a.minimoElem());
        System.out.println("Elem max "+a.maximoElem());
        System.out.println("Lista menor a mayor "+a.listar().toString());
        System.out.println("Lista rango [2, 8]: "+a.listarRango(2, 8).toString());
        a.eliminar(10);
        System.out.println(a.toString());

    }

    public static void llenarArbol(ArbolBB a){
        for (int i = 9; i >= 0; i--){
            a.insertar(i);
        }
    }

    public static void llenarArbol2(ArbolBB a, int[] arr){
        int i, limite = arr.length;
        for (i = 0; i < limite; i++){
            a.insertar(arr[i]);
        }
    }
}