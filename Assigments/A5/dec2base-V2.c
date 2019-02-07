/*
 * Name: Ding Ma
 * ID: 260871301
 */



#include <stdio.h>
#include <stdlib.h>
//gcc dec2base.c -o run


// In c, the functions have to be defined before they are used
//this fucntions reverses the string
void revStr(char *str, int length, int b, int display){// int b was added in order to make the print function display the base that we are converting to
	char* str2 = (char*)(malloc(sizeof(char)*length));
	int i; //initialize the variable for the forloop
	for(i=0; i<length;i++){
		str2[length-1-i]= str[i];
	}
	//prints out the number converted in that base
	printf("The Base-%d",b);
	printf(" form of %d", display);
	printf(" is: ");
	for (i=0;i<length;i++){
		printf("%c",str2[i]);
	}
	printf("\n");

	//set the pointer of str -> str_rev
	//char* = &rev;
//scanner on argv1, argv2
}
void dec2base(int input,int base,char *str, int display){
	int a=input;
	int b=base;
	int i=0;
	int length =0;
	//printf("The quotients for calculating the binary numbers are:\n");
	while(a>0){
		int q= a/b;
		int r=a%b;
		length ++;
		if (r<=9)
			str[i]= r+'0';
		else if (r>9)
			str[i]= 'A' + r-10;
		//		printf("r= %c\n", str[i]); //prints quotient
		i++;
		a=q;
	}
	revStr(str,length, b, display);//passes these values by reference to the function "revStr"
}

int main() {
	int a;

	printf("Enter an integer in decimal number system: \n");
	//	scanf("%d", &a);


	char term;
	//detects for integer
	if(scanf("%d%c", &a, &term) != 2 || term !='\n')
		printf("Error: number must be in the range of [0,2147483647]\n");
	//detects for negative numbers
	else  if( a < 0)
		printf("Error: number must be in the range of [0,2147483647]\n");
	else {
		int b;
		printf("Enter a base between 2 and 36: \n");
		scanf("%d", &b);
		//detects for bases that are smaller than 2 and bigger than 36
		if (b<2 || b> 36)
			printf("Error: base must be in the range [2,36]\n ");
		//this is for handelling when the user inputs 0 case
		else if(a==0){
			printf("The Base-%d",b);
			printf(" form of 0 is 0");
			printf("\n");
			printf("The reversed string of 0 is 0");
		}
		// if none of those conditions are satisfied, the program can then run
		else{

			int display =a; // this is used to display the number in the printed lines
			int n=0;//initilize n
			int bk=a; // gets a backup of a
			//this part does the division, q as the quotion, r as the remainder
			//a is the entered integerand b is the entered base
			while(a>0){
				int q=a/b; //returns the integer part of the division
				int r=a%b;// returns the remainder part of the division
				a=q;
				n++;
			}
			a=bk;
			char* fResult=(char*)(malloc(n*sizeof(char)));
			dec2base(a,b,fResult, display); // passes these values by reference the the function "dec2base"
			int i;// initialize the variable for the forloop
			printf("The reversed string of %d", display);
			printf(" converted number is: ");
			for (i=0; i<n; i++)
				printf("%c",fResult[i]);
			printf("\n");
		}
	}
	return 0;
}
