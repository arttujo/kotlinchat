//Arttu Jokinen
//1606267
//Chat history. Stores ChatMessages and handles observers to send out messages when it receives them.
object ChatHistory:HistoryObservable {

    val history = mutableListOf<String>()

    private val observers = mutableListOf<CiObserver>()

    fun insert(chatMessage: ChatMessage) {          //When a chat message is inserted into the history it also notifies all the observers
        history.add(chatMessage.toString())
        notifyObservers(chatMessage)
    }

    override fun register(who: CiObserver) {
        observers.add(who)
    }

    override fun deregister(who: CiObserver) {
        observers.remove(who)
    }

    fun notifyObservers(chatMessage: ChatMessage){      //Notifies all the observers on list
        observers.forEach { it.newMessage(chatMessage)}
    }
}

interface HistoryObservable{
    fun register(who: CiObserver)
    fun deregister(who: CiObserver)
}

interface CiObserver{
    fun newMessage(chatMessage: ChatMessage){
    }
}

object ChatConsole:CiObserver{              // Prints out to the console all the messages people send.
    fun cConsole(chatMessage: ChatMessage){
        println(chatMessage)
    }
}