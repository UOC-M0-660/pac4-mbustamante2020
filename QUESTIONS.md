# PARTE TEORICA

### Arquitecturas de UI: MVP, MVVM y MVI

#### MVVM

##### ¿En qué consiste esta arquitectura?
Esta arquitectura separa la parte visual (View) de la lógica del negocio (Model), relacionándolas con una capa intermedia llamada ViewModel, la cual es la más importante en esta arquitectura. Esta separación de capas facilitan la tarea de testear la aplicación (Unit Test), también facilita el mantenimiento y la escalabilidad de la aplicación.

Model: Es el encargado de proveer los datos desde el backend a los ViewModels, este recibe distintos eventos desde el ViewModel, que le indican acciones a realizar en el backend, tales como crear, leer, actualizar o eliminar.

View: Esta capa muestra la interfaz de usuario (actividades y fragmentos), permite mostrar información al usuario y en la puede interactuar con la aplicación, mediante eventos. En MVVM las vistas pueden referenciar a uno o mas ViewModels para obtener la información que será mostrada e informar al ViewModel de las acciones del usuario.

ViewModel: Esta capa es el intermediario entre la vista y el modelo, solicita información al modelo y la envía a la vista. Teniendo en cuenta que Android es el encargado de administrar los eventos del ciclo de vida de las actividades y fragmentos, este puede destruirlas o crearlas nuevamente en cualquier momento, como respuesta a eventos del usuario o algún otro tipo de acciones, como las rotaciones de pantalla. Al ocurrir esto todos los datos obtenidos previamente se perderán y se tendrán que solicitar nuevamente al backend. Debido a que el ViewModel esta desacoplado de la vista (no tiene ninguna referencia a que vista le solicita algo), este permanece en memoria, almacenando la información en su interior, hasta que el ciclo de vida de la vista termina por completo (cuando la actividad termina o cuando los fragmentos se separan).

##### ¿Cuáles son sus ventajas?
* Facilita las prueba unitarias, esto es debido a que los ViewModel no tienen referencias a las vistas.
* Debido a la clara separación de tareas de cada una de las capas, se reduce el problema conocido como “controladores de grasa”, lo cual facilita su mantenimiento.
* Los ViewModel poseen herramientas que controlan el ciclo de vida de las vistas, con lo cual al haber un cambio en el ciclo de vida de la vista no se pierde la información del ViewModel.

##### ¿Qué inconvenientes tiene?
* Puede ser demasiado complejo para pequeñas aplicaciones, complicando su implementación. Como por ejemplo para que una actividad obtenga datos desde Internet se necesita integrar varias clases para realizar esto, la actividad, el ViewModel, el Repository, el DataSource, Api, por ejemplo.

#### MVP

##### ¿En qué consiste esta arquitectura?
Esta arquitectura funciona haciendo uso de presentadores, los cuales son los intermediarios entre las vistas y el modelo. La vista es punto de entrada principal del usuario (al ejecutar algún evento), luego la vista informa al presentador de dicha entrada, posteriormente el presentador ejecuta la tarea actualizando el modelo y finalmente luego que se ha terminado la actualización en el modelo el presentador actualiza los datos en la vista.

Las capas de esta MVP se detallan a continuación:

Model: Esta es la capa de datos, contiene las clases de las reglas del negocio, que permiten el acceso y la obtención de datos.

View: Esta es la entrada principal, también es la responsable de la interfaz de usuario, mostrando u ocultando vistas, controlando la navegación a otras actividades y escuchando las acciones del usuario que serán informadas al presentador. En MVP las vistas solo será una actividad o fragmento, conteniendo solo el código que permite manejar la interfaz de usuario.

Presenter: Es la clase que se encarga de comunicar las vistas con el modelo, conteniendo todas la funcionalidades que se ejecutarán luego que el usuario interactúa con la vista, ejecutando un evento. Cuando el modelo se actualiza, es responsabilidad del presentador saber de que forma la vista necesitará  los datos, haciendo todo lo necesario, un mapeo o formateo adicional de los datos, para que la vista reciba los datos y los muestre.

Debido a que el presentador no debe extender de ninguna clase especifica de Android, no debe hacer referencia al contexto, no debe tener intenciones y no debe tener ninguna referencia directa a la vista. La forma en que interactúan la vista y el presentador es mediante interfaces, con esto se tienen una separación clara de las 2 clases, facilitando la implementación y ejecución de las pruebas unitarias.

##### ¿Cuáles son sus ventajas?
* Existe una clara separación entre la vista (solo código de UI), presentador (nada del manejo de la UI, no referenciar al contexto, etc) y el modelo.
* Facilita la tarea de realizar pruebas unitarias en el presentador, debido al uso de interfaces con que interactúan la vista y el presentador.

##### ¿Qué inconvenientes tiene?
* Se necesitan muchas interfaces para implementarlo, una para la vista y otra para el presentador.
* Se debe tener presente que al momento de destruir una actividad, también se deben destruir todas las AsyncTasks del presentador, de lo contrario puede ocurrir que se intente actualizar una actividad que haya sido destruida.
* Se debe tener presente de lo que le sucede al presentador, luego que se destruye la actividad, ya que seguirá estando en memoria, si se desea que el presentador libere la memoria, hay que asegurarse de que no este referenciado por otra clase. Si no se libera la memoria del presentador se puede producir una fuga de memoria.

#### MVI

##### ¿En qué consiste esta arquitectura?
Al igual que MVP, esta arquitectura hace uso de presentadores y se introducen los conceptos de intención y estado. La intención es un evento, que representa un deseo de realizar una tarea en particular por parte del usuario, esta es enviada al modelo mediante un Intent (con esto se obtendrá un nuevo estado de la aplicación), luego se notifica a la vista del cambio de estado y se actualiza la vista.

View: Las vistas están representadas por interfaces, que se implementan mediante un actividad o un fragmento. Las vistas tienden a tener un solo método render(), el cual recibe el estado, que se debe mostrar en la interfaz de usuario y tiene varios intent() que reaccionan a las acciones del usuario.

Intent: Representa una intención del usuario de realizar una acción, esta acción es observada por el presentador, con esto se realizará un cambio de estado en el modelo y este cambio de estado será mostrado en la vista por medio del método render().

Model: En MVI el modelo tiene una mayor importancia que en las arquitecturas anteriores, ya que ahora no solo contiene los datos, sino que representa un estado de la aplicación. El modelo toma un intent() como entrada para manipular el modelo, con esto se obtiene un nuevo modelo y se produce un cambio de estado. No se actualiza el modelo actual, si no que se obtiene un nuevo modelo, debido a que se quiere que cada estado del modelo sea inmutable. Al ser un modelo inmutable, se garantiza que el flujo de datos sea unidireccional, esto ayuda a mantener la lógica empresarial como una única fuente de la verdad. Con esto se también se garantiza que el modelo no se modificará en otros lugares, manteniendo un solo estado durante todo el ciclo de vida de la aplicación.
La forma en que se cambia de un estado a otro, es mediante los reductores de estado, en el cual se crea un nuevo estado a partir de un estado anterior. Este nuevo estado, es la combinación del estado anterior más nuevos los cambios y este se convierte en estado actual de la aplicación.

##### ¿Cuáles son sus ventajas?
* Al ser unidireccional y cíclico, el flujo de datos puede ser rastreado fácilmente.
* Se garantiza un comportamiento confiable y la seguridad de los subproceso, debido a que los estados del modelo son inmutables.
* Probar la aplicación también es fácil.

##### ¿Qué inconvenientes tiene?
* La curva de aprendizaje es alta, ya que se deben poseer varios conocimientos previos (programación reactiva, RxJava, entre otros).
* Se acumula una gran cantidad de código, ya que se debe mantener un estado por cada acción del usuario.

---

### Testing

#### ¿Qué tipo de tests se deberían incluir en cada parte de la pirámide de test? Pon ejemplos de librerías de testing para cada una de las partes. 
Pequeñas pruebas:
* Son las llamadas pruebas unidad, estas son pruebas pequeñas y rápidas, que solo se enfocan en probar un solo componente.
* Para ejecutar esta prueba no es necesario hacer uso de un emulador o de un dispositivo real.
* Deben ser la mayor cantidad, esto es debido a que se prueba la base sobre la cual funcionará la aplicación, Google recomienda que este tipo de pruebas sean el 70% del total.
* Para realizar este tipo de pruebas en Android se utiliza Junit y Mockito.

Pruebas medias:
* Son pruebas de integración que permiten comprobar como es que interactúa el código escrito con el framework de Android.
* Se ejecutan después de finalizar las pruebas unitarias.
* Debido a que su complejidad es mayor, ya que integran las funcionalidades testeadas con las pruebas de nivel inferior con el Framework de Android, Google recomienda que este tipo de pruebas sean el 20% del total.
* Roboelectric, es una de las herramientas más se utiliza para realizar este tipo de pruebas. La particularidad de esta herramienta es que las pruebas se ejecutan dentro de una caja de arena y no es necesario hacer uso de un emulador o de un dispositivo real.
* Firebase Test Lab, es un servicio que permite realizar este tipo de pruebas en diferentes tipos de dispositivos, con distintos tamaños de pantalla y diferentes configuraciones de hardware.

Grandes pruebas:
* Son pruebas que emulan el comportamiento del usuario, simulando acciones del usuario en la UI.
* Estas con más lentas y caras que la anteriores, ya que solo se ejecutan en un emulador o en un dispositivo real.
* Estas son las más complejas, se debe tomar más tiempo para implementarlas y se deben utilizar los emuladores o dispositivos físicos para poder ejecutarlas, ya que emulan el comportamiento del usuario con la UI. Debido a esto, Google recomienda que estas sean el 10% del total de pruebas.
* Para realizar este tipo de pruebas se pueden utilizar herramientas como Espresso y UI Automator.

#### ¿Por qué los desarrolladores deben centrarse sobre todo en los Unido Tests?
* Debido a que son pequeñas, es mas rápido ejecutar este tipo de pruebas que las de integración o de simulación. Con lo cual se ahorra una gran cantidad de tiempo y recursos al ejecutar las pruebas  medianas y grandes.
* No requieren librerías de Android, ya que en este tipo de pruebas lo que se desea probar es código regular de Java o de Kotlin.
* Estas pruebas son la base del conjunto de pruebas de la aplicación. Ya que si existe un error en alguna funcionalidad que no fue testeada, este error se mostrará al realizar las pruebas de integración o de simulación. Por lo tanto al probar todas las unidades individuales de código y estas funcionan correctamente, da más confianza, al realizar las pruebas medianas y grandes.

---

### Inyección de dependencias

#### Explica en qué consiste y por qué nos ayuda a mejorar nuestro código.
La inyección de dependencias es un mecanismo en el que si una clase requiere instancias de una o más clases, en vez de generarlas dentro de su propio constructor, las recibe ya instanciadas.

Ayudan a mejorar nuestro código, debido a:
* Ahorro de código, si para crear una nueva instancia de una clase dentro de otra clase se necesitan demasiadas lineas de código para realizar esto, lo recomendable seria es inyectar la instancia de la clase.
* Permite una mayor reutilización de la clase.
* Facilitan la implementación de las pruebas unitarias de una clase.

#### Explica cómo se hace para aplicar inyección de dependencias de forma manual a un proyecto (sin utilizar librerías externas).
Se tienen las siguientes clases:

class Streams {
    private val repository: Repository

    fun getStreams() {
        repository.getAllStreams()
    }
}

class Main {
     fun main(args: Array) {
         val streams = Streams()
         streams.getStreams ()
    }
}

Si se quiere modificar a Streams, para que se le puede inyectar una instancia de Repository.

Se debe modificar el constructor de Streams de la siguiente forma:
class Streams(private val repository: Repository) {

Y en Main se deben realizar los siguientes cambios:
* Se crea una instancia de Repository
              val repository = Repository()

* Se inyecta a repository en Streams
             val streams = Streams(repository)

Finalmente el ejemplo quedaría de la siguiente forma:

class Streams( private val repository: Repository ) {
    fun getStreams() {
        repository.getAllStreams()
    }
}

class Main {
     fun main( args: Array) {
        val repository = Repository()

        val streams = Streams(repository)
        streams.getStreams ()
    }
}

### Ejercicio 6: Análisis de código estático

#### Lint es una herramienta que nos ayuda a revisar las buenas prácticas o guías de estilo de nuestro proyecto que está incorporada directamente a Android Studio. Haz una lista con 5 de ellos y explica de qué problema te avisan y cómo corregirlos.

1. Unused symbol  → Property “TAG” is never used
Se eliminaron los "TAG" indicados

1. Unused import directive
Se eliminaron todas las importaciones no usadas con “Optimize Imports”

1. Style issues → Might be ‘const’
En OAuthFeature se tenia:
private val RefreshKey = "Ktor-OAuth-Refresh"
Y se agregó const:
private const val RefreshKey = "Ktor-Oauth-Refresh"

1. Redundant constructs → Redundant SAM constructor
En LaunchActivity se tenia:
launchViewModel.isUserAvailable.observe( this, Observer { isUserAvailable ->
Y se eliminó Observer:
launchViewModel.isUserAvailable.observe( this, { isUserAvailable ->

1. Redundant constructs → Unnecessary local variable
En TwitchApiService se tenia:
    val response = httpClient
        .get<StreamsResponse>(Endpoints.streamsUrl) {
            cursor?.let { parameter("after", it) }
        }
    return response
Y se modificó a:
    return httpClient
        .get<StreamsResponse>(Endpoints.streamsUrl) {
            cursor?.let { parameter("after", it) }
        }
