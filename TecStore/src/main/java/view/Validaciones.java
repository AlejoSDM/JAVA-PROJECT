package view;

import java.util.Scanner;

public class Validaciones {

    private static final Scanner SCANNER = new Scanner(System.in);

    public String validarTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("El texto no puede estar vacío.");
        }
    }

    public double validarDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingresa un número decimal válido.");
            }
        }
    }

    public int validarEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingresa un número entero válido.");
            }
        }
    }

    public long validarEnteroGrande(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();

            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: ingresa un número entero válido.");
            }
        }
    }
}