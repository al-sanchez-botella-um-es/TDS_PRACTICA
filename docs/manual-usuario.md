# Manual de Ejecución (Eclipse)

El proyecto permite dos modos de ejecución: **Interfaz Gráfica** y **Modo Consola**. 

> **Nota:** Asegúrese de cumplir los requisitos previos indicados en el 'README.md' (tener el proyecto importado como Maven Project, Java instalado, etc.).

---

### 1. Modo Interfaz Gráfica
 1. En el explorador de paquetes, navegue hasta: `src/main/java/umu/tds/AppGastos.java`.
 2. Haga clic derecho sobre el archivo y seleccione **Run As -> Java Application**.

### 2. Modo Consola (Eclipse)
 1. Acceder a `src/main/java/umu/tds/controlador/Terminal.java`.  
 2. Haga clic derecho sobre el archivo y seleccione **Run As -> Java Application**.
 3. La aplicación se iniciará en la pestaña "Console" de Eclipse. Debe hacer clic dentro de esa consola para poder escribir. 


#### Solución de problemas JavaFX
Si obtiene un error al iniciar, debe configurar los argumentos de la máquina virtual (VM):
 1. Vaya al menú **Run -> Run Configurations**.
 2. Seleccione la pestaña **Arguments**.
 3. En la caja **VM arguments**, pegue el siguiente comando:
 `--module-path "..\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml`