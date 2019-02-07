#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXLEN 100




/*
 * note to self
 * search in the btree if the name/ id exists
 *add error name/id not found
 * fix ln/li formating
 */

//iniates the data structure
struct StudentRecord {
	char first [MAXLEN];
	char last [MAXLEN];
	int ID;
	int marks;
	struct StudentRecord *left;
	struct StudentRecord *right;
};


//these are athe nodes of the binary tree
void addNode(struct StudentRecord** root,struct StudentRecord* input, int type)
{
	//if the type is other than 1, means no data
	//if the root is null
	if((*root) == NULL){
		// create a root with input value, malloc dynamically allocates memory
		(*root) = (struct StudentRecord*)malloc(sizeof(struct StudentRecord));
		//sets the root to be the id
		(*root)->ID = input->ID;
		//sets the root to be mark
		(*root)->marks = input -> marks;
		//creating a second btre
		strcpy((*root)->first, input->first);
		strcpy((*root)->last, input->last);
		//makes sure there is nothing in the tree
		(*root)->left=NULL;
		(*root)->right=NULL;
	}
	else{
		//if there is data
		if(type ==1){
			// inserts right/left of the branch depending on their value
			//left for smaller, right for bigger than the root
			if(input->ID < (**root).ID) // root -> val
			{

				//adds node to the left
				//the root points to the address of the left node, it it is not done this way it will explode the ram
				addNode(&((*root)->left), input, type);
			}
			else{
				//adds node to the right
				addNode(&((*root)->right), input, type);
			}
		}
		else
		{
			//compares if t he string is smaller than 0
			//if it is, insert ot the left
			//else, insert to right
			if(strcmp(input->last,(*root)->last)<0){
				addNode(&((*root)->left), input, type);
			}
			else{
				addNode(&((*root)->right), input, type);
			}
		}

	}
}


//funtion that traverse tree for LN/LI functions
void traverseID(struct StudentRecord* root){

	//we are doing it in form of left, root, right [in order]
	//if there is something left, go left
	if(root->left != NULL){
		traverseID(root->left);
	}

	char temp[0];
	//this concatenates the first name and the last name together in order to facilate the formating
	strcat(strcat(strcpy(temp, root->first), " "), root->last);

	//prints out the string "temp" which is concatenated with first and last name
	printf(" %-25s",temp);
	//prints id
	printf("%-13d",root->ID);
	//prints marks
	printf("%d\n",root->marks);

	//if there is something right, go right
	if(root->right != NULL){

		traverseID(root->right);
	}
	//this function is a recursive function and it will only stop when the entire tree is printed out
}


//function that traverse tree for the last name searching funciton
//the value found is to detect if the last name can be found in the tree

void searchTreeName(struct StudentRecord* root, char name[], int *found){
	//if there is something left, go left
	if(root->left != NULL){
		searchTreeName(root->left, name, found);
	}

	//compares the last name entered by the user to the last name in the root.
	//if they are equal, it will return 0 and it will print out the information
	//if they are not equal, it will return a value other than 0, thus continuing the search
	if(strcmp(root->last,name)==0){
		//prints out the name, id, and mark
		printf("\n");
		printf("Student Name:  ");
		printf("%s %-23s\n",root->first,root->last);
		printf("Student ID:%8d\n",root->ID);
		printf("Total Grade:%5d\n",root->marks);
		//if the name is found, it yield 1
		//if the name is not found, it will stay at 0, therefore telling the user that the name does not exist
		*found=1;

	}
	//if there is something right, go right
	if(root->right != NULL){

		searchTreeName(root->right,name, found);
	}

	//this function is a recursive function and it will only stop when the entire tree is printed out
}



//function that traverse tree for the student ID searching funciton
//the value find is to detect if the ID can be found in the tree

void searchTreeID(struct StudentRecord* root, int id, int *find){

	//if there is something left, go left
	if(root->left != NULL){
		searchTreeID(root->left, id, find);
	}
	if(root->ID==id){

		// print the middle
		printf("\n");
		printf("Student Name:  ");
		printf("%s %-23s\n",root->first,root->last);

		//	printf(" %-15s",root->last);
		printf("Student ID:%8d\n",root->ID);
		printf("Total Grade:%5d\n",root->marks);
		//if the ID is found, it yield 1
		//if the ID is not found, it will stay at 0, therefore telling the user that the name does not exist
		*find=1;
	}

	//if there is something right, go right
	if(root->right != NULL){
		searchTreeID(root->right,id, find);

	}
	//this function is a recursive function and it will only stop when the entire tree is printed out
}


int main(int argc, char* argv[]){
	struct StudentRecord data, *rootName, *rootID;
	FILE *NamesIDs;
	FILE *Marks;
	int start =0;// checks if the database can be started

	//not enough arguments on cmd line
	if ((NamesIDs = fopen(argv[1],"r")) == NULL) { // open Names IDs file
		printf("Can't open %s\n",argv[1]);
		return -1;
	}
	if ((Marks = fopen(argv[2],"r")) == NULL) { // open marks file
		printf("Can't open %s\n",argv[2]);
		return -2;
	}
	else{//if all the contions are satisfied, the program can start since start=1
		printf("Building database...\n");
		printf("Finished...\n");
		start=1;

	}

	rootName=NULL; // initialize 2 B-Trees
	rootID=NULL;
	int numrecords=0;
	while (fscanf(NamesIDs,"%s%s%d", // scan record into structure
			&(data.first[0]),
			&(data.last[0]),
			&(data.ID)) != EOF) {
		fscanf(Marks,"%d",&(data.marks)); // marks too
		numrecords++;
		addNode (&rootName,&data,2); // copy to B-Tree sorted by last
		if (rootName==NULL) {
			printf("Error creating name B-Tree, aborted.\n");
			return -3;
		}
		addNode (&rootID,&data,1); // copy to B-Tree sorted by last
		if (rootID==NULL) {
			printf("Error creating ID B-Tree, aborted.\n");
			return -4;
		}
	}
	fclose(NamesIDs);
	fclose(Marks);


	//since there is no error, the value of start=1, this starts the program
	while (start==1){

		char entered[100];//entered string by the used
		char name[100];//name entered by the user
		int id;//ID entered by the user
		printf("\n");
		printf("sdb:");
		scanf("%s",&entered);//scans for what the user enters

		//compares the string that will be converted to uppercase
		//makes the program case insensitive
		int b;
		for(b=0; entered[b]!='\0'; b++){
			if (entered[b] >= 'a' && entered[b] <= 'z'){
				entered[b]=entered[b]-('a'-'A');
			}
		}

		/*
		 * strcmp compares the string entered by the user to the string of the function.
		 * If it matches, it will yiel 0, if not, it will compare to the next one until one is matched
		 * if non is matched, it will print out the error
		 */
		//lists by last name
		if (strcmp(entered,"LN")==0){
			printf("\n");
			printf("Student Record Database sorted by Student ID\n");
			printf("\n");
			traverseID(rootName);//calls the function traverseID with the argument rootName [sort in order by last name]

		}
		//lists by student ID
		else if(strcmp(entered,"LI")==0){
			printf("\n");
			printf("Student Record Database sorted by Last Name\n");
			printf("\n");
			traverseID(rootID);//calls the function traverseID with the argument rootID [sort in order by ID]


		}
		//searches with last name
		else if(strcmp(entered,"FN")==0){
			printf("Enter name to search:");
			scanf("%s",&name);//scans for the name entered by the user
			int i;
			int found =0;//found is to detect if the last name can be found in the btree
			//this makes the program case insensitive
			for(i=0; name[i]!='\0'; i++){
				//converts first letter to uppercase
				if (name[0] >= 'a' && name[i] <= 'z'){
					name[0]=name[0]-('a'-'A');
				}
				//convert ther rest of letters in lowercase
				if(name[i]>='A' && name[i]<='Z'){
					name[i]=name[i]+('a'-'A');
				}
			}
			/*
			 * calls the function searchTreeName with the arguments of rootName [sort by name]
			 * with the name entered by the user and the value of found
			 */
			searchTreeName(rootName,name, &found);
			//if it is not found, the value of found will stay at 0, therefore printing out the error message
			if (found!=1){
				printf("There is no student with that name.\n");
			}
		}
		//searches with ID
		else if(strcmp(entered,"FI")==0){
			int find=0;//find is to detect if the last name can be found in the btree
			printf("Enter ID to search:");
			scanf("%d",&id);//scans for the ID entered by the user

			/*
			 * calls the function searchTreeID with the arguments of rootID [sort by ID]
			 * with the ID entered by the user and the value of found
			 */
			searchTreeID(rootID,id, &find);

			//if it is not found, the value of find will stay at 0, therefore printing out the error message
			if (find!=1){
				printf("There is no student with that ID.\n");
			}

		}
		//prints out help message for user
		else if(strcmp(entered,"HELP")==0){
			printf("LN List all the records in the database ordered by last name.\n");
			printf("LI List all the records in the database ordered by student ID.\n");
			printf("FN Prompts for a name and lists the record of the student with the corresponding name.\n");
			printf("FI Prompts for a name and lists the record of the student with the corresponding ID.\n");
			printf("HELP Prints this list\n");
			printf("? Prints this list\n");
			printf("QUIT Exits the program.\n");
			printf("\n");
		}
		//prints out help message for user
		else if(strcmp(entered,"?")==0){
			printf("LN List all the records in the database ordered by last name.\n");
			printf("LI List all the records in the database ordered by student ID.\n");
			printf("FN Prompts for a name and lists the record of the student with the corresponding name.\n");
			printf("FI Prompts for a name and lists the record of the student with the corresponding ID.\n");
			printf("HELP Prints this list\n");
			printf("? Prints this list\n");
			printf("QUIT Exits the program.\n");
			printf("\n");

		}
		//allows user to quit the program
		else if(strcmp(entered,"QUIT")==0){
			printf("Program terminated...");
			start=0;//since start is set to 0, the program will stop
		}
		//if none of the if clauses are met, it will display the error message of invalid command
		else{
			printf("Error, invalid command.");
			printf("\n");
		}


	}
	start =0;//this will terminate the program
	return 0;
}


