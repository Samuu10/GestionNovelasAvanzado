## GESTIÓN NOVELAS AVANZADO

# Objetivo

El objetivo del proyecto era extender la funcionalidad de la practica anterior 'Gestión Novelas'. 
Se buscaba conectar el código fuente con una base de datos de Firebase e implementar mecanismos de sincronización en segundo plano.

# Firebase

La aplicación utiliza Firebase como base de datos para almacenar las novelas,
base de datos a la que se han añadido 20 novelas de ejemplo para probar la applicación
`FirebaseHelper` gestiona todas las operaciones relacionadas con la base de datos,
permitiendo que las novelas se sincronicen y persistan a través de diferentes sesiones de la aplicación.

**Novelas añadidas a Firebase como ejemplo** (IMPORTANTE ESCRIBIR LITERAL para probar la aplicación.)

   - Don Quijote de la Mancha
   - La Odisea
   - La Iliada
   - Crimen y Castigo
   - Orgullo y Prejuicio
   - El Retrato de Dorian Gray
   - Matar a un ruiseñor
   - 1984
   - Moby Dick
   - El Gran Gatsby
   - Ulises
   - Madame Bovary
   - La Metamorfosis
   - En busca del tiempo perdido
   - El guardián entre el centeno
   - El señor de los anillos
   - Cien Años de Soledad
   - Fahrenheit 451
   - Las uvas de la ira
   - El amor en los tiempos del cólera

# Clases JAVA

1. **MainActivity**: 
      - Controla la actividad principal de la aplicación, donde se muestran las novelas.
      - Maneja la lógica para añadir y eliminar novelas, así como para mostrar detalles de cada novela.
      - Contiene métodos para interactuar con el `ListView` de novelas y gestionar la sincronización con Firebase.

2. **FavoritosActivity**: 
      - Maneja la visualización de las novelas marcadas como favoritas.
      - Permite al usuario ver y gestionar su lista de novelas favoritas.

3. **Novela**: 
      - Clase modelo que representa una novela.
      - Contiene atributos como título, autor, año, sinopsis y un indicador de si la novela es favorita.
      - Proporciona métodos para acceder y modificar estos atributos.

4. **NovelaAdapter**: 
      - Adaptador personalizado para el `ListView` de novelas.
      - Se encarga de enlazar los datos de las novelas con las vistas en la interfaz de usuario.
      - Facilita la visualización de cada elemento de la lista con el formato adecuado.

5. **AlarmManagerUtil**: 
      - Clase utilitaria para manejar las alarmas y recordatorios.
      - Utiliza el `AlarmManager` de Android para programar tareas periódicas y gestionar notificaciones.

6. **ConexionReceiver**: 
      - Clase que extiende `BroadcastReceiver` para recibir y manejar cambios en la conectividad de red.
      - Notifica a la aplicación sobre cambios en la conexión para gestionar adecuadamente la sincronización con Firebase.

7. **FirebaseHelper**: 
      - Clase encargada de gestionar la conexión con Firebase.
      - Contiene métodos para cargar, agregar y eliminar novelas en la base de datos de Firebase.
      - Facilita la sincronización de datos entre la aplicación y Firebase.

8. **SyncTask**: 
      - Clase que extiende `AsyncTask` para realizar tareas de sincronización de datos en segundo plano.
      - Permite que la aplicación realice operaciones de red sin bloquear la interfaz de usuario.
   
9. **ConfiguracionActivity**
      - Actividad que representa la pantalla de configuración de la aplicación.
      - Permite al usuario cambiar el tema de la aplicación, hacer copias de seguridad y restaurar datos.

10. **BackupTask**
      - Clase que extiende `AsyncTask` para realizar una copia de seguridad de los datos de la aplicación.
      - Guarda la lista de novelas y el estado del tema en un archivo.

11. **RestoreTask**
      - Clase que extiende `AsyncTask` para restaurar los datos de la aplicación desde un archivo de copia de seguridad.
      - Carga la lista de novelas y el estado del tema desde el archivo.

12. **PreferencesManager**
      - Clase que gestiona las preferencias de la aplicación utilizando `SharedPreferences`.
      - Permite guardar y cargar el tema de la aplicación y la lista de novelas.

# Archivos XML

1. **activity_main.xml**: 
   - Archivo de diseño para la actividad principal de la aplicación.
   - Contiene elementos como `TextView` para títulos, `ListView` para mostrar novelas, y botones para añadir y eliminar novelas.
   - Diseñado para ser amigable y responsive.

2. **favoritos_activity.xml**: 
   - Archivo de diseño para la actividad que muestra las novelas favoritas.
   - Similar a `activity_main`, pero centrado en la visualización de novelas marcadas como favoritas.

3. **agregar_novela.xml**: 
   - Archivo de diseño para el diálogo de agregar una nueva novela.
   - Contiene campos de entrada para el título, autor, año y sinopsis de la novela.

4. **eliminar_novela.xml**: 
   - Archivo de diseño para el diálogo de eliminar una novela.
   - Permite al usuario seleccionar una novela de la lista para eliminarla de su biblioteca.

5. **detalles_novela.xml**: 
   - Archivo de diseño para mostrar los detalles de una novela específica.
   - Incluye información como el título, autor, año, sinopsis y un botón para marcarla como favorita.

6. **item_novela.xml**: 
   - Archivo de diseño para cada elemento en la lista de novelas.
   - Define la apariencia de cada novela en el `ListView`, incluyendo el título y el autor.

7. **configuracion_activity.xml**:
   - Archivo de diseño para la actividad de configuración (`ConfiguracionActivity`).
   - Contiene elementos de interfaz de usuario como botones y switches para realizar acciones de configuración.

Link al reposirotio: https://github.com/Samuu10/GestionNovelasAvanzado.git

Link a la base de datos de Firebase: https://console.firebase.google.com/project/sample-firebase-ai-app-d9b5c/database/sample-firebase-ai-app-d9b5c-default-rtdb/data
