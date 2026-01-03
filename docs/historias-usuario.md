# "Historias de Usuario"
### 1. REGISTRAR DATOS
Como usuario quiero registrar mis transacciones personales para llevar un seguimiento de mis finanzas. 

***Criterios de aceptación:***
- Dado que el usuario se encuentra en la terminal o mediante la interfaz gráfica proporcionada por la aplicación cuando quiere registrar datos entonces el sistema mostrará un mensaje indicando la información añadida en la base de datos.

---
### 2. AÑADIR GASTOS AL SISTEMA
Como usuario quiero asociar a cada registro de gastos la cantidad y la fecha correspondientes para tenerlos organizados tanto en la interfaz como en la línea de comandos. 

***Criterios de aceptación:***
- Dado que el usuario ha introducido una serie de gastos con características para diferenciarlos cuando le da a guardar entonces el sistema comprobará que los valores son válidos, siendo la fecha real y válida y la cantidad no puede ser negativa. 
- La información debe guardarse de forma persistente. 
- Los gastos se guardarán en una categoría predefinida (alimentación, transporte o entretenimiento) o en una nueva categoría según las necesidades del usuario.

---
### 3. ORGANIZACIÓN EN CATEGORÍAS
Como usuario quiero organizar mis gastos en categorías para organizarlos según mis necesidades. 

***Criterios de aceptación:***
- Dado que el usuario ha creado una nueva categoría cuando ha introducido un nuevo gasto entonces se mostrará un mensaje indicando este movimiento. 
- Dado que el usuario ha añadido un nuevo gasto a una carpeta ya existente entonces también se informará de ello.

---
### 4. ACCIONES POSIBLES SOBRE LOS DATOS
Como usuario que ha introducido gastos previamente quiero poder editar o eliminar algunos de ellos para modificar la fecha, la cantidad o simplemente porque ya no deseo conservarlos (para poder mantener mi lista de registros actualizada). 

***Criterios de aceptación:***
- El usuario podrá consultar sus gastos existentes al acceder en las diferentes carpetas. 
- Se mostrará un mensaje indicando la acción realizada. 
- Al intentar eliminar un gasto, se preguntará si está seguro porque el dato se borrará permanentemente de la base de datos y al volver a iniciar la aplicación no estará disponible.

---
### 4.5 USO DE LA LÍNEA DE COMANDOS
Como usuario que ha introducido gastos anteriormente quiero poder editarlos o eliminarlos mediante la línea de comandos para mantener mis gastos actualizados.  

***Criterios de aceptación:***
- Se mostrará un mensaje indicando la acción realizada. 
- Para añadir se usará ‘add gasto fecha cantidad’. 
- Para eliminar se utilizará ‘remove gasto’. 
- Y para editarlo, 'modify gasto’ y se incluirán los parámetros que se quieran modificar (ya sea la fecha, la cantidad o ambas)

---
### 5. REPRESENTACIÓN DE LOS GASTOS
Como usuario quiero poder ver de forma visual mis gastos de una determinada categoría tanto en listas/tablas como en gráficas (diagrama de barras y/o circulares) y en un calendario. 

***Criterios de aceptación:***
- La aplicación leerá los datos que se hayan añadido previamente y el sistema lo mostrará de la forma en la que el usuario haya elegido. 
- Para el calendario se usará la vista proporcionada por CalendarFX. 
- Y para las representaciones mediante listas, tablas y gráficas se usarán las clases facilitadas por JavaFX.

---
### 6. FILTRAR GASTOS
Como usuario quiero poder filtrar los gastos por una lista de meses, por intervalos de fechas personalizados y por una lista de categorías para poder analizar y ver mis gastos de distintas formas. 

***Criterios de aceptación:***
- Filtrado por ‘Meses’: El usuario seleccionará meses específicos y el sistema buscará en cada categoría mostrándole los gastos en esos meses. 
- Filtrado por ‘Fechas Personalizadas’: el usuario define un intervalo de fechas y se mostrarán gastos dentro de ese rango. 
- Filtrado por ‘Categorías’: el usuario selecciona categorías específicas y el sistema muestra todos los datos que tenga de esas categorías elegidas.

---
### 6.5 FILTRAR GASTOS EN COMBINACIÓN
Como usuario quiero poder filtrar mis gastos de forma combinada para poder obtener consultas más precisas. 

***Criterios de aceptación:***
- El usuario utilizará varios filtros a la hora de consultar gastos y el sistema cumplirá con las condiciones de cada uno detalladas en la historia de usuario anterior.

---
### 7. ALERTAS CONFIGURABLES
Como usuario quiero poder establecer alertas de forma semanal, mensual y opcionalmente pueden estar vinculadas a una categoría específica para poder limitar mis gastos.  

***Criterios de aceptación:***
-  El sistema deberá ir comprobando cada una de las alertas definidas por el usuario y que no superen la cantidad máxima establecida para gastar. 
- Si se cumple la condición anterior, saltará una notificación avisando de la alerta preconfigurada.

---
### 8. VISUALIZAR NOTIFICACIONES
Como usuario quiero poder visualizar todas las notificaciones sobre las alertas que han saltado para poder tener un mejor recuento de todos los gastos. 

***Criterios de aceptación:***
- Todas las notificaciones tienen que estar a disposición del usuario mediante un historial de visualización que irá guardando las alertas activadas.

---
### 9. CUENTA DE GASTOS GRUPAL POR DEFECTO
Como usuario quiero poder compartir una cuenta de gastos con otras personas para que todos los gastos se distribuyan de forma equitativa. 

***Criterios de aceptación:***
Cuando una persona introduce un gasto, el sistema debe mostrar: 
1- la cantidad del gasto de la persona que lo ha pagado (indicándolo de forma positiva) 
2- el saldo de dinero pendiente por el resto (representado de manera negativa)

---
### 9.5 CUENTA DE GASTOS GRUPAL CON PORCENTAJE
Como expansión de la historia de usuario anterior, se podrá definir el porcentaje de gasto asumido por cada persona (en vez de hacerlo de forma equitativa). 

***Criterios de aceptación:***
- Una vez registrado un gasto, tomando su valor, el sistema calculará el dinero que le corresponde pagar a cada persona dependiendo del porcentaje asociado a cada una.

---
### 10. IMPORTAR DATOS DE GASTO DE FUENTES EXTERNAS
Como usuario quiero poder importar gastos de fuentes externas (como un listado de gastos generados desde una plataforma bancaria). 

***Criterios de aceptación:***
- El sistema debe poder soportar diferentes formatos de texto plano. 
- Los datos importados se integran en la aplicación como si fueran registrados manualmente.
