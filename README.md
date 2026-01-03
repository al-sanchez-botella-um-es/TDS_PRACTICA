# "Gestión de Gastos"
## Integrantes del grupo

María Isabel Pérez Lisón - mi.perezlison@um.es - 3.2
Ana Luo Sánchez Botella - al.sanchezbotella@um.es - 3.2
Leonie Swart

## Descripción del proyecto
Este proyecto implementa una aplicación de escritorio en JavaFX para la gestión y control de gastos personales, siguiendo una estructura propuesta en la asignatura Tecnologías de Desarrollo de Software.
La aplicación permite:
- Registrar, editar y eliminar gastos.
- Organizar gastos por categoriías (predefinidas o creadas por el usuario).
- Visualizar la información en tablas, gráficos de barras/circulares y vista de calendario (mediante CalendarFX).
- Aplicar filtros por meses, intervalos de fechas y categorías.
- Configurar alertas de gasto (semanales, mensuales, anuales o por categoría), con historial de notificaciones.
- Gestionar cuentas de gasto compartidas, con reparto equitativo o porcentual.
- Importar gastos desde ficheros externos mediante un sistema extensible basado en patrones Adaptador y Factoría.
- Utilizar tanto interfaz gráfica como línea de comandos para la gestión de gastos.
El almacenamiento se realiza en _JSON_ mediante la librería Jackson, siguiendo el patrón Repositorio.
Se emplean Maven para la gestión de dependencias y Git para el control de versiones.

## Cómo ejecutar el proyecto
### 1. Requisitos previos
...

## Documentación del proyecto
Toda la documentación se encuentra en la carpeta **/docs** del repositorio.
Enlaces directos:
1. Diagrama de clases del dominio del proyecto > docs/diagrama-clases.md
2. Especificación de las historias de usuario del proyecto > docs/historias-usuario.md
3. Un diagrama de interacción para una de las historias de usuario > docs/diagrama-interaccion.md
4. Breve explicación de la arquitectura de la aplicación y decisiones de diseño que se consideren de interés para la comprensión del trabajo > docs/arquitectura.md
5. Explicación de los patrones de diseño usados > docs/patrones-diseño.md
6. Breve manual de usuario > docs/manual-usuario.md
