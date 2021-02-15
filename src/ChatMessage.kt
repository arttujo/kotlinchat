//Arttu Jokinen
//1606267
//Creates a formatted string for all the sent chat messages.
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class ChatMessage(private val text: String,private val sender:String){
    private val formatted = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    private val time = LocalDateTime.now()
    private val formattedTime = time.format(formatted)
    override fun toString(): String {
        return sender + "@" + formattedTime + " said: \n"+ text
    }
}