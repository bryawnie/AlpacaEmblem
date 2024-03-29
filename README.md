# Alpaca Emblem v2.5

Alpaca Emblem es un juego de estrategia por turnos. En él existen distintos tipos de unidades e items equipables, los cuales permiten la interacción entre las unidades mismas. A continuación se explicará a grandes rasgos la dinámica del juego, junto con las funcionalidades implementadas hasta esta versión.

## ✰ Novedades de esta versión
- [Factory](./CC3002_Alpaca_Emblem#4-factory), una nueva forma de crear objetos.
- [Tactician](./CC3002_Alpaca_Emblem#5-tactician), el jugador.
- [Game Controller](./CC3002_Alpaca_Emblem#6-game-controller), el controlador.
- Las [unidades](./CC3002_Alpaca_Emblem#1-unidades) ahora tienen una referencia a su respectivo tactician.
- Se modifican ligeramente los tipos de [items](./CC3002_Alpaca_Emblem#2-items), dejando solo 2 tipos; *attack* y *healing*, dejando a *weapon* y *spellbook* como subtipos de *attack*.
- Se completan algunos test con poca veracidad:
  - Restricciones de distancia al transferir items.
  - Restricciones de distancia al contraatacar.

## 1. Unidades
El juego cuenta actualmente con 7 diferentes unidades, las cuales se clasifican en 4 grupos principales; *carrier*, *magic*, *physical* y *healer*. Todas las unidades tienen un comportamiento global similar, sin embargo, difieren en ciertas características específicas, como en la cantidad de items que pueden portar, su ataque y contraataque, los items que pueden (o no) equipar, etc. La siguiente tabla resume de manera general lo anterior:

<table>
  <tr>
    <td><b> Unidad </b></td>
    <td><b> Grupo </b></td>
    <td><b> Max. Items </b></td>
    <td><b> Tipo de Items Equipables </b></td>
  </tr>
  
  <tr>
    <td> Alpaca </td>
    <td><i> Carrier </i></td>
    <td><i> ∞ </i></td>
    <td> Ninguno </td>
  </tr>
  
  <tr>
    <td> Cleric </td>
    <td><i> Healer </i></td>
    <td><i> 3 </i></td>
    <td> Staff </td>
  </tr>
  
  <tr>
    <td> Sorcerer </td>
    <td><i> Magic </i></td>
    <td><i> 3 </i></td>
    <td> Spell Books </td>
  </tr>
  
  <tr>
    <td> Fighter </td>
    <td rowspan="4" ><i> Physical </i></td>
    <td><i> 3 </i></td>
    <td> Axe </td>
  </tr>
  
  <tr>
    <td> Archer </td>
    <td><i> 3 </i></td>
    <td> Bow </td>
  </tr>
  
  <tr>
    <td> Hero </td>
    <td><i> 3 </i></td>
    <td> Spear </td>
  </tr>
  
  <tr>
    <td> SwordMaster </td>
    <td><i> 3 </i></td>
    <td> Sword </td>
  </tr>
  
</table>

Los distintos tipos de items se describen en la sección siguiente.

Cada unidad se compone de las siguientes variables:
- `maxHitPoints`: Máxima cantidad de puntos de vida que puede alcanzar la unidad.
- `currentHitPoints`: Puntos de vida que tiene la unidad, por conveniencia y comodidad en la implementación, corresponde a un  *double*.
- `items:` Lista de items que porta la unidad.
- `equippedItem`: Item equipado, debe pertenecer a la lista de items.
- `maxItems`: Máxima cantidad de items que puede portar la unidad.
- `movement`: Distancia máxima que se puede desplazar la unidad.
- `location`: Ubicación de la unidad.
- `alive`: Indicador de si la unidad está viva (una unidad muere cuando alcanza los 0 *hitpoints*).
- `inCombat:` Indicador de si una unidad se encuentra en combate con otra.
- `owner`: *Tactician* dueña de la unidad.
- `isDeadPCS`: avisa al tactician si es que la unidad fue derrotada en combate y ha muerto.
- `moved`: indica si es que fue movida dentro del turno.

### 1.1 Implementación General de las Unidades
En esta versión se añadió una nueva unidad, el *sorcerer*, el cual puede usar libros de magia para atacar a sus oponentes. 

Las dinámicas e interacciones de todos las unidades son muy similares, de modo que cada unidad solo desciende de un *AbstractUnit*, clase abstracta que define los metodos en común entre las unidades.

Los campos `maxHitPoints`, `maxItems`, `alive` e `inCombat`, fueron añadidos en la versión 1.1 del juego. El objetivo del primero es almacenar los máximos **hitpoints** que puede tener la unidad, para evitar *overhealing* y curaciones infinitas. La segunda nueva variable fue creada con la intención de realizar futuras validaciones para interacciones entre *item-unit* y otros. El objetivo de `alive` es facilitar el testeo de casos bordes y *setear* cuando alguien muere como un estado. Por otro lado, pareció interesante de definir la variable `inCombat` para alguna aplicación futura, donde sea importante si una unidad se encuentra en combate en cierto momento.

En la versión 2.5 además se añaden los campos `owner`, `isDeadPCS` y `moved`. La funcionalidad de estos se explica a continuación, el primero sirve para identificar una unit en el contexto de que ahora existen *Tacticians* que son quienes manejarán sus respectivas unidades, de modo que resulta útil conocer a quién pertenece una unidad en caso de combate o movimiento. El segundo, sigue el patrón de diseño *Observer* donde la unit es el objeto observado y (de terminos practicos) su *Tactician* es el observador, esto con el fin de que cuando una unidad muera, como es imposible de revivirla, simplemente sea eliminada del juego. Por último, dentro del contexto de un **turno** (se explica mas en la sección de Game Controller), un *Tactician* solo puede mover a cada una de sus unidades una vez, de modo que `moved` permite *checkear* si la unidad ya fue movida, para que no sea posible moverla de nuevo.

Al añadir un item, es necesario que este no tenga dueño previamente, de modo que dos unidades no pueden añadir el mismo item ya que si una lo añade, automáticamente se setea como su dueña. La única forma de transferir items es con el método respectivo explicado más adelante.

## 2. Items
Existen 3 tipos de items, *weapons*, *spellbooks* y *healing*. Algunos items son fuertes (o bien débiles) contra otros y se pueden equipar a distintas unidades de acuerdo al cuadro mostrado en la sección anterior. La clasificación de los items va de acuerdo a lo que muestra el siguiente cuadro:

<table>
  <tr>
    <td><b> Item </b></td>
    <td><b> Tipo </b></td>
    <td><b> Subtipo </b></td>
    <td><b> Fuerte v/s Item </b></td>
    <td><b> Debil v/s Item </b></td>
  </tr>
  
  <tr>
    <td> Axe </td>
    <td rowspan="7"><i> Attack </i></td>
    <td rowspan="4"><i> Weapon </i></td>
    <td> Spear </td>
    <td> Sword </td>
  </tr>
  
  <tr>
    <td> Sword </td>
    <td> Axe </td>
    <td> Spear </td>
  </tr>
  
  <tr>
    <td> Spear </td>
    <td> Sword </td>
    <td> Axe </td>
  </tr>
  
  <tr>
    <td> Bow </td>
    <td> - </td>
    <td> - </td>
  </tr>
  
  <tr>
    <td> Darkness </td>
    <td rowspan="3"> Spellbook </td>
    <td> Spirit </td>
    <td> Light </td>
  </tr>
  
  <tr>
    <td> Spirit </td>
    <td> Light </td>
    <td> Darkness </td>
  </tr>
  
  <tr>
    <td> Light </td>
    <td> Darkness </td>
    <td> Spirit </td>
  </tr>
  
  <tr>
    <td> Staff </td>
    <td> Healing </td>
    <td> Healing </td>
    <td> - </td>
    <td> - </td>
  </tr>
  
</table>

A su vez, todos los items de tipo *spellbook* son fuertes contra los no-*spellbook* y viceversa. 
Cada item se compone de las siguientes variables:

- `name`: nombre del item.
- `power`: poder del item (cuanto daño hace o cuanto logra curar a otra unidad).
- `minRange`: rango mínimo.
- `maxRange`: rango máximo.
- `owner`: unidad que es dueña del item.

### 2.1 Implementación General de los Items
Dados los tipos de items disponibles en el juego, se crea una *interfaz* para ayudar a identificar ciertos tipos de ellos, de modo que existen las interfaces **ISpellBook** y **IHealing**,  donde la utilidad del primero es poder identificar todos los items tipo mágico junto con los metodos que podrían tener en común, mientras que la segunda no otorga una utilidad muy clara de momento, pues solo existe un objeto de tipo *healing*, pero podría ser útil si eventualmente se añade otro item de este tipo.

Se usan clases abstractas para definir métodos en común entre los items del mismo tipo. Por ejemplo, recibir el ataque de una espada puede ser diferente a recibir el de un acha si el arma receptora es una lanza, pero da exactamente lo mismo si el arma receptora es un *darkness book*.

## 3. Interacciones

### 3.1 Movimiento
Una unidad puede moverse por el mapa según la restrinja su campo `movement`. Al mover una *unit* desde un punto del mapa al otro, primero verifica que no haya ninguna unidad en ese espacio del mapa en específico, de lo contrario el movimiento no es posible. Para verificar la presencia de una unidad en el mapa, cada *Location* guarda una referencia a la unidad que se ubica en dicha posición, al moverse, esto es reseteado para la ubicación antigua quedando disponible para otras unidades.

A nivel del juego, una unidad se puede mover 1 vez por turno por parte de su respectivo *Tactician*, si se intenta mover a una *Location* ya ocupada, esto no cuenta como un movimiento en sí, de modo que se podrá intentar hasta hacer un movimiento efectivo.

### 3.2 Equipar Item
Cada unidad puede equipar (o no) cierto tipo de items específicos, tal como se mostró en el cuadro de unidades. Para que una unidad no se equipe un tipo de item incorrecto, se utiliza *double dispatch*, haciendo que el item se equipe a la unidad, desambiguando así el tipo de item y la unidad en cuestión.

### 3.3 Dar Item
Una unidad puede entregar un item que esté portando siempre y cuando la unidad receptora porte menos de su máximo de items y ambas unidades esten a distancia 1. Si se regala un ítem que esté equipado, la unidad quedará sin item equipado. Además, cada vez que un item cambie de unidad, su dueño será la unidad que lo porte consigo.

Adicionalmente, a nivel del juego implementado en [Game Controller](./CC3002_Alpaca_Emblem#6-game-controller), solo es posible dar un item a otra unit perteneciente al mismo [Tactician](./CC3002_Alpaca_Emblem#5-tactician).

### 3.4 Usar Items

Todas las unidades que puedan tener un item equipado, pueden utilizarlo para interactuar con otra unidad bajo ciertas restricciones:
- Ambas unidades participantes  deben estar vivas.
- El item **debe** estar equipado.
- La unidad objetivo a recibir la interacción debe estar a una distancia que se encuentre dentro del rango del item.

#### 3.4.1 Ataque
Se rige bajo las reglas del uso de items. Cada ataque desencadena a su vez un contraataque inmediato por parte de la unidad atacada, siempre y cuando cumpla con las mismas restricciones necesarias para cualquier uso de items. Las alpacas y los *clerics* no pueden ni atacar ni contraatacar, por lo que al ser atacados, solo reciben daño y el combate finaliza de inmediato sin respuesta alguna.

Si en el ataque, una unidad recibe más daño que sus *currentHitPoints*, esta muere y pasa a estar fuera de combate.

El daño recibido por un ataque puede variar según las armas de los participantes del encuentro, en particular se pueden dar 4 casos:
1. Si el arma del atacante es fuerte contra el arma de la unidad atacada, el daño será el poder del arma de ataque x1,5.
2. Si es débil, el daño será poder del arma de ataque -20 (en particular, si esto resulta en una curación, el daño será 0).
3. Si no hay una relación en particular entre las armas, el daño será el poder del arma de ataque.
4. Si el objetivo no posee un arma equipada, el daño será también el poder del arma de ataque.

En caso de que el daño recibido sea mayor a la vida de la unidad, sus *currentHitPoints* sólo disminuirán a 0, impidiendo niveles menores a 0.

#### 3.4.2 Curaciones
Toda unidad que equipe un item de tipo *healing* puede curar a otras unidades que se encuentren dentro del rango del item. Actualmente, existe sólo un item de este tipo, el *Staff*, y sólo puede ser equipado por un *Cleric*. Cuando una unidad es curada, sus *currentHitPoints* se restauran de acuerdo al poder del *staff*. 

Cada unidad posee un máximo de puntos de vida, si al curar estos se pudieran sobrepasar, la unidad objetivo aumentará sus *currentHitPoints* sólo hasta el máximo permitido.

#### 3.4.3 Diseño
El uso de un item para atacar o curar a otra unidad depende principalmente de los items involucrados en el encuentro. Por lo tanto, la implementación del uso de items, junto con los daños a otras unidades, curaciones, ataques fuertes, ataques débiles, etc, está delegado al item mismo. 

Los items de tipo *AttackItem* implementan un método `attack`, mientras que los de tipo *HealingItem* implementan un método `heal`. Además, todos los items implementan `useOn`, el cual llama a `attack` o `heal` según el tipo de item.

Cuando una unidad usa su item contra otra, dado que cada unidad porta un tipo de item específico, no se sabe a priori con qué arma se está realizando el *ataque/curación*, pero el arma sí sabe que tipo de arma es. De modo que el método *useItemOn* de las unidades, se delega al metodo *useOn* del item. El ítem sí sabe que es, de modo que es posible usar *double dispatch* para que el ítem del enemigo (*unknown*), reciba el *ataque/curación* del primer ítem (*known*).

## 4. Factory
Una factory es un patrón de diseño en el cual una clase "fabrica" instancias de objetos, haciendo más facil la creación de estos y pudiendo fijar métodos que retornen cierto objeto con parámetros predeterminados.
Para facilitar la futura creación de unidades por parte de un [*Tactician*](./CC3002_Alpaca_Emblem#5-tactician), se implementarán *factories* de unidades, items y del mapa, se verá cada caso por separado a continuación.

### 4.1 Units Factory
A nivel implementación, toda clase fábrica de unidades que implemente la interfaz `IUnitsFactory` debe implementar los siguientes 4 métodos:
- `createUnit`: metodo que recibe todos los parámetros necesarios para crear una unidad.
- `createGenericUnit`: crea una unidad genérica, con 100 de HP y 3 de movimiento.
- `createTankUnit`: crea una unidad tanque, con 200 de HP y 1 de movimiento.
- `createFastUnit`: crea una unidad de largo desplazamiento, con 70 de HP y 5 de movimiento.

La idea detrás de esto es crear unidades por default que estén relativamente balanceadas entre sí. Una unit con parámetros ya fijos (generic, tank y fast) se crea en una invalid Location, con un *Tactician* `owner` nulo. Luego, son asignadas a un jugador por medio del *Controller* y ubicadas en un punto del mapa.

### 4.2 Items Factory
Al igual que para las unidades, se desarrolla una interfaz común para todas las fábricas de items, llamada `IItemsFactory`, la exige la implementación de los métodos:
- `create`: metodo que recibe todos los parámetros necesarios para crear un nuevo item.
- `createGenericItem`: crea un item genérico, con 30 de poder, 1 de rango mínimo y 5 de rango máximo.
- `createPowerfullItem`: crea un item con alto poder, con 50 de poder, 1 de rango mínimo y 3 de rango máximo.
- `createLongDistanceItem`: crea un item para larga distancia, con 10 de poder, 3 de rango mínimo y 10 de rango máximo.

**Nota:** Un arco tiene de por sí distancia mínima 2, de modo que es una excepcion para el rango mínimo en los items genericos y de alto nivel de poder.

Al igual que para las units, la idea de esta distribución de parámetros es generar items balanceados. Los items con parametros preseteados sólo solician el nombre para la creación.

### 4.3 Field Factory
Para crear un mapa es necesaria una *seed* de tipo `long`, un tamaño, y saber si se quieren conectar todas las ubicaciones o no. Si se desea que cada nodo esté conectado a todos sus vecinos, la semilla no tiene mucho sentido de ser aplicada; pero si se quiere que se conecten al azar, la *seed* ayuda a obtener una secuencia "aleatoria" replicable.

Las dimensiones del mapa son cuadradas, es decir, con tamaño nos referimos a cantidad de filas y columnas.

## 5. Tactician
Un *Tactician* representa a un jugador. La misión de esta entidad es manejar las instrucciones que podría dar un usuario y delegarlas al modelo, de este modo, se evita que quien usa la aplicación interactúe con este úlimo de forma directa.

Cada *Tactician* tiene las siguientes variables:
- `units`: corresponde a una lista con todas las unidades que pertenecen a ese jugador. Un jugador puede añadir unidades a la lista, ya sea definiendolas de forma completa, o usando las predefinidas en las [*UnitsFactory*](./CC3002_Alpaca_Emblem#41-units-factory).
- `selectedUnit`: Unidad seleccionada, es a esta unidad a la cual podrá ver sus parámetros específicos y con la cual podrá interactuar con otras unidades (uso de items, intercambios, etc). Cabe destacar que las acciones sobre la unidad seleccionada están restringidas a que la unidad pertenezca al propio *Tactician* (esto no tiene por qué suceder necesariamente). El jugador puede seleccionar otra unidad o desseleccionar una unidad cuando estime conveniente.
- `selectedItem`: item seleccionado por la `selectedUnit`.
- `equippedItem`: Item equipado por la unidad seleccionada, se guarda la referencia para mayor comodidad en la implementación.
- `field`: Mapa del juego.
- `name`: Nombre del jugador.
- `heroDeadPCS`: Avisa al [controlador](./CC3002_Alpaca_Emblem#6-game-controller) sobre la muerte de un heroe en la lista de unidades del jugador.

### 5.1 Responsabilidades
Dentro de las posibilidades de un jugador estan añadir unidades y seleccionar (o deseleccionar) una unidad. Si la unidad seleccionada pertenece a sus unidades (esto no tiene por qué pasar necesariamente), puede añadirles items, equiparles un item (adecuado a la unidad), intercambiar items entre esta y otra unidad cualquiera, atacar a una unidad de otro tactician o curar una unidad propia. 

Un *tactician* puede saber si la unidad seleccionada es suya, de modo que si intenta hacer algo con una unidad de otra persona, no podrá hacer nada. A su vez, un tactician puede conocer los items de sus unidades y el item equipado por cualquier unidad seleccionada. Además, puede conocer toda la información de sus propias unidades (hay métodos directos para ello), siempre y cuando la unidad sea seleccionada previamente.

Dos tactician's no pueden tener las mismas unidades, ya que como condición previa para añadir una unidad, se requiere que el *Tactician owner* sea un *null*, y se setea inmediatamente al *owner* como el *Tactician* en cuestión.

### 5.2 Cuida tus Heroes
Si un jugador tiene algún Hero y este muere por efecto del ataque de otra unidad, el tactician pierde el juego (y es borrado de la partida, más sobre esto en [Game Controller](./CC3002_Alpaca_Emblem#6-game-controller).

### 5.3 Observer Pattern
Un *Tactician* es **observador** de sus *Units*, de modo que cuando una de ellas muere, es "avisado" por medio de un *handler* que ocurrió tal evento. De este modo puede eliminar de su lista las unidades que ya no se pueden usar. Un caso específico es el *Hero*, como se mencionó en el apartado previo, si un héroe muere, el jugador pierde la partida. Por lo tanto, la muerte de un *Hero* llama a dos *Handlers*, el mencionado anteriormente y uno que llama a un método de tactician, que a su vez "avisa" a un *handler* del Controlador, que debe ser eliminado de la partida. 

## 6. Game Controller
El controlador es el encargado de mantener el estado del juego en todo momento, es con esta entidad con la que finalmente, el usuario interactuará de forma más "directa". Como dice su nombre, es el que controla los aspectos del juego. Se compone de los siguientes campos.

**Respecto al Controller en General:**
- `tacticians`: Lista con todos los jugadores.
- `playerInTurn`: *Tactician* actualmente en turno, con disponibilidad para ejercer acciones como mover sus unidades, atacar, intercambiar items, equipar unidades, etc. Debe terminar su turno cuando no tenga más acciones por realizar.
- `random`: esta variable permite hacer asignaciones aleatorias eventualmente replicables.
- `seedPlayerSelection`: semilla que fija el random previo, se utiliza para crear una secuencia "aleatoria" (no tanto) identica.
- `numberOfPlayer`: numero de jugadores en la partida.
- `maxRounds`: máximo número de rounds en la partida, si se desea un duelo a muerte hasta que solo quede uno, se define un juego con infinitos rounds, donde a nivel implementación significa que `maxRounds=-1`.
- `winners`: ganadores de la partida.

**Configuraciones del Juego**
- `mapSize`: dimensiones del mapa, corresponde a un entero ya que el mapa es cuadrado.
- `map`: mapa de juego, se genera por medio de una *Factory*.
- `roundNumber`: número del round actual.
- `turnNumber`: número del turno actual.
- `orderRound`: una lista de jugadores pero en el orden en que deben jugar el round actual.

**Property Change Listeners**
- `heroDeadPCL`: listener para cuando una unidad *Hero* muere en combate, esto detona que el jugador pierde la partida y sea eliminado por el controller.

**Factories**
- `unitsFactory`: fábrica de unidades. Cuando se quieran añadir unidades a un *Tactician* se hará a través de esta factory, solo es necesario setear que tipo de unidad se quiere crear, y luego añadir ya sea una *Unit* preseteada o una totalmente configurable.
- `itemsFactory`: fábrica de items. Cuando se quieren añadir items a una *Unit* se usa esta factory en un proceso similar al de añadir una unit.

**Nota**: como al crear una unidad por medio de una *Factory*, se setea en una *Location* invalida, hay que setearle de forma manual donde se le quiere ubicar dentro del mapa.

### 6.1 Turnos
Un turno es la instancia donde un *Tactician* puede efectuar sus movimientos. Un *Tactician* puede mover a cada una de sus unidades ubicadas en el mapa 1 vez, donde no se consideran movimientos fallidos, donde se intenta mover una *Unit* a una celda ocupada. Por otro lado, de momento una unidad puede usar su item sobre otra las veces que el *Tactician* en turno estime conveniente (probablemente se modifique en un futuro esto).

En cualquier momento de su turno un jugador puede decidir no realizar más acciones, en cuyo caso termina su turno y se pasa al turno del jugador siguiente.

### 6.2 Héroes
Si el héroe de un jugador es derrotado en el turno de cualquier otro, entonces este jugador pierde la partiday se retira del juego junto con todas sus unidades. En cambio, si el héroe es derrotado en el turno del mismo jugador al que pertenece entonces se termina su turno antes de ser excluido de la partida. 

Un usuario puede tener más de un héroe en juego, en cuyo caso pierde la partida si cualquiera de estos es derrotado.

### 6.3 Rondas de Juego
Una ronda de juego se definirá como un ciclo en que todos los jugadores usen su turno. Al comenzar una partida se decidirá de forma aleatoria el orden en que jugarán los usuarios, y al final de cada ronda se seleccionará un nuevo orden de juego de manera aleatoria, con la restricción de que un jugador no puede tener dos turnos seguidos.

### 6.4 Inicio de la Partida
Para comenzar una partida, el controlador:
- Crea a los jugadores, de tal forma que sus nombres son "Player 1", "Player 2", etc. (en el orden que se vayan creando).
- Proporciona métodos para iniciar y crear unidades para los *Tacticians*, en esta version se utilizan las *facories* para añadir y crear unidades e items a cada *Tactician*, estos métodos sólo son utilizables si se encuentra en un *Turno 0*, el cual es previo al inicio del juego y es específico para añadir unidades.
- Crea un mapa aleatorio (a partir de una *seed*).

**Nota:** En ningún momento del juego puede haber dos unidades en la misma casilla, pero si una unidad es derrotada
entonces se retira de su celda.

### 6.5 Para Ganar
Existen dos maneras de ganar el juego:
1. Sólo queda 1 *Tactician* en el juego.
2. Se alcanza una cantidad máxima de turnos (consideramos que -1 significa que se jugará sin limite de puntos). El ganador en estos casos se define como el jugador con mayor número de unidades restantes. En caso de que dos o más jugadores tengan la misma cantidad de unidades la partida se declara empatada.

## 7 Ejecución y Tests
Las funcionalidades de Alpaca Emblem v2.5 descritas previamente sólo son ejecutables a partir de los *test* implementados. Estos prueban el correcto funcionamiento de los equipamientos e intercambios de items, los ataques y las curaciones. Se cuenta con un 99% de *coverage* donde se intentó testear casos borde que se espera haber resuelto en la implementación.

Los *tests* de ataque se implementan en cada unidad por separado, ya que existen ataques normales, fuertes y débiles para cada caso. Por otro lado, los de las curaciones se implementan en la clase abstracta que testea los *healings* para cada unidad existente.

Los *test* de interacciones entre items, se prueban en las clases abstractas del tipo de item si es una interacción general (por ejemplo, recibir el efecto de una arma fisica siendo un arma mágica), y si es una interacción más específica, el testeo se realiza para el item en particular (recibir un ataque de *darkness* siendo *light*).

### Novedades version 2.5
Dado que varios metodos implementados por el controlador son llamadas a métodos del *Tactician*, algunas funcionalidades específicas se testean en *TacticianTest*, como por ejemplo:

- Al morir una unidad, se espera que el [Tactician](./CC3002_Alpaca_Emblem#5-tactician) la borre, teniendo que además desaparecer del mapa (`deadOfUnitTest`).
- No se puede añadir una nueva unidad en una celda ocupada. Si se intenta hacer esto, simplemente la unidad no queda ubicada en el mapa y hay que intentar asignarle una nueva posición (`addUnitInNonEmptyCellTest`).
- No se puede curar una unidad de otro tactician ni atacar una unit del mismo tactician (`attackTest`).
- Si un tactician selecciona una unidad que no es suya, no puede moverla ni atacar con ella (`permissionsOnSelectedUnitTest`). Tampoco puede ver la información detallada de su item equipado ni obtener ninguno de los items que tiene (`itemMethodsTest`).

En *GameControllerTest* se testean funcionalidades como:
- Si se termina una ronda, el jugador que inicie la siguiente no puede ser el mismo que terminó la ronda anterior (`getTurnOwner`).
- No es posible dar un item a una unidad de otro tactician (`giveItemTo`).
- Al morir un *Hero* su *Tactician* es eliminado de la partida (`heroDeathTest`)
- Al morir un *Hero* en el turno de su propio *Tactician*, su turno termina (`heroDieInMyTurn`).
- Si la al hacer un movimiento la celda está ocupada, la unidad no se mueve y es posible intentar moverla de nuevo hasta que el cambio de posición sea efectivo (`moveToUnavailableCell`).
- No se puede mover la misma unidad 2 veces dentro de un mismo turno (`moveTheSameUnitInATurn`).
- No se pueden añadir *units* a un *Tactician*, ni *items* a una *unit* cuando el juego ya empezó (`addUnitAfterInitGame`).
- No se puede atacar a otra *unit* antes de que empiece el juego (`useItemBeforeGameInit`).

# Alpaca Emblem v2.5
