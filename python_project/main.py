
def menu_choice():
    menuChoice = [
        "1. Ägare (visa alla, lägg till, ändra, ta bort).",
        "2. Anställd (visa alla, lägg till, ändra, ta bort).",
        "3. Skriv ut sammanställning.",
        "0. Avsluta programmet."
    ]  

    for i in range(0,len(menuChoice)):
        print(menuChoice[i])

def get_owner_name(request_name_prompt):
    print(request_name_prompt, end = "")
    restaurant_name = input().strip()
    print(f"Välkommen till {restaurant_name}!")
    return restaurant_name

get_owner_name("Ange restaurangens namn: ")