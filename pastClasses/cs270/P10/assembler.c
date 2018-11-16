/** @file assembler.c
 *  @brief You will modify this file and implement the assembler.h interface
 *  @details This is a implementation of the interface that you will write
 *  for the assignment. Although there are only four functions defined
 *  by the interface, it is likely that you will add many helper functions
 *  to perform specific tasks.
 *  <p>
 *  @author <b>your name here</b>
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <stdarg.h>
#include <assert.h>

#include "assembler.h"
#include "lc3.h"
#include "symbol.h"
#include "tokens.h"
#include "util.h"

/** Global variable containing the current line number in the source file*/
static int srcLineNum;

/** Global variable containing the current LC3 address */
static int currAddr;

// do not modify
line_info_t* asm_init_line_info (line_info_t* info) {
  if (! info)
    info = malloc(sizeof(line_info_t));

  info->next        = NULL;
  info->lineNum     = srcLineNum;
  info->address     = currAddr;
  info->opcode      = OP_INVALID;
  info->format      = NULL;
  info->DR          = -1;
  info->SR1         = -1;
  info->SR2         = -1;
  info->immediate   = 0;
  info->reference   = NULL;

  return info;
}

// do not modify
void asm_print_line_info (line_info_t* info) {
  if (info) {
    fprintf(stderr, "%3d: addr: x%04x op: %s DR:%3d, SR1:%3d SR2:%3d imm: %d ref: %s\n",
           info->lineNum, info->address, lc3_get_opcode_name(info->opcode), info->DR,
           info->SR1, info->SR2, info->immediate, info->reference);
  }
}

// do not modify
/* based on code from http://www.eskimo.com/~scs/cclass/int/sx11c.html */
void asm_error (const char* msg, ...) {
  numErrors++;
  va_list argp;
  fprintf(stderr, "ERROR %3d: ", srcLineNum);
  va_start(argp, msg);
  vfprintf(stderr, msg, argp);
  va_end(argp);
  fprintf(stderr, "\n");
}

/** @todo implement this function */
void asm_init (void) {
  tokens_init();
  currAddr = 0;
  srcLineNum = 1;
}

int get_reg(const char* token) {
  int reg = util_get_reg(token);
  if (reg == -1) {
    asm_error(ERR_EXPECTED_REG,token);
    exit(0);
  }
  return reg;
}

int line_parser (char* line, sym_table_t* symTab, line_info_t* next) {
  const char* nextTok = nextTok = tokenize_lc3_line(line);
  if (nextTok == NULL)
    return 1;
  int opcode = util_get_opcode(nextTok);
  if (opcode == OP_INVALID && util_is_valid_label(nextTok) != 0){
    if (symbol_add(symTab,nextTok,currAddr) == 0) {
      asm_error(ERR_DUPLICATE_LABEL,nextTok);
      exit(0);
    }
    nextTok = next_token();
  }
  
  opcode = util_get_opcode(nextTok);
  
  next->opcode = opcode;
  switch (opcode) {
    case OP_ADD: case OP_AND: // R,R,R or R,R,imm5
      nextTok = next_token();
      next->DR = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      next->SR1 = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      if (util_get_reg(nextTok) != -1)
        next->SR2 = util_get_reg(nextTok);
      else
        lc3_get_int(nextTok,&next->immediate);
      currAddr++;
      break;
    case OP_LD: case OP_LDI: case OP_LEA: case OP_ST: case OP_STI: // R,off9
      nextTok = next_token();
      next->DR = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      next->reference = strdup(nextTok);
      currAddr++;
      break;
    case OP_LDR: case OP_STR: // R,R,off6
      nextTok = next_token();
      next->DR = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      next->SR1 = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      lc3_get_int(nextTok,&next->immediate);
      currAddr++;
      break;
    case OP_NOT: // R,R
      nextTok = next_token();
      next->DR = get_reg(nextTok);
      next_token();
      nextTok = next_token();
      next->SR1 = get_reg(nextTok);
      currAddr++;
      break;
    case OP_TRAP: // tv8
      nextTok = next_token();
      lc3_get_int(nextTok,&next->immediate);
      currAddr++;
      break;
    case OP_END:
      return 2;
      break;
    case OP_ORIG:
      nextTok = next_token();
      lc3_get_int(nextTok,&next->immediate);
      lc3_get_int(nextTok,&currAddr);
      break;
    case OP_BLKW:
      nextTok = next_token();
      int i;
      lc3_get_int(nextTok,&i);
      next->immediate = i;
      currAddr += i;
      break;
    case OP_FILL:
      nextTok = next_token();
      lc3_get_int(nextTok,&next->immediate);
      currAddr++;
      break;
    case OP_GETC: case OP_OUT: case OP_PUTS: case OP_IN: case OP_PUTSP: case OP_HALT: case OP_GETS: case OP_RTI:
      currAddr++;
      break;
    case OP_BR:
      if (strcasecmp(nextTok,"br")!= 0) {
        int branch = 0;
        if (strlen(nextTok) > 2) {
          if (nextTok[2] == 'n' || nextTok[2] == 'N')
            branch += 4;
          else if (nextTok[2] == 'z' || nextTok[2] == 'Z')
            branch += 2;
          else if (nextTok[2] == 'p' || nextTok[2] == 'P')
            branch += 1;
        }
        if (strlen(nextTok) > 3) {
          if (nextTok[3] == 'z' || nextTok[3] == 'Z')
            branch += 2;
          else if (nextTok[3] == 'p' || nextTok[3] == 'P')
            branch += 1;
          }
        if (strlen(nextTok) > 4)
          if (nextTok[4] == 'p' || nextTok[4] == 'P')
            branch += 1;
        next->DR = branch;
      }
      else
        next->DR = 7;
      nextTok = next_token();
      next->reference = strdup(nextTok);
      currAddr++;
      break;
    case OP_JSR_JSRR:
      if (strcasecmp(nextTok,"jsr") == 0){
        nextTok = next_token();
        next->reference = strdup(nextTok);
      }
      else {
        nextTok = next_token();
        next->SR1 = get_reg(nextTok);
      }
      currAddr++;
      break;
    case OP_JMP_RET:
      if (strcasecmp(nextTok,"jmp") == 0){
        nextTok = next_token();
        next->SR1 = get_reg(nextTok);
      }
      currAddr++;
      break;
    default: // have to handle jmp, ret
      printf("INVALID OP\n");
  }
  return 0;
}

/** @todo implement this function */
line_info_t* asm_pass_one (const char* asm_file_name,
                           const char* sym_file_name) {
  FILE* f = fopen(asm_file_name,"r");

  if (!f) {
    asm_error(ERR_OPEN_READ, asm_file_name);
    exit(0);
  }

  FILE* s = fopen(sym_file_name,"w");

  if (!f) {
    asm_error(ERR_OPEN_WRITE, sym_file_name);
    exit(0);
  }

  lc3_sym_tab = symbol_init(997,0);

  line_info_t* head = malloc(sizeof(line_info_t));
  head = asm_init_line_info(head);
  line_info_t* next = head;
  line_info_t* temp = NULL;

  char nextLine[256];
  while (fgets(nextLine, sizeof nextLine, f) != NULL){
    temp = asm_init_line_info(temp);
    srcLineNum ++;
    int parseVal = line_parser(nextLine,lc3_sym_tab,temp);
    if (parseVal == 0){
      next->next = temp;
      next = next->next;
    }
    if (parseVal == 2)
      break;
    temp = NULL;
  }
  printf("\n");
  lc3_write_sym_table (s);
  fclose(f);
  fclose(s);
  return head->next;
}

int get_pc_offset(char* label, int address, sym_table_t* sym_tab, int length) {
  // Acceptable range is between -2^(length-1) and 2^(length-1)-1
  int range = 1 << (length - 1);
  int mask = (1 << length) - 1;
  if (symbol_find_by_name(sym_tab,label) == NULL) {
    asm_error(ERR_MISSING_LABEL,label);
    exit(0);
  }
  int value = symbol_find_by_name(sym_tab,label)->addr - address - 1;
  if (value > -range && value < range - 1)
    return value & mask;
  else {
    asm_error(ERR_BAD_PCOFFSET,label);
    exit(0);
  }
}

void asm_pass_two (const char* obj_file_name, line_info_t* list) {
  int hexval;
  
  char* sym_file_name = strdup(lc3_replace_suffix(strdup(obj_file_name),".sym"));
  FILE* s = fopen(sym_file_name,"r");

  if (!s) {
    asm_error(ERR_OPEN_READ, sym_file_name);
    exit(0);
  }

  lc3_read_sym_table(s);
  
  FILE* h = fopen(obj_file_name,"w");
  
  if (!h) {
    asm_error(ERR_OPEN_WRITE, obj_file_name);
    exit(0);
  }

  lc3_set_obj_file_mode(obj_file_name);

  while (list != NULL) {
    asm_print_line_info(list);
    int op = list->opcode;

    if ((op == OP_ADD || op == OP_AND) && list->SR2 == -1)
      hexval = lc3_get_inst_info(op)->forms[1].prototype + list->immediate; // TEST TO SEE IF IMMEDIATE IS IN RANGE
    else if (op == OP_ADD || op == OP_AND)
      hexval = lc3_get_inst_info(op)->forms[0].prototype + list->SR2;
    else
      hexval = lc3_get_inst_info(op)->forms[0].prototype;
    
    if (op == OP_ORIG){
      hexval = list->immediate;
    }

    // DR in bits 9-11
    if (op == OP_ADD || op == OP_AND || op == OP_LD ||
                op == OP_LDI || op == OP_LDR || op == OP_LEA ||
                op == OP_NOT || op == OP_BR || op == OP_ST || op == OP_STI || op == OP_STR) {
      hexval += (list->DR) << 9;
    }
    
    if (op == OP_JMP_RET && list->SR1 == -1) {
      hexval += 7 << 6;
    }
    
    // SR1 in bits 6-8
    if (op == OP_ADD || op == OP_AND || (op == OP_JMP_RET && list->SR1 != -1) || (op == OP_JSR_JSRR && list->SR1 != -1) ||
                 op == OP_LDR || op == OP_NOT || op == OP_STR) {
      hexval += (list->SR1) << 6;
    }

    // Adding immediate value
    if (op == OP_LDR || op == OP_STR || op == OP_TRAP || op == OP_FILL) {
      hexval += list->immediate;
    }

    // has offset9
    if (op == OP_BR || op == OP_LD || op == OP_LDI ||op == OP_LEA || op == OP_ST || op == OP_STI) {
      hexval += get_pc_offset(list->reference,list->address,lc3_sym_tab, 9);
    }

    // has offset11
    if (op == OP_JSR_JSRR && list->SR1 == -1) {
      hexval += get_pc_offset(list->reference,list->address,lc3_sym_tab, 12);
    }

    
    if (op == OP_BLKW) {
      for (int i=0;i<list->immediate;i++)
       fprintf(h,"0000\n");
      list = list->next;
      continue;
    }
    
    lc3_write_LC3_word(h,hexval);
    list = list->next;
  }
  fclose(h);
}

/** @todo implement this function */
void asm_term (line_info_t* list) {
  // FREE UP LINKED LIST
//  line_info_t* templist = NULL;
//  while (list != NULL) {
//    templist = list->next;
//    free(list->reference);
//    free(list);
//    list = templist->next;
//  }
  tokens_term();
}
