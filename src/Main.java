import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] menuChoices = {
                "1. Ägare (visa alla, lägg till, ändra, ta bort).",
                "2. Anställd (visa alla, lägg till, ändra, ta bort).",
                "3. Skriv ut sammanställning.",
                "0. Avsluta programmet."
        };

        String restaurantName;

        do {
            System.out.print("Ange restaurangens namn > ");
            restaurantName = scanner.nextLine().trim();
            if (restaurantName.length() < 11) {
                System.out.println("För kort namn. Prova igen... ");
            }
        } while (restaurantName.length() < 11);

        int totalOwners;

        do {
            System.out.print("Antal ägare? ");
            totalOwners = scanner.nextInt();
            scanner.nextLine();
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
        } else {
            for (i = 0; i < ownersArray.length - 1; i++) {
                do {
                    System.out.print("Ange ägare " + (i + 1) + " ägarandel > ");
                    ownership = scanner.nextInt();
                    scanner.nextLine();
                    if (ownership <= 0)
                        System.out.println("En ägare måste äga något... ");
                } while (ownership <= 0);
                ownersArray[i] = ownership;
                totalOwnerPercentage += ownersArray[i];
            }
            ownersArray[i] = 100 - totalOwnerPercentage;
            System.out.println("Ägare " + ownersArray.length +
                    " blir tilldelad resterande " +
                    ownersArray[i] + "% ägarandel.");
        }

        int[] employeesArray = new int[0];
        boolean exitMainProgram = false;
        String menuChoice;

        System.out.println("Valkommen till " + restaurantName + "! \n");

        while (!exitMainProgram) {
            System.out.println("Välj ett av dessa meny-alternativ:");

            for (String choice : menuChoices) {
                System.out.println(choice);
            }

            System.out.print("Ange siffran för menyval > ");
            menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1" -> ownersArray = subChoice(ownersArray, "ägare");
                case "2" -> employeesArray = subChoice(employeesArray, "anställd");
                case "3" -> printSummary(ownersArray, employeesArray);
                case "0" -> { System.out.println("Programmet avslutas... "); exitMainProgram = true; }
                default -> System.out.println("Ogiltigt val. Försök igen... ");
            }
        }
    }

    public static int[] subChoice(int[] arrayParam, String elementParam) {
        boolean exitSubMenu = false;
        int menuSubChoice;

        while (!exitSubMenu) {

            System.out.println("Vad vill du göra med " + elementParam + "?\n" +
                    "1. Visa alla " + elementParam + ".\n" +
                    "2. Lägg till en ny " + elementParam + ".\n" +
                    "3. Ändra en " + elementParam + ".\n" +
                    "4. Ta bort en " + elementParam + ".\n" +
                    "0. Gå tillbaka till huvudmenyn"
            );

            System.out.print("Ange siffran för menyval > ");
            menuSubChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuSubChoice) {
                case 1 -> printAll(arrayParam, elementParam);
                case 2 -> arrayParam = addNew(arrayParam, elementParam);
                case 3 -> arrayParam = change(arrayParam, elementParam);
                case 4 -> arrayParam = remove(arrayParam, elementParam);
                case 0 -> exitSubMenu = true;
                default -> System.out.println("Ogiltigt val. Försök igen... ");
            }
        }
        return arrayParam;
    }

    private static void printSummary(int[] ownersArray, int[] employeesArray) {
        int totalOwnership = 0;

        System.out.println("----------------------------------------------------");

        for (int i = 0; i < ownersArray.length; i++) {
            System.out.println("Ägare " + (i + 1) + ": " + ownersArray[i] + "%");
            totalOwnership += ownersArray[i];
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Total ägande: " + totalOwnership + "%");
        System.out.println("""
                ----------------------------------------------------
                ----------------------------------------------------""");

        int totalHourSalaries = 0;
        for (int i = 0; i < employeesArray.length; i++) {
            System.out.println("Anställd " + (i + 1) + ": " + employeesArray[i] + "kr/h");
            totalHourSalaries += employeesArray[i];
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Totala timkostnad anställda " + totalHourSalaries + "kr/h");
        System.out.println("----------------------------------------------------");
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
        if (elementParam.equals("anställd")) {
            int salary = 0;

            do {
                System.out.print("Ange den anställdes timlön > ");
                salary = scanner.nextInt();
                scanner.nextLine();
                if (salary <= 0) {
                    System.out.println("Timlönen måste vara minst 0kr per timme... ");
                }
            } while (salary <= 0);

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
                ownership = scanner.nextInt();
                scanner.nextLine();
                if (ownership > 0 && ownership < 100) {
                    break;
                }
                System.out.println("Felaktig ägarandel. Det måste vara mer än 0% och mindre än 100%... ");
            } while (ownership <= 0 || ownership > 100);

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
        System.out.println("Vilken " + elementParam + " vill du ändra på?");
        printAll(arrayParam, elementParam);
        int inputNumber = 0;
        boolean giveAway = true;

        do {
            System.out.print("Ange siffran på den du vill ändra på > ");
            inputNumber = scanner.nextInt();
            scanner.nextLine();
            inputNumber--;
            if (inputNumber >= arrayParam.length || inputNumber < 0) {
                System.out.println("Ange en giltig siffra... ");
            }
        } while (inputNumber >= arrayParam.length || inputNumber < 0);

        if (elementParam.equals("ägare")) {
            int ownership = 0;

            do {
                System.out.print("Ange ägarens nya ägarandel > ");
                ownership = scanner.nextInt();
                scanner.nextLine();
                if (ownership < 0 || ownership > 100) {
                    System.out.println("Felaktig ägarandel. Det måste vara mer än 0% men mindre än 100%... ");
                }
            } while (ownership < 0 || ownership > 100);

            if (ownership > arrayParam[inputNumber]) {
                ownership -= arrayParam[inputNumber];
                arrayParam[inputNumber] += ownership;
                giveAway = false;
            } else {
                ownership = (arrayParam[inputNumber] - ownership);
                arrayParam[inputNumber] -= ownership;
                giveAway = true;
            }

            int[] tempArray = new int[arrayParam.length - 1];
            int i = 0;
            int j = 0;

            do {
                if (i != inputNumber){
                    tempArray[j] = arrayParam[i];
                    j++;
                }
                i++;
            } while (i < arrayParam.length);

            tempArray = correctOwnership(tempArray, ownership, giveAway);
            i = 0;
            j = 0;

            while (i < arrayParam.length) {
                if (i != inputNumber) {
                    arrayParam[i] = tempArray[j];
                    j++;
                } else {
                    arrayParam[i] = tempArray[j];
                }
                i++;
            }

        } else {
            int salary = 0;

            do {
                System.out.print("Ange den anställdes nya timlön > ");
                salary = scanner.nextInt();
                scanner.nextLine();
                if (salary < 0) {
                    System.out.println("Felaktig timlön. Det måste vara mer än 0kr/h...");
                }
            } while (salary < 0);

            arrayParam[inputNumber] = salary;
        }
        return arrayParam;
    }

    private static int[] remove(int[] arrayParam, String elementParam) {
        if (arrayParam.length == 1 && elementParam.equals("ägare")) {
            System.out.println("Du kan inte ta bort den enda ägaren i företaget...");
        } else {
            System.out.println("Vilken " + elementParam + " vill du ta bort?");
            printAll(arrayParam, elementParam);
            int inputNumber = -1;

            do {
                System.out.print("Ange siffran på den " + elementParam + " du vill ta bort > ");
                inputNumber = scanner.nextInt();
                scanner.nextLine();
                inputNumber--;
                if (inputNumber < arrayParam.length && inputNumber >= 0) {
                    break;
                }
                System.out.println("Ange en giltig siffra... ");
            } while (inputNumber >= arrayParam.length && inputNumber < 0);
            System.out.println(arrayParam.length + "KOLLA");

            int[] newArray = new int[arrayParam.length - 1];
            for (int i = 0; i < arrayParam.length - 1; i++) {
                if (i != inputNumber && i < inputNumber) {
                    newArray[i] = arrayParam[i];
                }
                newArray[i] = arrayParam[ i + 1 ];
            }

            if (elementParam.equals("ägare")) {
                return correctOwnership(newArray, arrayParam[inputNumber], true);
            } else {
                return newArray;
            }
        }
        return arrayParam;
    }

    private static int[] correctOwnership(int[] arrayParam, int wantedOwnership, boolean giveAwayOwnership) {
        String giveOrTake = giveAwayOwnership ? "fördelas ut" : "tas fram";
        String infoGiveOrTake = giveAwayOwnership ? "ge till" : "ta ifrån";

        do {
            System.out.println("Det är " + wantedOwnership + " procentenheter som behöver " + giveOrTake + ".");
            if (giveAwayOwnership) {
                System.out.println("Vilken ägare vill du ge ägarandelar till?");
            } else {
                System.out.println("Vilken ägare vill du ta ägarandelar av?");
            }
            int i;

            for (i = 0; i < arrayParam.length; i++) {
                System.out.println("Ägare " + (i + 1) + ": " + arrayParam[i] + "%.");
            }

            int ownerIndex;

            while (true) {
                do {
                    System.out.print("Ange siffran för vilken ägare du vill " + infoGiveOrTake + " > ");
                    ownerIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (arrayParam[ownerIndex - 1] == 1 && !giveAwayOwnership) {
                        System.out.println("Ägare " + ownerIndex + " har bara 1% kvar. Du kan inte ta bort det sista... ");
                    }
                } while (arrayParam[ownerIndex - 1] == 1 && !giveAwayOwnership);

                if (ownerIndex > 0 && ownerIndex <= arrayParam.length) {
                    break;
                }
                System.out.println("Felaktigt val. Prova igen ... ");
            }

            int correctOwnership;

            while (true) {
                do {
                    System.out.print("Hur många procentenheter vill du " + giveOrTake + " ägare " + ownerIndex + "? > ");
                    correctOwnership = scanner.nextInt();
                    scanner.nextLine();
                    if (correctOwnership > wantedOwnership) {
                        System.out.println("Du kan inte ta mer än " + wantedOwnership + "...");
                    }
                } while (correctOwnership > wantedOwnership);

                if (!(i > 0 && i <= wantedOwnership && correctOwnership < arrayParam[ownerIndex - 1])) {
                    System.out.println("Du kan endast ta 1-" + wantedOwnership + " procentenheter från ägaren...");
                } else {
                    ownerIndex--;
                    break;
                }
            }

            if (giveAwayOwnership) {
                arrayParam[ownerIndex] += correctOwnership;
            } else {
                arrayParam[ownerIndex] -= correctOwnership;
            }

            wantedOwnership -= correctOwnership;

        } while (wantedOwnership != 0);
        return arrayParam;
    }
}