#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

#include "symbol.h"

/** @file symbol.c
 *  @brief You will modify this file and implement the symbol.h interface
 *  @details Your implementation of the functions defined in symbol.h.
 *  You may add other functions if you find it helpful. Added functions
 *  should be declared <b>static</b> to indicate they are only used
 *  within this file. The reference implementation added approximately
 *  90 lines of code to this file. This count includes lines containing
 *  only a single closing bracket (}).
 * <p>
 * @author <b>Sean Russell</b> goes here
 */

/** size of LC3 memory */
#define LC3_MEMORY_SIZE  (1 << 16)

/** Provide prototype for strdup() */
char *strdup(const char *s);

/** defines data structure used to store nodes in hash table */
typedef struct node {
  struct node* next;     /**< linked list of symbols at same index */
  int          hash;     /**< hash value - makes searching faster  */
  symbol_t     symbol;   /**< the data the user is interested in   */
} node_t;

/** defines the data structure for the hash table */
struct sym_table {
  int      size;        /**< size of hash table                          */
  node_t** hash_table;  /**< array of head of linked list for this index */
  char**   addr_table;  /**< look up symbols by addr (optional)          */
};

/** djb hash - found at http://www.cse.yorku.ca/~oz/hash.html
 * tolower() call to make case insensitive.
 */

static int symbol_hash (const char* name) {
  unsigned char* str  = (unsigned char*) name;
  unsigned long  hash = 5381;
  int c;

  while ((c = *str++))
    hash = ((hash << 5) + hash) + tolower(c); /* hash * 33 + c */

  c = hash & 0x7FFFFFFF; /* keep 31 bits - avoid negative values */

  return c;
}

sym_table_t* symbol_init (int table_size, int lookup_by_addr) {
  sym_table_t* s = (sym_table_t*)calloc(1,sizeof(sym_table_t));
  s->size = table_size;
  s->hash_table = calloc(sizeof(node_t*),table_size);
  return s;
}

int symbol_add (sym_table_t* symTab, const char* name, int addr) {
  int hash = 0;
  int index = 0;
  char* nameDup = strdup(name);
  if (symbol_search (symTab,nameDup,&hash,&index) == NULL){
    node_t* newNode = (node_t*)calloc(1,sizeof(node_t));
    newNode->next = symTab->hash_table[index];
    newNode->hash = hash;
    newNode->symbol.name = strdup(nameDup);
    newNode->symbol.addr = addr;
    symTab->hash_table[index] = newNode;
    return 1;
  }
  return 0;
}

struct node* symbol_search (sym_table_t* symTab, const char* name, int* hash, int* index) {
  *hash = symbol_hash(name);
  *index = *hash % symTab->size;
  node_t* nextPointer = symTab->hash_table[*index];
  while (nextPointer != NULL){
    if (strcmp(nextPointer->symbol.name,name) == 0){
      return nextPointer;
    }
    nextPointer = nextPointer->next;
  }
  return NULL;
}

symbol_t* symbol_find_by_name (sym_table_t* symTab, const char* name) {
  int h = 0, i = 0;
  node_t* n = symbol_search(symTab, name, &h, &i);
  if (n != NULL)
    return &n->symbol;
  return NULL;
}

void symbol_iterate (sym_table_t* symTab, iterate_fnc_t fnc, void* data) {
  for (int i=0; i<symTab->size; i++){
    node_t* currentPointer = symTab->hash_table[i];
    while (currentPointer != NULL){
      (*fnc)(&currentPointer->symbol, data);
      currentPointer = currentPointer->next;
    }
  }
}

/** @todo implement this function */
void symbol_reset(sym_table_t* symTab) {
  for (int i=0; i<symTab->size; i++){
    node_t* currentPointer = symTab->hash_table[i];
    symTab->hash_table[i] = NULL;
    node_t* nextPointer = currentPointer;
    while (currentPointer != NULL){
      nextPointer = currentPointer->next;
      free(currentPointer->symbol.name);
      free(currentPointer);
      currentPointer = nextPointer;
    }
  }
}

/** @todo implement this function */
char* symbol_find_by_addr (sym_table_t* symTab, int addr) {
  return NULL;
}

/** @todo implement this function */
void symbol_term (sym_table_t* symTab) {
  symbol_reset(symTab);
  free(symTab);
  symTab = NULL;
}

