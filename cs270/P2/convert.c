// P2 Assignment - convert.c
// Author: Sean Russell
// Date:   9/4/2015
// Class:  CS270
// Email:  slrussel@rams.colostate.edu

#include <stdio.h>
#include <string.h>
#include <math.h>

// char2int DONE
int char2int(char digit){
    if ('0' <= digit && digit <= '9')
        return digit - '0';
    if ('A' <= digit && digit <= 'F')
        return digit - 'A' + 10;
    return -1;
}

// int2char DONE
char int2char(int digit){
    if (0 <= digit && digit <= 9)
        return digit + '0';
    if (10 <= digit && digit <= 15)
        return digit + 'A' - 10;
    return 'X';
}

void int2asciiRec(int value, int base){
    if (value == 0)
	return;
    int2asciiRec(value/base,base);
    printf("%c",int2char(value % base));
}

// Convert integer to string in specified base and print
// 2 <= base <= 16
void int2ascii(int value, int base){
    int2asciiRec(value,base);
}

// Convert string in specified base to integer and print
// 2 <= base <= 16
void ascii2int(char *ascii, int base){
    int total = 0;
    for (int i=strlen(ascii);i>0;i--){
        total += char2int(ascii[i-1]) * pow(base,strlen(ascii)-i);
    }
    printf("%d",total);
}
