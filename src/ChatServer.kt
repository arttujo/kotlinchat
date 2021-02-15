//Arttu Jokinen
//1606267
//Creates a server socket and puts every connection to its own thread
import java.net.ServerSocket
class ChatServer{
       fun serve(){
        val ss = ServerSocket(54372)
        println("Our port is: ${ss.localPort}")
               while (true) {
                   println("Step 1")
                   val listen = ss.accept()
                   println("Step 2")
                   val t = Thread(CommandInterpreter(input = listen.getInputStream(), output = listen.getOutputStream())) //Threads a CI and Starts it in the next line
                   t.start()
               }
        }
}