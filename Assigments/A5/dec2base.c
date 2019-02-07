/*
 * Name: Ding Ma
 * ID: 260871301
 */



#include <stdio.h>
#include <stdlib.h>



// In c, the functions have to be defined before they are used
//this fucntions reverses the string
void revStr(char *str, int length){
	//dymanically allocates memory
	char* str2 = (char*)(malloc(sizeof(char)*length));
	int i; //initialize the variable for the forloop
	for(i=0; i<length;i++){
		str2[length-1-i]= str[i];//definining size of the array
	}
	//prints out the number converted in that base

	for (i=0;i<length;i++){
		printf("%c",str2[i]);
	}


}
void dec2base(int input,int base,char *str){
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
	revStr(str, i);//passes these values by reference to the function "revStr"
}

int main(int argc, char *argv[]) {
	int a;
	int b;
/*
 * scans in the command line for the 3 arguments
 * argv[0] is dec2base so that can be ignored
 * argv[1] is the number
 * argv[2] is the base
 * returning a number stops the program
 */
	if(argc ==3){
		sscanf(argv[1], "%d",&a);
		sscanf(argv[2], "%d",&b);
	}
	if (argc==2){
		//if 2 arguments, the base is set to default for 2
		sscanf(argv[1], "%d",&a);
		b=2;
	}
	if(argc==1||argc>3){
		printf("Error invalid amount of arguments");
		return 1;
	}
//check if the number is positive
	if( a < 0){
		printf("Error: number must be in the range of [0,2147483647]\n");
	return 1;
	}
//check if the number is less than the allowed integer number
	if(a>2147483647){
		printf("Error: number must be in the range of [0,2147483647]\n");
	return 1;
	}
	//checks if the base is between 2 and 36
	if (b<2 || b> 36){
		printf("Error: base must be in the range [2,36]\n ");
	return 1;
	}
//if the entered number is 0, this is what it should be done otherwise, there will be no output
	if(a==0){
		printf("The Base-%d",b);
		printf(" form of 0 is 0");
		printf("\n");
		return 1;
	}

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
				//malloc dynamically allocates memory
	char* fResult=(char*)(malloc(n*sizeof(char)));
	//prints out the converted number
	printf("The Base-%d",b);
		printf(" form of %d", display);
		printf(" is: ");
	dec2base(a,b,fResult); // passes these values by reference the the function "dec2base"

	return 0;
}
