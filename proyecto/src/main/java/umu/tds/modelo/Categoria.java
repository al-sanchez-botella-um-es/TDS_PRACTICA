package umu.tds.modelo;

/*
- id : Integer
- nombre : String
--
Métodos del controlador referentes a las historias de usuario:
+ addGasto()
+ removeGasto()
+ modifyGasto()
*/

public class Categoria {
	private String nombre;
	
	public Categoria () {}
	
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
    @Override
    public String toString() {
        return this.nombre; // Asegúrate de que tu atributo se llama 'nombre'
    }
}
