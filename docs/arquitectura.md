# Arquitectura de Gestión de Gastos
La aplicación implementa un sistema de escritorio para el control y seguimiento de las finanzas personales, manteniendo un registro persistente de todos los movimientos monetarios. Permite al usuario interactuar con la información tanto mediante una Interfaz Gráfica de Usuario como a través de una línea de comandos para operaciones básicas.

Además incorpora funcionalidades para el trabajo cooperativo mediante cuentas de gastos compartidos, calculando automáticamente los saldos entre los participantes según los porcentajes asignados al momento de su creación. Asimismo, integra un sistema de alertas y notificaciones para el control del presupuesto y ofrece la posibilidad de importar datos desde fuentes externas.

![Ventana Principal](<../docs/imagenes/Ventana Principal App.png>)

---

# Conceptos básicos
La arquitectura de la aplicación se basa en el patrón Modelo-Vista-Controlador (MVC), el cual desacopla la lógica de negocio de la interfaz de usuario. A continuación se detallan sus tres componentes en el contexto de este proyecto:

El *** Modelo *** representa los datos, la lógica de negocio y las reglas de la aplicación. Este componente es independiente de la interfaz gráfica y se estructura en dos bloques principales:
 - *Entidades*: Clases como Gasto, Participante, Categoria, CuentaCompartida, Notificacion y Alerta. En estas clases se encuentran los datos necesarios de la aplicación y la lógica de negocio intrínseca (cómo calcular el saldo pendiente de una persona en una cuenta compartida o verificar si un gasto supera el límite de una alerta).
 - *Persistencia*: El modelo integra la capa de acceso a datos ubicada en el paquete `umu.tds.repository`. Siguiendo el Patrón Repositorio, se definen interfaces que desacoplan el dominio de la tecnología de almacenamiento. Las implementaciones concretas `umu.tds.repository.impl` son las encargadas de materializar la persistencia en ficheros JSON utilizando la librería Jackson.


La *** Vista *** corresponde a la interfaz gráfica desarrollada en JavaFX, cuya responsabilidad principal es presentar la información del modelo y capturar las acciones del usuario. Esta capa no contiene lógica de negocio, limitándose a la visualización de datos y a la delegación de eventos hacia el controlador. La vista está compuesta por los siguientes elementos:
 - *Ficheros FXML*: definen la estructura visual de las ventanas y componentes de la aplicación.    
 - *Controladores de Vista*: clases Java asociadas a cada FXML que gestionan los eventos de la interfaz. Estos controladores recogen los datos introducidos por el usuario y delegan su procesamiento en el controlador principal de la aplicación.

Para poder realizar operaciones de negocio, la vista mantiene una referencia a la instancia única del Controlador. A diferencia de un esquema MVC básico, esta arquitectura implementa una comunicación bidireccional:
 - *Vista -> Controlador*: cuando el usuario realiza una acción, cuando el controlador de la vista correspondiente recoge los datos introducidos por el usuario y los envía al controlador.
 - *Controlador -> Vista*: el controlador de negocio mantiene una referencia a ControladorVentanaPrincipal, lo que le permite enviar notificaciones que se muestran inmediatamente al usuario mediante ventanas emergentes o actualizaciones en el panel de notificaciones.

La navegación entre las distintas pantallas de la aplicación no se realiza mediante la apertura de nuevas ventanas del sistema operativo, sino a través de un sistema de pestañas dinámicas gestionado centralizadamente por la clase ControladorVentanaPrincipal.


El *** Controlador *** actúa como intermediario entre el modelo y la vista, exponiendo las operaciones de negocio que el usuario puede ejecutar. La interfaz de usuario invoca estas operaciones y presenta al usuario las respuestas obtenidas. A su vez, el controlador se encarga de recuperar y almacenar datos en el repositorio de datos, así como de comprobar las precondiciones necesarias para cada operación.
Este componente está implementado en la clase Controlador, dentro del paquete `umu.tds.controlador`.

Entre sus capacidades más destacadas se encuentra el procesamiento de datos en memoria mediante Java Streams, lo cual se refleja en funcionalidades clave como:
    - *Motor de Alertas*: tras el registro de un gasto, el controlador ejecuta un proceso de verificación que filtra y acumula los gastos del periodo correspondiente (semanal o mensual) para contrastarlos con los límites definidos por el usuario.
    - *Sistema de Filtrado*: construcción de predicados complejos que permiten realizar búsquedas multicriterio (fechas, categorías, meses), devolviendo vistas depuradas de la información.

Finalmente, el controlador gestiona el ciclo de vida de la interfaz mediante su vínculo con ControladorVentanaPrincipal, manteniendo una comunicación bidireccional que le permite no solo responder a las peticiones del usuario, sino también actuar de forma proactiva. Gracias a esta conexión, puede notificar a la interfaz ante eventos críticos, como la superación de un presupuesto, y actualizar en tiempo real el estado de los participantes en cuentas compartidas mediante avisos emergentes o la actualización del panel de notificaciones del día.