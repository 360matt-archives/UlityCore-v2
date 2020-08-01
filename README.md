# Coucou les kiwis !

### Si vous lisez ce wiki c'est que vous souhaitez probablement créer un AddOn de UlityCore,

### Les tutos que je rédigerai permettront de vous apprendre à bien utiliser l'API que j'ai mis à disposition.

**-**

Au tout début, nous avons besoin d'importer UlityCore en dépendance dans votre IDE.

Une fois cela fait, créez le plugin avec sa MainClass et le plugin.yml.


Maintenant il est temps d'initializer l'API
* ajoutez dans le onEnable() : <pre><code>Initializer init = new Initializer(this);</code></pre>
* sautez deux lignes. lol.
* créez une condition: <pre><code>if (init.ok){ }</code></pre>
entre les accolades de cette condition se trouvera le code de l'Addon c'est à dire les définitions de commande, events, etc ...


⚠️ ℹ️ Si l'API rencontre une erreur, les AddOns se désactiveront par mesure de sécurité



# Propriétés de l'initializer
### ⚠️ ces propriétés ont une conséquence sur le comportement de votre Addon

Souvenez-vous dans le tuto n°1 nous avons évoqué d'initialiser l'Addon, mais sans préciser les propriétés.

Je vous fais donc une liste de ces propriétés:
* `<#>.requireVersion("2.0")` exigera que le Core soit au minimum à la version 2.0. Indispensable pour prévenir l'incompatibilité et empêcher l'Addon de se charger pouvant provoquer des erreurs parfois irréversibles.
* `<#>.reloadLang()` si l'Addon est dépendant d'un module linguistique, même monolinguistique, UlityCore en a une toute prête. cette propriété charge les fichiers de langue .yml situés dans le package **.languages**. Elle recharge si elle est ré-exécutée.
* `<#>.ok` n'est pas une méthode mais un field étant un booléen indiquant le status de l'Initializer. une valeur mise à **false** devrait empêcher l'Addon de se charger, et le désactive, dans le tuto n°1 vous devez avoir fait la condition qui test la valeur avant de register les commands et events.



# Se familiariser avec le module linguistique

### le module linguistique est multilingue (ou monolingue).
### Extrêmement simple à mettre en place et à utiliser.


## 1. Mettre en place
* Appelez la méthode <#>.reloadLang() de l'instance de l'Initializer lors du démarrage du plugin.
* créez un package **languages** ⚠️ à l'interieur du package contenant votre class principale.
* ajoutez vos fichiers de langue, d'extension .yml avec comme nom l'id de la langue au format 2 lettres.


## 2. Utiliser, récupérer les données de langue
* la class concernée est fr.ulity.core.api.Lang

Les méthodes de cette class sont:
* `reloadCore()` : reload les fichiers de langue du Core.
* `reloadAddons()` : reload les fichiers de langue de tous les Addons.
* `reloadOneAddon( plugin.getClass() )` : reload les fichiers de langue d'un Addons grace à la class de l'object plugin.

'

'

ℹ️ Obj correspond soit à un String étant la langue en ISO, soit un object player, proxiedPlayer ou soit sender.

* `get( Obj, String: exp)` :       Renvoie l'expression en fonction de Obj
* `get( String: exp)` :            Renvoie l'expression en fonction de la langue par défaut du serveur

* `getInt(Obj, String: exp)` :      Renvoie un entier en fonction de Obj
* `get( String: exp)` :            Renvoie un entier en fonction de la langue par défaut du serveur

* `getStringArray(Obj, String: exp)` :      Renvoie un array de type String en fonction de Obj
* `getStringArray( String: exp)` :         Renvoie un array de type String en fonction de la langue par défaut du serveur

* `getStringArrayColor(Obj, String: exp)` :      Renvoie un array de type String formaté de couleurs en fonction de Obj
* `getStringArrayColor( String: exp)` :         Renvoie un array de type String formaté de couleurs en fonction de la langue par défaut du serveur

* `getBoolean(Obj, String: exp)` :      Renvoie un booléen en fonction de Obj
* `getBoolean( String: exp)` :         Renvoie un booléen en fonction de la langue par défaut du serveur

* `prepare( String: exp)` :         Depuis v2.3, renvoie un objet Prepared. Lire ci-dessous.

## Depuis la v2.3:

#### Description:
Le but de la création de cette Class est de faciliser le développement et la lecture du code lors de l'utilisation du module linguistique.

Les variables sont plus facile à être ajoutée.

Pour les envoies multiples à plusieurs joueurs, possiblement de différentes langues, l'exp et les variables seront définis qu'une seule fois et chaque joueurs recevra le message dans sa langue. Les variables peuvent être modifiés au long du code.

#### Class Prepared:

``constructor ( exp )``

``prefix ( String ) -> this`` --> défini un préfix, comme une couleur, etc ...

``suffix ( String ) -> this`` --> défini un suffix, comme un point, etc ...

``variable (String: variableName, String: remplacement) -> this`` --> défini la variable, au lieu du replaceAll() traditionnel :) 

``sendPlayer (Player | CommandSender)`` --> envoie l'output au joueur

``getOutput ()`` --> renvoie le résultat dans la langue du serveur

``getOutput (String iso | Player | CommandSender)`` --> renvoie le résultat dans la langue selon l'object

#### Exemples:

            Lang.Prepared prepared = Lang.prepare("exemple.commands.giveall.gived")
                    .variable("amount", "50 000");

            for (Player player : Bukkit.getOnlinePlayers()) {
                // imaginons un code qui $50 000 à player.
                prepared.sendPlayer(player);
            }




# Storage - Configurer, traiter & Stocker
#### Sah quel plaisir

### Préambule:
noms des class concernées:
* ``fr.ulity.core.api.Config`` : cette class gère les fichiers de configuration.
* ``fr.ulity.core.api.Data`` : cette class gère le stockage des données.
* ``fr.ulity.core.api.Temp`` : cette class gère les données temporaires, supprimées après le redémarrage du serveur.

## Constructeurs:
Le premier argument sera toujours le nom du fichier.

Le second argument est optionnel, il indique le répertoire du fichier relatif au répertoire de configuration d'UlityCore.

ℹ️ pour Temp, il n'y a aucun arguments, le fichier est mutualisé


## Exemples:
* ``Config kits = new Config("kits", "kits")`` : location du fichier: /plugins/UlityCore/kits/kits.yml
* ``Data data = new Data("kits", "players")`` : location du fichier: /plugins/UlityCore/kits/players.json

* ``Config kits = new Config( new File() | File )`` : location du fichier: chemin du fichier File
* ``Data data2 = new Data( new File() | File )`` : location du fichier: chemin du fichier File



* ``Temp kits = new Temp()`` : location du fichier: /plugins/UlityCore/data/temps.json

⚠️ ce fichier se reset à chaque redémarrage du serveur

'

'


Pour ces trois class, les méthodes sont les même.
UlityCore utilise la librairie [LightningStorage](https://github.com/JavaFactoryDev/LightningStorage) pour l'api du stockage, data et temp.

Je vous invite donc à vous référer à leur documentation, déjà existante.


# Commandes - Définir, modeler et savourer !
#### Sah quel plaisir

#### depuis UlityCore v2.3



### Toutes les méthodes:
``ind`` représente l'indice (le numéro de l'argument) commençant par 0.

Les statuts disponibles: ``SUCCESS, PLAYER_ONLY, SYNTAX, NOPERM, FAILED, STOP``

 ~

 - Checks, renvoie un booléan et renvoie une erreur si **false**

``requirePlayer()`` --> **true** si la personne est un joueur, sinon **false** accompagné d'une erreur automatique pour la console

``requirePermission( perm )`` --> **true** si l'éxécuteur détient la permission, sinon **false** accompagné d'une erreur automatique

 ~ 

 - check si les arguments existent et s'ils correspondent au type:

``arg.is( ind )`` --> si *args[ ind ]* **existe**

``arg.isNumber ( ind )`` --> si *args[ ind ]* est un **nombre**

``arg.isPlayer ( ind )`` --> si *args[ ind ]* est un **joueur connecté**

 ~

 - Récupère la valeur selon le type demandé, null sans erreur si n'existe pas:

``arg.get( ind )`` --> contenu de *args[ ind ]* sous forme de **texte**

``arg.getLong( ind )`` --> contenu de *args[ ind ]* sous forme de nombre en **Long**

``arg.getPlayer( ind )`` --> contenu de *args[ ind ]* sous forme d'un objet **joueur connecté**

 ~

 - Même effet que les check précedants, mais renvoie une erreur automatiquement si **false**:

``arg.require( ind )`` --> **true** si *args[ ind ]* **existe**, sinon **false** et erreur

``arg.requirePlayer( ind )`` --> **true** si *args[ ind ]* est un **joueur connecté**, sinon **false** et erreur

``arg.requirePlayerNoSelf( ind )`` --> **même effet** mais n'accepte pas que le **joueur** soit **égal** au **sender** ( à lui même )

``arg.requireNumber( ind )`` --> **true** si *args[ ind ]* **existe** et est un **nombre**, sinon **false** erreur

 ~

 - autres méthodes:

``arg.inRange( min, max )`` --> **true** si la longueur est **respectée** ( se base sur #length )

``arg.compare( "texte" , "infinie", "et", "au", "dela" )`` --> **true** si texte est **égal** à une des **valeurs entrées** (String...)

``arg.compare( ind , "infinie", "et", "au", "dela" )`` --> **même effet**, mais en remplaçant texte par un argmument

 ~ 

### Créer une Class de commande:

    public class ExempleCommand extends CommandManager.Assisted {
        public ExempleCommand(CommandMap commandMap, JavaPlugin plugin) {
            super(plugin, "exemple");
    
    
            registerCommand(commandMap);
        }
    
        @Override
        public void exec(CommandSender sender, Command command, String label, String[] args) {
    
            if (arg.isNumber(0)) {
                sender.sendMessage("Nombre: " + args[0]);
            } else {
                sender.sendMessage("le premier argument n'est pas un nombre, peut être qu'il existe pas");
            }
            /* un simple check */


            // @@  @@  @@  @@  @@


            if (arg.requiredNumber(0)) {
                sender.sendMessage("Ouf, un nombre: " + args[0]);
            } else {
                // pas besoin d'envoyer une erreur, l'executeur l'a déjà reçu.
                // la balise else n'a pas vraiment d'utilité dans ce cas-ci, sauf pour des cas complexes.
            }
            /* un check plus poussé renvoyant une erreur si falsy */


            // @@  @@  @@  @@  @@


            if (arg.requiredPlayer(1)) {
                sender.sendMessage("Ouf, nom du joueur: " + arg.getPlayer(1).getName());
            }
            /* un check plus poussé renvoyant une erreur si falsy */


            // @@  @@  @@  @@  @@

    
            /* sinon, on peut définir un status: */
            setStatus(Status.SYNTAX);
            setStatus(Status.NOPERM);
            setStatus(Status.PLAYER_ONLY);
            setStatus(Status.SUCCESS);
        }
    
    }


### Register la commande
Dans la méthode `onLoad()` de votre plugin,
Invoquez une instance de la Class de commande, dans la balise qui vérifie `Api.ok` (souvenez-vous du tuto n°1):

    new ExempleCommand(Api.Bukkit.commandMap, this);

Le premier argument est la commandMap de bukkit, une sorte de liste de commande où l'on va ajouter cette nouvelle commande.

Le second argument est juste l'instance de la class principale de votre plugin, de type Plugin.
