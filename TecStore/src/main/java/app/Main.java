package app;


import Dao.CelularCRUD;
import Dao.ClienteCRUD;
import Dao.EmpleadoCRUD;
import Dao.ReporteCRUD;
import controller.CelularController;
import controller.ClienteController;
import controller.CompraController;
import controller.EmpleadoController;
import controller.GestionGeneral;
import controller.MarcaController;
import controller.ReporteController;
import view.Menu;

public class Main {
	public static void main(String[] args) {
		Menu menu = new Menu();
		CelularController celularController = new CelularController(
				new CelularCRUD(),
				menu
		);

		GestionGeneral sistema = new GestionGeneral(
				menu,
				celularController,
				new MarcaController(),
				new EmpleadoController(new EmpleadoCRUD(), menu),
				new ClienteController(new ClienteCRUD(), menu),
				new CompraController(menu, celularController),
				new ReporteController(new ReporteCRUD(), menu)
		);

		sistema.iniciar();
	}
    
}