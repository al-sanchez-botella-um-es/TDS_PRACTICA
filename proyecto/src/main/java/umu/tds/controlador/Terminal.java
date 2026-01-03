package umu.tds.controlador;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import umu.tds.modelo.Gasto;
import umu.tds.modelo.Categoria; // Asegúrate de importar esto

public class Terminal {

    private static Controlador controlador = new Controlador(); 
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("################################################");
        System.out.println("#      APLICACIÓN DE GASTOS - MODO TERMINAL    #");
        System.out.println("################################################");

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String entrada = scanner.nextLine();

            switch (entrada) {
                case "1":
                    listarGastosTerminal();
                    break;
                case "2":
                    addGastoTerminal();
                    break;
                case "3":
                    removeGastoTerminal();
                    break;
                case "4":
                    modificarGastoTerminal();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no reconocida.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Listar todos los gastos");
        System.out.println("2. Añadir un gasto");
        System.out.println("3. Borrar un gasto");
        System.out.println("4. Modificar un gasto");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void listarGastosTerminal() {
        System.out.println("\n--- LISTADO DE GASTOS ---");
        List<Gasto> gastos = controlador.getGastos();
        
        if (gastos.isEmpty()) {
            System.out.println("No hay gastos registrados.");
        } else {
            for (int i = 0; i < gastos.size(); i++) {
                Gasto g = gastos.get(i);
                // con 'i' para facilitar
                System.out.println("[" + i + "] " + g.getFecha() + " | " + g.getNombre() + 
                                   " | " + g.getCantidad() + "€ | " + g.getCategoria().getNombre());
            }
        }
    }

    private static Categoria seleccionarCategoria() {
        Categoria categoriaSeleccionada = null;
        
        while (categoriaSeleccionada == null) {
            System.out.println("\n--- SELECCIONAR CATEGORIA ---");
            List<Categoria> categorias = controlador.getCategorias();

            System.out.println("0. "+ "Crear Categoria");
            
            // Mostramos las categorias numeradas empezando por 1
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + ". " + categorias.get(i).getNombre());
            }

            System.out.print("Elige el número de la categoria: ");
            String entrada = scanner.nextLine(); 

            try {
                int opcion = Integer.parseInt(entrada);

                if (opcion == 0) {
                    // CREAR CATEGORIA
                    System.out.print("Introduce el nombre de la nueva categoria: ");
                    String nombreNuevaCat = scanner.nextLine();
                    
                    controlador.addCategoria(nombreNuevaCat); 
                    System.out.println("Categoria creada.");
                    
                } else if (opcion > 0 && opcion <= categorias.size()) {
                    // SELECCIONAR CATEGORIA
                    // Restamos 1 para obtener el indice real de la lista (0...N)
                    categoriaSeleccionada = categorias.get(opcion - 1);
                    
                } else {
                    System.out.println("Indice incorrecto.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
        
        return categoriaSeleccionada;
    }
    
    private static boolean comprobarFecha(LocalDate fecha){
    	return fecha.isBefore(LocalDate.now());
    }
    private static void addGastoTerminal() {
        try {
            System.out.println("\n--- AÑADIR GASTO ---");
            
            System.out.print("Concepto/Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Cantidad: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            System.out.print("Fecha [Intro para hoy]: ");
            String fechaStr = scanner.nextLine();
            LocalDate fecha = null;
            if(fechaStr.isEmpty()) {
            	fecha = LocalDate.now();
            }
            else{
            	fecha = LocalDate.parse(fechaStr);
            }
            if(!comprobarFecha(fecha)) {
            	System.out.println("La fecha no puede ser posterior a la de hoy");
            	return;
            }

            Categoria categoria = seleccionarCategoria();
            controlador.addGasto(nombre, categoria.getNombre(), cantidad, fecha);
            System.out.println("Gasto registrado");

        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Error en el formato de los datos: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Categoria no válida.");
        }
    }

    private static void removeGastoTerminal() {
        listarGastosTerminal();
        System.out.print("\nIntroduce el indice del gasto a borrar: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine());
            List<Gasto> gastos = controlador.getGastos();
            
            if (indice >= 0 && indice < gastos.size()) {
                Gasto gastoABorrar = gastos.get(indice);
                controlador.removeGasto(gastoABorrar); 
                System.out.println("Gasto borrado.");
            } else {
                System.out.println("Indice incorrecto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número.");
        }
    }
    
    private static void modificarGastoTerminal() {
    	listarGastosTerminal();
    	System.out.print("\nIntroduce el indice del gasto a modificar: ");
    	try {
            int indice = Integer.parseInt(scanner.nextLine());
            List<Gasto> gastos = controlador.getGastos();

            if (indice >= 0 && indice < gastos.size()) {
                Gasto gastoOriginal = gastos.get(indice);
                
                //NOMBRE
                System.out.print("Nombre [" + gastoOriginal.getNombre() + "]: ");
                String nuevoNombre = scanner.nextLine();
                if (nuevoNombre.isEmpty()) {
                    nuevoNombre = gastoOriginal.getNombre(); // Si está vacio, mantenemos el antiguo
                }
                gastoOriginal.setNombre(nuevoNombre);

                // CANTIDAD 
                System.out.print("Cantidad [" + gastoOriginal.getCantidad() + "]: ");
                String cantStr = scanner.nextLine();
                double nuevaCantidad;
                if (cantStr.isEmpty()) {
                    nuevaCantidad = gastoOriginal.getCantidad();
                } else {
                    nuevaCantidad = Double.parseDouble(cantStr);
                }
                gastoOriginal.setCantidad(nuevaCantidad);

                // FECHA 
                System.out.print("Fecha [" + gastoOriginal.getFecha() + "]: ");
                String fechaStr = scanner.nextLine();
                LocalDate nuevaFecha;
                if (fechaStr.isEmpty()) {
                    nuevaFecha = gastoOriginal.getFecha();
                } else {
                    nuevaFecha = LocalDate.parse(fechaStr);
                }
                if(!comprobarFecha(nuevaFecha)) {
                	System.out.println("La fecha no puede ser posterior a la de hoy");
                	return;
                }
                gastoOriginal.setFecha(nuevaFecha);
                // CATEGORIA
                // preguntamos si quiere cambiarla, para no buscar en la lista si no quiere
                System.out.println("Categoria actual: [" + gastoOriginal.getCategoria().getNombre() + "]");
                System.out.print("¿Deseas cambiar la categoria? (s/n) [n]: ");
                String respuestaCat = scanner.nextLine();
                
                Categoria nuevaCategoria;
                if (respuestaCat.equalsIgnoreCase("s")) {
                    nuevaCategoria = seleccionarCategoria();
                } else {
                    nuevaCategoria = gastoOriginal.getCategoria();
                }
                gastoOriginal.setCategoria(nuevaCategoria);
                
                controlador.modifyGasto(gastoOriginal);
                
                System.out.println("Gasto modificado.");

            } else {
                System.out.println("ndice incorrecto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
        } catch (Exception e) {
            System.out.println("Error al modificar: " + e.getMessage());
        }
    }
}