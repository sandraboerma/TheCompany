import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner mainInput = new Scanner(System.in);

        String[] menuChoices = {
                "1. Ägare (visa alla, lägg till, ändra, ta bort).",
                "2. Anställd (visa alla, lägg till, ändra, ta bort).",
                "3. Skriv ut sammanställning.",
                "0. Avsluta programmet."
        };

        String restaurantName;
        do {
            System.out.print("Ange restaurangens namn > ");
            restaurantName = mainInput.nextLine();
            if (restaurantName.length() < 10) {
                System.out.println("För kort namn. Prova igen... ");
            }
        } while (restaurantName.length() < 10);

        int totalOwners;
        do {
            System.out.print("Antal ägare? ");
            totalOwners = mainInput.nextInt();
            if (totalOwners <= 0) {
                System.out.println("Det måste finnas minst en ägare... ");
            }
        } while (totalOwners <= 0);

        int[] ownersArray = new int[totalOwners];
        int i;
        int totalOwnerPercentage = 0;
        int ownership;

        if (ownersArray.length == 1) {
            ownersArray[0] = 100;
            System.out.println("Owner is the " + ownersArray.length + " and only och äger " + ownersArray[0] + "%."); // code check
        } else {
            for (i = 0; i < ownersArray.length - 1; i++) {
                do {
                    System.out.print("Ange ägare " + (i + 1) + " ägarandel > ");
                    ownership = mainInput.nextInt();
                    if (ownership <= 0)
                        System.out.println("En ägare måste äga något... ");
                } while (ownership <= 0);
                ownersArray[i] = ownership;
                totalOwnerPercentage += ownersArray[i];
                System.out.println("Current stored index: " + i); //code check
                System.out.println("Owner " + (i + 1) + " owns " + ownership + "% andel."); //code check
            }
            ownersArray[i] = 100 - totalOwnerPercentage;
            System.out.println("Owner " + ownersArray.length + " owns " + ownersArray[i] + "% andel."); // code check
        }

        int[] employeesArray = new int[0];
        boolean exitMainProgram = false;
        String menuChoice;
        mainInput.nextLine();

        System.out.println("Valkommen till " + restaurantName + "! \n");

        while (!exitMainProgram) {
            System.out.println("Välj ett av dessa meny-alternativ:");

            for (String choice : menuChoices) {
                System.out.println(choice);
            }

            System.out.print("Ange siffran för menyval > ");
            menuChoice = mainInput.nextLine();

            switch (menuChoice) {
                case "1":
                    System.out.println(menuChoices[0]);
                    ownersArray = subChoice(ownersArray, "ägare");
                    break;
                case "2":
                    System.out.println(menuChoices[1]);
                    employeesArray = subChoice(employeesArray, "anställd");
                    break;
                case "3":
                    System.out.println(menuChoices[2]);
                    printSummary(ownersArray, employeesArray);
                    break;
                case "0":
                    System.out.println("Programmet avslutas... ");
                    exitMainProgram = true;
                    break;
                default:
                    System.out.println("Ogiltigt val. Försök igen... ");
                    break;
            }
        }
    }

    public static int[] subChoice(int[] arrayParam, String elementParam) {
        Scanner subChoicInput = new Scanner(System.in);
        boolean exitSubMenu = false;
        String menuSubChoice;

        while (!exitSubMenu) {

            System.out.println("Vad vill du göra med " + elementParam + "?\n" +
                    "1. Visa alla " + elementParam + ".\n" +
                    "2. Lägg till en ny " + elementParam + ".\n" +
                    "3. Ändra en " + elementParam + ".\n" +
                    "4. Ta bort en " + elementParam + ".\n" +
                    "0. Gå tillbaka till huvudmenyn"
            );

            System.out.print("Ange siffran för menyval > ");
            menuSubChoice = subChoicInput.nextLine();

            switch (menuSubChoice) {
                case "1":
                    printAll(arrayParam, elementParam);
                    break;
                case "2":
                    arrayParam = addNew(arrayParam, elementParam);
                    break;
                case "3":
                    change(arrayParam, elementParam);
                    break;
                case "4":
                    remove(arrayParam, elementParam); // check this more later.
                    break;
                case "0":
                    exitSubMenu = true;
                    break;
                default:
                    System.out.println("Ogiltigt val. Försök igen... ");
            }
        }
        return arrayParam;
    }

    private static void printSummary(int[] ownersArray, int[] employeesArray) {
        int totalOwnership = 0;
        for (int i = 0; i < ownersArray.length; i++) {
            System.out.println("Ägare " + (i + 1) + ": " + ownersArray[i] + "%");
            totalOwnership += ownersArray[i];
        }

        System.out.println("Total ägande: " + totalOwnership + "%");

        int totalHourSalaries = 0;
        for (int i = 0; i < employeesArray.length; i++) {
            System.out.println("Anställd " + (i + 1) + ": " + employeesArray[i] + "kr/h");
            totalHourSalaries += employeesArray[i];
        }

        System.out.println("Totala timkostnad anställda" + totalHourSalaries + "kr/h");
    }

    private static void printAll(int[] arrayParam, String elementParam) {
        String printPrefix;

        if (arrayParam.length == 0) {
            System.out.println("Det finns inga " + elementParam + " inlagda... \n");
        } else {
            for (int i = 0; i < arrayParam.length; i++) {
                if (elementParam.equals("ägare")) {
                    printPrefix = "%";
                } else {
                    printPrefix = "kr/h";
                }
                System.out.println(elementParam + " " + (i + 1) + ": " + arrayParam[i] + printPrefix + ".");
            }
        }
    }

    private static int[] addNew(int[] arrayParam, String elementParam) {
        Scanner addNewInput = new Scanner(System.in);

        if (elementParam.equals("anställd")) {
            int salary = 0;

            do {
                System.out.print("Ange den anställdes timlön > ");
                salary = addNewInput.nextInt();
                if (salary <= 0) {
                    System.out.println("Timlönen måste vara minst 0kr per timme... ");
                }
            } while (salary <= 0);

            System.out.println("Lönen är " + salary);

            System.out.println("arrayParam har " + arrayParam.length + " antal index");

            if (arrayParam.length == 0) {
                int[] insertEmployeeSalary = new int[1];
                insertEmployeeSalary[0] = salary;
                arrayParam = insertEmployeeSalary;
            } else {
                int[] modifiedEmployeeSalary = new int[arrayParam.length + 1];
                for (int i = 0; i < arrayParam.length; i++) {
                    modifiedEmployeeSalary[i] = arrayParam[i];
                }
                modifiedEmployeeSalary[arrayParam.length] = salary;
                arrayParam = modifiedEmployeeSalary;
            }
        } else {
            int ownership = 0;

            do {
                System.out.print("Ange ägarens ägarandel > ");
                ownership = addNewInput.nextInt();
                if (ownership > 0 && ownership < 100) {
                    break;
                }
                System.out.println("Felaktig ägarandel. Det måste vara mer än 0% och mindre än 100%... ");
            } while (ownership == 0);

            arrayParam = correctOwnership(arrayParam, ownership, false);

            int[] anotherTempArray = new int[arrayParam.length + 1];
            for (int i = 0; i < arrayParam.length; i++) {
                anotherTempArray[i] = arrayParam[i];
            }
            anotherTempArray[arrayParam.length] = ownership;
            arrayParam = anotherTempArray;
        }
        return arrayParam;
    }

    private static int[] change(int[] arrayParam, String elementParam) {
        Scanner changeInput = new Scanner(System.in);
        System.out.println("Vilken " + elementParam + " vill du ändra på?");
        printAll(arrayParam, elementParam);
        int inputNumber = 0;
        boolean giveAway = true;

        //InputNumber < arrayParam.length e.g. InputNumber must be smaller than array index
        //InputNumber >= 0 e.g. InputNumber must be bigger or equal to zero

        do {
            System.out.println("Ange siffran på den du vill ändra på > ");
            inputNumber = changeInput.nextInt();
            inputNumber--;

            if (inputNumber >= arrayParam.length || inputNumber < 0) {
                System.out.println("Ange en giltig siffra... ");
            }

        } while (arrayParam.length >= inputNumber || inputNumber < 0);

        if (elementParam.equals("ägare")) {
            int ownership = 0;

            do {
                System.out.println("Ange ägarens nya ägarandel > ");
                ownership = changeInput.nextInt();
                if (ownership < 0 || ownership > 100){
                    System.out.println("Felaktig ägarandel. Det måste vara mer än 0% men mindre än 100%... ");
                }
            } while (ownership < 0 || ownership > 100);

            if (ownership > arrayParam[inputNumber]){
                ownership -= arrayParam[inputNumber];
                arrayParam[inputcdNumber] += ownership;
                giveAway = false;
            } else {
                ownership = arrayParam[inputNumber];
                arrayParam[inputNumber] -= ownership;
                giveAway = true;
            }

        } else {
            int salary = 0;

            do {
                System.out.println("Ange den anställdes nya timlön > ");
                salary = changeInput.nextInt();
                if (salary < 0) {
                    System.out.println("Felaktig timlön. Det måste vara mer än 0kr/h...");
                }
            } while (salary < 0);

            arrayParam[inputNumber] = salary;

        }
        return arrayParam;

    }

    private static int[] correctOwnership(int[] arrayParam, int wantedOwnership, boolean giveAwayOwnership) {
        Scanner updateInput = new Scanner(System.in);
        String giveOrTake = giveAwayOwnership ? "fördelas ut" : "tas fram";
        String infoGiveOrTake = giveAwayOwnership ? "ge till" : "ta ifrån";
        boolean updateCompleted = false;

        while (!(wantedOwnership != 0)) {
            System.out.println("Det är " + wantedOwnership + " procentenheter som behöver " + giveOrTake + ".");

            if (giveAwayOwnership == true) {
                System.out.println("Vilken ägare vill du ge ägarandelar till?");
            } else {
                System.out.println("Vilken ägare vill du ta ägarandelar av?");
            }

            for (int i = 0; i < arrayParam.length; i++) {
                System.out.println("Ägare " + (i + 1) + arrayParam[i] + "%.");
            }

            int ownerIndex = updateInput.nextInt();


                do {
                    System.out.println("Ange siffran för vilken ägare du vill " + infoGiveOrTake + " > ");
                    ownerIndex = updateInput.nextInt();

                    if (arrayParam[ownerIndex - 1] == 1 && !giveAwayOwnership) {
                        System.out.println("Ägare " + ownerIndex + " har bara 1% kvar. Du kan inte ta bort det sista... ");
                    }
                } while (arrayParam[ownerIndex - 1] == 1 && !giveAwayOwnership);

                if (ownerIndex > 0 && ownerIndex <= arrayParam.length) {
                    System.out.println("Felaktigt val. Prova igen ... ");
                }


            int correctOwnership = 0;

            do {
                System.out.println("Hur många procentenheter vill du " + giveOrTake + "ägare " + ownerIndex + "? > ");
                correctOwnership = updateInput.nextInt();
                if (correctOwnership > wantedOwnership) {
                    System.out.println("Du kan inte ta mer än " + wantedOwnership + "...");
                }
            } while (correctOwnership > wantedOwnership);

            //if (!(i>0) && i <= wantedOwnership && correctOwnership < arrayParam[ownerIndex -1]) <<<<< Is this the error?
            if (!(correctOwnership < arrayParam[ownerIndex - 1])) {
                System.out.println("Du kan endast ta 1-" + wantedOwnership + " procentenheter från ägaren...");
            }

            ownerIndex--;

            if (giveAwayOwnership) {
                arrayParam[ownerIndex] += correctOwnership;
            } else {
                arrayParam[ownerIndex] -= correctOwnership;
            }

            wantedOwnership -= correctOwnership;

            if (wantedOwnership == 0) {
                break;
            }
        }
        return arrayParam;
    }

    private static void remove(int[] arrayParam, String elementParam) {
        System.out.println("remove method is not fully implemented yet.");
    }


}