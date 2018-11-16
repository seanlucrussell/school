/*
 * testPrint.c - simple driver to test methods of printnum.h.
 *
 * "Copyright (c) 2013-15 by Fritz Sieker."
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written
 * agreement is hereby granted, provided that the above copyright notice
 * and the following two paragraphs appear in all copies of this software.
 *
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE TO ANY PARTY FOR DIRECT,
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 * OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE AUTHOR
 * HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * THE AUTHOR SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS"
 * BASIS, AND THE AUTHOR NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS."
 */

/** @mainpage cs270 Programming Assignment - print a number using recursion
 *  \htmlinclude "PRINTNUM.html"
 */

/** @file testPrint.c
 * @brief Driver to test functions of printnum.c (do not modify)
 *
 * @details This is a driver program to test the functions
 * defined in printnum.h and implemented in printnum.c. 
 */

#include <stdio.h>
#include <stdlib.h>

#include "printnum.h"

char* digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

/** Print a usage statement, then exit the program returning a non-zero
 *  value, the Linux convention indicating an error
 */
static void usage() {
  puts("Usage: testPrint value base");
  puts("");
  puts("e.g. testPrint 178 16");
  exit(1);
}

/** Get int value from argument, allowing binary input like 0b0111001
 * @param arg string containing value (decimal, octal, hex, binary)
 * @return value
 */
static int getArg (char* arg) {
  char* junk;
  int   value = (int) strtol(arg, &junk, 0);

  if ((*junk == 'B') || (*junk == 'b')) { /* user entered binary value */
    value = (int) strtol(junk + 1, &junk, 2); /* skip over 'B/b' */
    if (arg[0] == '-') {
      value = -value;
    }
  }

  if (*junk) {
    printf("ERROR - bad format: %s\n", arg);
  }

  return value;
}

/** Entry point of the program
 * @param argc count of arguments, will always be at least 1
 * @param argv array of parameters to program argv[0] is the name of
 * the program, so additional parameters will begin at index 1.
 * @return 0 the Linux convention for success.
 */

int main (int argc, char* argv[]) {
  if (argc < 3)
    usage();

  int num  = getArg(argv[1]);
  int base = getArg(argv[2]);

  printNum(num, base);
  putchar('\n');
  return 0;
}

