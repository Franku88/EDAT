package estructuras.especifico.testing;
import estructuras.especifico.diccionario.*;

public class testDiccionario {
    public static void main(String[] args) {
        Diccionario dicc = new Diccionario();
        
        System.out.println("<-- Rotacion simple a Derecha -->");
        llenarDiccionario(dicc, new Object[][] {{10,"Diez"}, {9,"Nueve"}, {5,"Cinco"}, {4,"Cuatro"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.insertar(3, "Tres");
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Rotacion simple a Izquierda -->");
        llenarDiccionario(dicc, new Object[][] {{2, "Dos"}, {1, "Uno"}, {3, "Tres"}, {4, "Cuatro"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.insertar(5, "Cinco");
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Rotacion doble Derecha-Izquierda -->");
        llenarDiccionario(dicc, new Object[][] {{10, "Diez"}, {5, "Cinco"}, {15, "Quince"}, {12, "Doce"}, {17, "Diecisiete"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.insertar(13, "Trece");
        System.out.println(dicc.toString());
        dicc.vaciar();
        
        System.out.println("<-- Rotacion doble Izquierda-Derecha -->");
        llenarDiccionario(dicc, new Object[][] {{12, "Doce"}, {5, "Cinco"}, {23, "Veintitres"}, {3, "Tres"}, {8, "Ocho"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.insertar(10, "Diez");
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Eliminar hoja -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}, {3, "Tres"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.eliminar(14);
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Eliminar nodo con 1 hijo -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}, {3, "Tres"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.eliminar(6);
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Eliminar nodo con 2 hijos -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}, {3, "Tres"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.eliminar(14);
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Otro eliminar nodo con 2 hijos -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}});
        System.out.println("Original: \n"+dicc.toString());
        dicc.eliminar(20);
        System.out.println(dicc.toString());
        dicc.vaciar();

        System.out.println("<-- Listar Claves -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}});
        System.out.println((dicc.listarClaves()).toString());
        dicc.vaciar();

        System.out.println("<-- Listar Datos -->");
        llenarDiccionario(dicc, new Object[][] {{15, "Quince"}, {9, "Nueve"}, {20, "Veinte"}, {6, "Seis"}, {14, "Catorce"}, {17, "Diecisiete"}, {35, "Treinta y cinco"}});
        System.out.println((dicc.listarDatos()).toString());
        dicc.vaciar();
    }

    public static void llenarDiccionario(Diccionario dicc, Object[][] mat){
        //Inserta todos los pares [clave][dato] de la matriz en orden a un Diccionario
        int j, limite = mat.length;
        for (j = 0; j < limite; j++){
            dicc.insertar((Comparable) mat[j][0], mat[j][1]);
        }
    }

}
