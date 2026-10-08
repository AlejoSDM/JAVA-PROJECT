package view;

import java.util.Scanner;

public class Validaciones {

    /*validar texto esta esperando un string mensaje osea la preugnta q se mostrara*/
    public String validarTexto(String mensaje) {
        /*se crea una variable llamada validacion que seria boolean*/
        boolean validacion;
        /*texto lo dejamos vaio*/
        String texto = "";
        Scanner x = new Scanner(System.in);
        /*el contador lo inicializams en 0*/
        int contador = 0;
        /*hacemos el do y ponemos validacion en true*/
        do {
            validacion = true;
            /*que imprima la pregunta*/
            System.out.println(mensaje);
            /*texto ahora tendra lo que escriba el usuario como respuesta*/
            texto = x.nextLine();
            /**/
            for (int i = 0; i < texto.length(); i++) {
                contador += texto.charAt(i) == ' ' ? 1 : 0;
                if (!Character.isLetter(texto.charAt(i)) || texto.charAt(i) != ' ') {
                    if (texto.charAt(i) == ' ' && i == 0) {
                        validacion = false;
                        break;
                    }
                }
            }
        } while (validacion == false);
        return texto;
    }

    public double validarDecimal(String mensaje) {
        System.out.println(mensaje);
        Scanner x = new Scanner(System.in);
        while (!x.hasNextDouble()) {
            System.out.println("Error, se espera un valor decimal");
        }
        return x.nextDouble();
    }

    public int validarEntero(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextInt();
    }

    public long validarEnteroGrande(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextLong()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextLong();
    }
}
