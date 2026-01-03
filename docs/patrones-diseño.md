# "Patrones de Diseño"
## Patrón Singleton
### Motivación
Algunas clases del sistema deben tener una única instancia global, accesible desde cualquier parte de la aplicación.
Esto evita inconsistencias, duplicación de datos y problemas de sincronización.

### Aplicación en el proyecto
Se utiliza Singleton en:
- Gestor de Persistencia:
  Cada repositorio se implementa como Singleton para asegurar que toda la aplicación trabaja sobre la misma colección de datos cargada desde JSON.
- Controlador principal:
  Gestiona la lógica central de la aplicación y coordina la interacción entre la interfaz y el modelo.
  Su instancia única evita inconsistencias y facilita el acceso global desde las distintas ventanas JavaFX.

--
## Patrón Repositorio
### Motivación
El patrón Repositorio desacopla la lógica de negocio de los detalles de persistencia.
Permite tratar los datos como si fueran colecciones en memoria, aunque realmente estén almacenados en JSON mediante Jackson.

### Aplicación en el proyecto
Se han definido repositorios como: _RepositorioGasto_, _RepositorioCategoria_, _RepositorioAlertas_...
Y cada uno ofrece operaciones como:
- findAll()
- save()
- delete()
- modify()

La implementación concreta usa Jackson para leer/escribir en ficheros JSON, pero la capa de dominio no depende de ello.

--
## Patrón Estrategia
### Motivación
El sistema de alertas debe permitir diferentes criterios de evaluación: alertas semanales, mensuales, por categoría, etc.
Cada tipo de alerta tiene una lógica distinta, pero todas deben poder evaluarse de forma uniforme.

### Aplicación en el proyecto
Se define como una interfaz:
```
public interface EstrategiaAlerta {
	boolean isAlertaActivada(List<Gasto> gastos, Alerta alerta);
}
```
Y varias implementaciones como: _AlertaSemanal_, _AlertaMensual_...
Cada una definirá el método heredado como considere oportuno para su funcionalidad concreta.

--
## Patrón Adaptador
### Motivación
La aplicación debe importar gastos desde diferentes formatos externos (por ejemplo, CSV bancario, TXT, JSON externo…).
Cada fuente puede tener un formato distinto, pero la aplicación necesita convertirlos a objetos 'Gasto' de forma uniforme.

### Aplicación en el proyecto
Se define una interfaz común:
```
public interface AdaptadorImportadorGastos {
	List<Gasto> importar(File archivo) throws IOException;
}
```
Y adaptadores concretos como: _ImportadorCSV_, _ImportadorTXT_, _ImportadorJSON_...
Cada adaptador transforma el formato externo al modelo interno.

--
## Patrón Método Factoría
### Motivación
El sistema debe seleccionar automáticamente el importador adecuado según el tipo de fichero proporcionado por el usuario.

### Aplicación en el proyecto
Se implementa una factoría:
```
public class FactoriaImportador {
    public static ImportadorGastos crearImportador(File fichero) {
        if (fichero.getName().endsWith(".csv")) return new ImportadorCSV();
        if (fichero.getName().endsWith(".txt")) return new ImportadorTXT();
        if (fichero.getName().endsWith(".json")) return new ImportadorJSON();
        throw new IllegalArgumentException("Formato no soportado");
    }
}
```

--
## Conclusión
El uso de estos patrones permite que la aplicación sea:
- Extensible (nuevos tipos de alertas, nuevos importadores).
- Mantenible (código desacoplado y modular).
- Robusta (una única fuente de verdad para datos y notificaciones).
- Clara en su arquitectura (repositorios, factorías, estrategias bien delimitadas).
