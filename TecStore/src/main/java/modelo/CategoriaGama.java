package modelo;

import view.Validaciones;

public enum CategoriaGama {

    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private final String descripcion;
    CategoriaGama(String descripcion) {
      this.descripcion = descripcion;
    }

    public String getDescripcion() {
      return descripcion;
    }

    public static CategoriaGama seleccionarGama() {
     Validaciones v = new Validaciones();
     System.out.println(" Seleccione la Gama");
     CategoriaGama[] opciones = CategoriaGama.values();
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