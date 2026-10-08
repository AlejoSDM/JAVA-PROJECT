package modelo;

import view.Validaciones;

public enum SistemaOperativo {
    IOS("iOS"),
    ANDROID("Android"),
    KAIOS("KaiOS");

    private final String nombre;
     SistemaOperativo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static SistemaOperativo SeleccionarSistemaOperativo() {
    Validaciones v = new Validaciones();
    System.out.println("Seleccione el Sistema Operativo");
    SistemaOperativo[] opciones = SistemaOperativo.values();
    for (int i = 0; i < opciones.length; i++) {
      System.out.println((i + 1) + ". " + opciones[i].name());
    }
    int opcion;
    do {
      opcion = v.validarEntero("Seleccione una opción (1-" + opciones.length + "):");
    } while (opcion < 1 || opcion > opciones.length);
    return opciones[opcion - 1];
   }
}