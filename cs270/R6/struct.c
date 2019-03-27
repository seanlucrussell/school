// File: struct.c

// Include files
#include <stdio.h>
#include "struct.h"

// Input scores
void inputScores(Student *student)
{
    printf("First name: ");
    scanf("%s", student->firstName);
    printf("Last name: ");
    scanf("%s", student->lastName);
    printf("Homework Average: ");
    scanf("%d", &student->hwAvg);
    printf("Lab Average: ");
    scanf("%d", &student->labAvg);
    printf("Midterm Score: ");
    scanf("%d", &student->midtermScore);
    printf("Final Score: ");
    scanf("%d", &student->finalScore);
}

// Output scores
void outputScores(Student student)
{
    printf("First name: %s\n", student.firstName);
    printf("Last name: %s\n", student.lastName);
    printf("Homework average: %d\n", student.hwAvg);
    printf("Lab average: %d\n", student.labAvg);
    printf("Midterm score: %d\n", student.midtermScore);
    printf("Final score: %d\n", student.finalScore);
    printf("Total points: %.3f\n", student.totalPoints);
    printf("Letter Grade: %c\n", student.letterGrade);
}

void calculateScores(Student *student)
{
    student->totalPoints = (student->hwAvg * 0.30) + (student->labAvg * 0.20) + (student->midtermScore * 0.20) + (student->finalScore * 0.30);
    if (student->totalPoints > 90.0) student->letterGrade = 'A';
    else if (student->totalPoints > 80.0) student->letterGrade = 'B';
    else if (student->totalPoints > 70.0) student->letterGrade = 'C';
    else if (student->totalPoints > 60.0) student->letterGrade = 'D';
    else student->letterGrade = 'F';
}
