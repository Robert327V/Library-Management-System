import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class LibraryPatron{
    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
June 19, 2026
This class is just use to hold the information for the patron nodes

 ---------------------------------------------------------------------------------------------------------------------*/
    int id;
    String name;
    String address;
    float debt;
    LibraryPatron next;
    LibraryPatron prev;

    LibraryPatron(int id, String name, String address, float debt){
        this.id = id;
        this.name = name;
        this.address = address;
        this.debt = debt;
        this.next = null;
        this.prev =null;
    }
}

class LibraryQueue{

        /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
June 19, 2026
This class is used to define the queue and keep track of the head and tail

 ---------------------------------------------------------------------------------------------------------------------*/

    LibraryPatron head;
    LibraryPatron tail;
}



class Main {

    /*----------------------------------------------------------------------------------------------------------------------

Robert Vazquez
Software Development I CEN-3024C - 31032
June 19,2026
This is the Main class. The purpose of the Main is to create a list of patron nodes in a queue class and have the ability to read patrons from a file, add them manually,
remove them using an ID#, and print a list of the patrons. I also added a function to search and then edit the information of a patron.

 ---------------------------------------------------------------------------------------------------------------------*/

     //initializeQueue
     //Takes in a queue and sets it to null
    static void initializeQueue(LibraryQueue queue){
        queue.head = null;
        queue.tail = null;
    }
    

    //inputHandler
    //Get user input and pass it to necessary method
    //Takes in a queue and passes it to the methods
    //No return value
    static void inputHandler(LibraryQueue queue){
        
        while(true){
        
            String choice = IO.readln("What would you like to do?(add/remove/print/read/search/exit) ");

            if(choice.equalsIgnoreCase("add")){
        
                manualAdd(queue);

            } else if(choice.equalsIgnoreCase("remove")){
        
                removePatron(queue);
        
            } else if(choice.equalsIgnoreCase("print")){
            
                printAll(queue);
        
            } else if(choice.equalsIgnoreCase("read")){
        
                readAll(queue);
        
            } else if(choice.equalsIgnoreCase("search")){

                searchID(queue);

            } 
            else if(choice.equalsIgnoreCase("exit")){
        
                break;
        
            } else {
        
                IO.println("Invalid choice. Please try again.");
            }
        }
    }

    //addPatron
    //checks if queue is empty and adds patron to the tail. Makes new patron the head if it is. Separate from manualAdd so it can be reused.
    //Takes in a patron node and a queue
    //no return
    static void addPatron(LibraryQueue queue, LibraryPatron patron){

        //Make sure the id is not a duplicate
        if(isDuplicate(queue, patron.id)){
        
            return;
        
        }

        IO.println("\n" + patron.name + " Added to list\n");

        if(queue.head == null){

            queue.head = patron;
            
            queue.tail = patron;
        
        } else {
        
            queue.tail.next = patron;
        
            patron.prev = queue.tail;
        
            queue.tail = patron;
       
        }
    }

    //manualAdd
    //Takes user input and passes it onto addPatron
    //Takes in a queue so that it can pass it to addPatron
    //no return
    static void manualAdd(LibraryQueue queue){

        boolean question = false;

        String input;

        while(true) {

            if(question) {

                //Checks if user would like to continue to add patrons so that they can add multiple without returning to menu

                input = IO.readln("Continue adding patron to list manually? (yes/no)\n");

                if (input.equalsIgnoreCase("no")) {

                    printAll(queue);

                    IO.println("Exiting to menu");

                    return;
                }

                if (input.equalsIgnoreCase("yes") == false) {

                    IO.println("Invalid input, please enter yes or no.");

                    return;
                }

            }
            question = true;

            int id;

            input = IO.readln("Enter patron ID: ");

            //Error checking for duplicate ids or wrong variable type
            if (isVariable(input, "int") == false) {

                continue;
            }
            if(isDuplicate(queue, Integer.parseInt(input)) == true){
                continue;
            }

            id = Integer.parseInt(input);

            String name = IO.readln("Enter patron name: ");

            String address = IO.readln("Enter patron address: ");

            float debt;

            input = IO.readln("Enter patron debt: ");

            if (isVariable(input, "float") == false) {

                continue;

            }

            debt = Float.parseFloat(input);

            addPatron(queue, new LibraryPatron(id, name, address, debt));
        }

    }

    //printAll
    //Moves through the queue linked list and prints all the data of each LibraryPatron node as it passes
    //takes in a queue
    //no return
    static void printAll(LibraryQueue queue){

        //Checks to make sure queue is not empty
        if(queue.head == null){

            IO.println("Patrons list is empty");

            return;
        }

        IO.println("\nPrinting patrons\n--------------------------------------------------------------------------------------------------------------\n");

        LibraryPatron cursor = queue.head;
        
        while(cursor!=null){
        
            IO.println("Patron ID: " + cursor.id);
        
            IO.println("Name: " + cursor.name);
        
            IO.println("Address: " + cursor.address);
        
            IO.println("Debt: " + cursor.debt);
        
            IO.println();
        
            cursor = cursor.next;
        
        }

        IO.println("---------------------------------------------------------------------------------------------------------------\n");
    }
    


    //removePatron
    //Move through linked list and once the ID is found that matches user input, remove patron and stitch the list back together
    //Takes queue argument so that it can find the patron once id is taken from user
    //no return
    static void removePatron(LibraryQueue queue){
           if(queue.head == null){

        IO.println("Patron list is empty");
        
        return;
        
    }
        int id;

        while(true){
        
            String input = IO.readln("Enter patron ID to remove(Type exit to cancel): ");

            if(input.equalsIgnoreCase("exit")){

                IO.println("\nReturning to menu\n");

                return;

            }

            if(isVariable(input, "int")){
            
                id = Integer.parseInt(input);

                break;

            }
        }

        LibraryPatron cursor = queue.head;
        
        while(cursor.id != id){

            if(cursor.next == null){
            
                IO.println("\nCould not find patron with ID " + id);

                removePatron(queue);

                return;
            
            }
            
            cursor = cursor.next;
        
        }
        
        if(cursor == queue.head){
        
            queue.head = cursor.next;
        
        } else if(cursor == queue.tail){

            queue.tail = cursor.prev;

            queue.tail.next = null;
        } else {
            
            cursor.prev.next = cursor.next;
            
            cursor.next.prev = cursor.prev;
            
        }
        IO.println("\nRemoving " + cursor.name + " From list\n");
        printAll(queue);
        deInitializePatron(cursor);

    }

    
    //readAll
    //Reads file line by line and passes each parsed line variables to addPatron
    //Takes queue argument
    //No return
    static void readAll(LibraryQueue queue){

        String file = IO.readln("Please enter the file: ");
        
        try {
            
            List<String> entries = Files.readAllLines(Paths.get(file));

            //for each line in the file, split the data and pass it to addPatron, counter used to inform user which line failed to import

            int counter = 0;

            for(String line : entries){

                counter++;

                String[] data = line.split("-");


                //Error check to make sure there are four pieces of data
                 if(data.length < 4){

                    IO.println("\nCould not add patron at line " + counter + " because information is missing");
                    
                    continue;
                }
            
                int id = Integer.parseInt(data[0]);
            
                String name = data[1];
            
                String address = data[2];
            
                float debt = Float.parseFloat(data[3]);

                addPatron(queue, new LibraryPatron(id, name, address, debt));

        
            }
        
        } catch (IOException e) {

            IO.println("Could not read file " + file + ": " + e.getMessage());

            return;
        
        }
        printAll(queue);
    }



    //deInitializePatron
    //Sets patron variables to null for data safety
    //uses LibraryPatron Argument
    //no return
    static void deInitializePatron(LibraryPatron patron){
        patron.id = 0;
        patron.name = null;
        patron.address = null;
        patron.debt = 0;
        patron.next = null;
        patron.prev = null;
    }



    //isDuplicate
    //Checks the queue for duplicate IDs to avoid data integrity issues.
    //Uses id and queue arguments
    //returns true if duplicate is already in list, false if not
    static boolean isDuplicate(LibraryQueue queue, int id){

     
        LibraryPatron cursor = queue.head;
        
        while(cursor != null){
        
            if(cursor.id == id){

                IO.println("Could not add " + cursor.name + " to ID " + id + " because already exists");

                return true;

            }
        
            cursor = cursor.next;
        }
        return false;
    }

    //searchID
    //Moves through queue to find patron with matching ID to user input. Prints the information then gives option to edit.
    //Takes LibraryQueue argument
    //no return
    static void searchID(LibraryQueue queue){

        if(queue.head == null){

            IO.println("Patron list is empty");
            
            return;
        
        }

        int id;
        
        LibraryPatron cursor = queue.head;
        
        while(true){

            String input = IO.readln("Enter patron ID to search for(Type exit to cancel): ");

             if(input.equalsIgnoreCase("exit")){
                    
                IO.println("\nReturning to menu\n");
                
                return;
                }


            if(isVariable(input, "int") == false){

                continue;
            
            }
            
            id = Integer.parseInt(input);

            break;
        
        }

        while(cursor.id != id){
        
            cursor = cursor.next;
        
            if(cursor == null){
            
                IO.println("Could not find patron with ID " + id);
            
                return;
            
            }
        }
        
        IO.println("\nPatron found\n ID: " + cursor.id + "\nName: " + cursor.name + "\nAddress: " + cursor.address + "\nDebt: " + cursor.debt + "\n");
        

        //offer choice to edit


        while(true){
        
             String input = IO.readln("Would you like to edit this patron's information? (yes/no) ");

             if(input.equalsIgnoreCase("yes")){
                
                editPatron(queue, cursor);
                
                return;
            
            } else if(input.equalsIgnoreCase("no")){
                
                return;
            
            } else {
                
                IO.println("Invalid input. Please enter yes or no.");
            
            }
        }
    }

    //editPatron
    //Allows the user to edit any data found within the patron node
    //Takes LibraryQueue and LibraryPatron arguments
    //no return
    static void editPatron(LibraryQueue queue, LibraryPatron cursor){


        while(true){
            
            String input = IO.readln("What would you like to edit? (id/name/address/debt/exit) ");

            //Changes variable the user desires to edit. Error checks for duplicate id and invalid ints and floats
            if(input.equalsIgnoreCase("id")){

                input = IO.readln("Enter new ID: ");
        
                if(isVariable(input, "int") == false){
            
                    continue;
                }

                if(isDuplicate(queue, Integer.parseInt(input)) == false){

                    IO.println("Changing " + cursor.name + "'s " + "id from " + cursor.id + " to " + input);

                    cursor.id = Integer.parseInt(input);
            
                }

            } else if(input.equalsIgnoreCase("name")){
            
                String name = IO.readln("Enter new name: ");

                IO.println("Changing " + cursor.name + "'s " + "name from " + cursor.name + " to " + name);

                cursor.name = name;
            
            } else if(input.equalsIgnoreCase("address")){
            
                String address = IO.readln("Enter new address: ");

                IO.println("Changing " + cursor.name + "'s " + "address from " + cursor.address + " to " + address);
            
                cursor.address = address;
            
            } else if(input.equalsIgnoreCase("debt")){
            
                input = IO.readln("Enter new debt: ");
            
                if(isVariable(input, "float") == false){
            
                    continue;
                
                }
                IO.println("Changing " + cursor.name + "'s " + "debt from " + cursor.debt + " to " + input);
                cursor.debt = Float.parseFloat(input);

            } else if (input.equalsIgnoreCase("exit")){

                IO.println("\nReturning to menu\n");

                return;

            } else {

                IO.println("Invalid input. Please enter id, name, address, debt, or exit.");
                continue;
            }
            
        }
    }


    //isVariable
    //Ensures that the given String can be parsed to int or float. Used to error check previous methods in a cleaner fashion.
    //Takes in String for input and String for the variable type the program is checking for (int or float)
    //returns true if it can be parsed to that variable type and false if not
    static boolean isVariable(String input, String variableType){

        if(variableType.equalsIgnoreCase("int")){
            try {
                
                Integer.parseInt(input);
                
                return true;
        
            } catch(NumberFormatException e){
        
                IO.println("Invalid input. Please enter an integer.");
        
                return false;

            }

        } else if(variableType.equalsIgnoreCase("float")){

            try {
                
                Float.parseFloat(input);
                
                return true;
        
            } catch(NumberFormatException e){
            
                IO.println("Invalid input. Please enter a float.");
            
                return false;
            }
        }
    return false;
    }





    //main
    //used to initialize library queue and begin input handler
    //no arguments
    //no return
    public static void main() {

        LibraryQueue queue = new LibraryQueue();
        
        initializeQueue(queue);

        readAll(queue);

        inputHandler(queue);


}

}