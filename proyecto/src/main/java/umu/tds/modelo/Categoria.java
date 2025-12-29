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
        return this.nombre;
    }
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Categoria)) return false;
	    Categoria c = (Categoria) o;
	    return nombre != null && nombre.equalsIgnoreCase(c.nombre);
	}

	@Override
	public int hashCode() {
	    return nombre == null ? 0 : nombre.toLowerCase().hashCode();
	}
}