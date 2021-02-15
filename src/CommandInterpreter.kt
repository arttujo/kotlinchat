//Arttu Jokinen
//1606267
//Main CommandInterpreter.
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

class CommandInterpreter(input: InputStream, output: OutputStream):Runnable,CiObserver{
    private val userIn = Scanner(input)
    private val userOut = PrintStream(output)
    private val test =input
    private var done: Boolean = false
    private var userState: Boolean = false
    private var startScore = 1
    var usersName: String =""

    override fun newMessage(chatMessage: ChatMessage){ //Prints message into chat when received from History
        userOut.println(chatMessage)
    }

    override fun run() {
        userOut.println("Welcome to Discord.V2!")
        userOut.println("Make sure to use !help command to see available commands")
        userOut.println("Also assign yourself a username with the !user command!")
        ChatHistory.register(this)
            try {
                while (!done) {
                    val txt: String = userIn.nextLine().toString()
                    if (txt.startsWith("!")) { //! as Prefix.
                        if (txt == "!help") {
                            userOut.println("Command list:")
                            help()
                        } else if (txt == "!history") {              //Prints out the whole history with a simple for loop
                            ChatHistory.history.forEach { userOut.println("From: $it") }

                        } else if (txt.startsWith("!user ")) {
                            val newName: String = txt.removePrefix("!user ") //When assigning new name this checks if the name is on the list
                            if (Users.userList.contains(newName)) {
                                userOut.println("User already exists!")
                            }
                            else if(newName.isBlank()){                    //If blank username is assigned this wont let it happen
                                userOut.println("Username can't be null")
                            }
                            else {
                                Users.removeUser(usersName)  //removes old username if there is one.
                                usersName = txt.removePrefix("!user ")  //New username is assigned by removing !user from users input.
                                Users.newUser(usersName)
                                userOut.println("Assigned username: $usersName")
                                userState = true                    //Sets userState to true so that the user can start chatting
                            }
                        } else if (txt == "!users") {                 //Returns list of users
                            userOut.println(Users.toString())
                        } else if (txt == "!quit") {                  //Breaks the loop and terminates the program
                            userIn.close()
                            println("Connection closed by $usersName")
                            Users.removeUser(usersName)
                            ChatHistory.deregister(CommandInterpreter(input = test, output = userOut))
                            break

                        } else if (txt.startsWith("!")) {           //Blank command checker
                            userOut.println("Didn't recognize command ")
                        }
                    } else if (userState == false) {  // If userState is false the program wont accept message input until username is assigned
                        userOut.println("Assign your username before sending messages!")
                    } else if (txt == "") {
                        userOut.println("Cannot send blank messages")
                    } else {
                        val msg = ChatMessage(txt, usersName)
                        ChatHistory.insert(msg)         //Send message to history.
                        ChatConsole.cConsole(msg)       //Prints message to console
                        TopChatter.scores[usersName] = startScore++
                        TopChatter.top4()               //prints to console the top chatters
                    }
                }
            }catch (e: java.util.NoSuchElementException){  //If the user closes the terminal via X button this makes sure the the user is removed, observer deregister and closes the scanner. Same as !quit command
                userIn.close()
                println("Connection closed by $usersName")
                Users.removeUser(usersName)
                ChatHistory.deregister(this)
            }
        }




    private fun help(){     //Just calls the list of available commands to the user
       val commandList = listOf(
           "help - Displays this list",
           "history - Displays the last 10 messages",
           "users - Display current users",
           "quit - Quits the chat and terminates the program",
           "user - Used to assign yourself to a role",
           "top - Shows the score of 4 top users currently chatting"
       )
        commandList.forEach{userOut.println(it)} //Print the commands 1 by 1
    }
}