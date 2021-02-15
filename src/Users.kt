//Arttu Jokinen
//1606267
//Insert and removes username
object Users{

    val userList = HashSet<String>()

    fun newUser(userName:String){       //Adds users to the list and score list
        userList.add(userName)
        TopChatter.scores[userName] = 0
    }

    fun removeUser(userName: String){   //Removes users from the list and score list
        userList.remove(userName)
        TopChatter.scores.remove(userName)
    }

    override fun toString(): String{
        return "Usernames in use: "+this.userList.joinToString()    //Returns usernames that are in use in a formatted string
    }
}

object TopChatter:CiObserver{
    val scores = mutableMapOf<String, Int>()

    fun top4(){
        println("Top 4 users:")
        scores.toList().sortedByDescending {(key,value)->value}.take(4).map{it}.forEach{println(it)}
        //takes the map and converts it into a list and sorts it by descending numbers. Then it takes 4, maps them and prints it with forEach
    }
}