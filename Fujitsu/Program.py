# Name: Ding Ma
# Email: ding.ma@mail.mcgill.ca

# opens the file
file = open('employees.dat', 'r')
all_of_it = file.read()
file.close()
# removes the noise at the beginning
all_of_it = all_of_it[87:-1]
# splits up the string into smaller strings
LineByLine = all_of_it.split(" \n")
cleaning = ''.join(map(str, LineByLine))
# cleans up the space before the comma for John Doe
cleaned = cleaning.replace(', ', ",")
# stores everything in dictionary with the keys being the id and value begin first and last name
listing = dict(item.split(",") for item in cleaned.split("\n"))

start = True
print("Enter \"number\" or \"id\" to sort users by employee numbers\n"
      "Enter \"name\" to sort users by last name\n"
      "Enter \"quit\" to terminate the program\n"
      "Note: this program is cap insensitive\n")
while start:

    user = input("Enter your command\n")
    action = user.lower()

    if action == "number" or action == "id":
        print("Processing by employee number...")
        # sorts using keys
        IdSorted = sorted(listing.items())
        for k, v in IdSorted:
            print(k + "," + v)

    elif action == "name":
        print("Processing by last (family) Name...")
        # sorts with the last name, the x[1] is for the second word
        LastName = sorted(listing.items(), key=lambda x: x[1].split()[1])
        for c, z in LastName:
            print(c + "," + z)

    elif action == "quit":
        print("Program terminated")
        start = False
        exit()
    else:
        print("Invalid input")
