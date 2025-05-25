import java.util.Scanner;

public class Menu {

     void menuItems(){

        System.out.println("********************Library Management System*************************"); // Printing the Menu
        System.out.println("1. Availiblity of Books");
        System.out.println("2. Handling Checkouts");
        System.out.println("3. Returns");
        System.out.println("4. Reservations");
    }


    public static void main(String[] args) {


         Menu menu = new Menu();
         menu.menuItems();

         Returns returns = new Returns();

         // Creation of Check Availability Method
        CheckAvailability checkAvailablity = new CheckAvailability();

         // Creation of Handling checkout method
         HandlingCheckouts handlingCheckouts = new HandlingCheckouts();

         Reservations reservations = new Reservations();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Which service you want to select");

        int choice = scanner.nextInt();

        switch(choice){

            case 1:
                //addition of method in switch
               checkAvailablity.checkAvailabilityMenu();
            break;
            case 2:
                // addition of menu method in switch
                handlingCheckouts.handlingCheckoutsMenu();
                break;
            case 3:
                returns.menuOfReturns();
                break;
            case 4:
                reservations.reservationMenu();
                break;
            default:
                System.out.println("You have entered wrong choice");
        }
    }
}
