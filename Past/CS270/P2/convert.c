// PA2 Assignment - convert.c
// Author: Sean Russell
// Date:   9/3/2015
// Class:  CS270
// Email:  slrussel@rams.colostate.edu

// char2int
// Converts from a character to an integer digit
// if character '0'..'9' convert to 0..9
// else if character 'A'..'F' convert to 10..15
// else convert to -1
int char2int(char digit){
	return digit-48;
}

// int2char
// Converts from an integer digit to a character
// if integer 0..9 convert to '0'..'9'
// else if integer 10..15 convert to 'A'..'F'
// else convert to 'X'
char int2char(int digit){
	return digit+48;
}

// Convert integer to string in specified base and print
// 2 <= base <= 16
void int2ascii(int value, int base){

}

// Convert string in specified base to integer and print
// 2 <= base <= 16
void ascii2int(char *ascii, int base){

}