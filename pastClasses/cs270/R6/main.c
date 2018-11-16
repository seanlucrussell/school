// File:        main.c
// Description: Keeps track of student records
// Author:      Sean Russell
// Date:        September 29 2015

// Include files
#include <stdio.h>
#include "struct.h"

// Function:     main
// Description:  entry point  
int main()
{
    Student student[5];
    for (int i=0;i<5;i++)
    {
        inputScores(&student[i]);
        calculateScores(&student[i]);
        outputScores(student[i]);
    }

    return 0;
}
