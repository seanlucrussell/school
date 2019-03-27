// File: struct.h

// Structure definition
typedef struct
{
    char firstName[80];
    char lastName[80];
    int hwAvg;
    int labAvg;
    int midtermScore;
    int finalScore;
    float totalPoints;
    char letterGrade;
} Student;

// Function Declarations 
void inputScores(Student *student);
void outputScores(Student student);
void calculateScores(Student *student);
