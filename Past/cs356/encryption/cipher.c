#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int block; // this value is 1 if using block cipher, if stream cipher this value is 0
int encrypting; // this value is 1 if message is being encrypted, if it is being decrypted this value is 0
char* text;
char* key;
size_t textLength;
size_t keyLength;
const int BLOCK_SIZE = 8;
const char PADDING = 0x80;

int parseargs(int argc, char **argv){
  if (argc != 6) {
    printf("Error- Incorrect number of arguments: expecting 5\n");
    exit(0);
  }
  int error = 0;
  // arg1 checks
  if (strcmp(argv[1],"B")==0) block = 1;
  else if (strcmp(argv[1],"S")==0) block = 0;
  else {
    printf("Error- expecting 'S' or 'B' for arg1\n");
    error = 1;
  }
  // arg2 checks
  FILE* inputFile = fopen(argv[2],"rb");
  if (inputFile == NULL){
    printf("Error- could not open file %s for reading\n",argv[2]);
    error = 1;
  } else {
    fseek (inputFile, 0, SEEK_END);
    textLength = ftell (inputFile);
    fseek (inputFile, 0, SEEK_SET);
    text = malloc (textLength);
    fread (text, 1, textLength, inputFile);
    if (block && encrypting && textLength % BLOCK_SIZE != 0) {
      printf("Error- file to by decrypted must have block sizes of %d\n",BLOCK_SIZE);
      error = 1;
    }
  }
  fclose(inputFile);
  // arg4 checks
  FILE* keyFile = fopen(argv[4],"rb");
  if(keyFile == NULL){
    printf("Error- could not open file %s for reading\n",argv[4]);
    error = 1;
  } else {
    fseek (keyFile, 0, SEEK_END);
    keyLength = ftell (keyFile);
    fseek (keyFile, 0, SEEK_SET);
    key = malloc (keyLength);
    fread (key, 1, keyLength, keyFile);
  }
  if (block == 1 && keyLength != BLOCK_SIZE) {
    printf("Error- keysize for block cipher is not %d bytes\n", BLOCK_SIZE);
    error = 1;
  }
  fclose(keyFile);
  // arg5 checks
  if (strcmp(argv[5],"E")==0) encrypting = 1;
  else if (strcmp(argv[5],"D")==0) encrypting = 0;
  else {
    printf("Error- expecting 'E' or 'D' for arg5\n");
    error = 1;
  }
  return error;
}

void blockXOR() {
  for (int i=0;i<textLength;++i) text[i] ^= key[i%BLOCK_SIZE];
}

void blockSwap() {
  if (textLength == 0) return;
  char* start = text;
  char* end = text + textLength - 1;
  int keyPosition = 0;
  while (start < end) {
    if (key[keyPosition++] % 2 == 0) ++start;
    else {
      char temp = *start;
      *start = *end;
      *end = temp;
      ++start;
      --end;
    }
    if (keyPosition == BLOCK_SIZE) keyPosition = 0;
  }
}

void blockEncryption() {
  // add padding
  if (textLength % BLOCK_SIZE != 0) {
    char* newText = malloc (textLength + BLOCK_SIZE - textLength % BLOCK_SIZE);
    for (int i=0;i<textLength;++i) newText[i] = text[i];
    for (int i=textLength;i < textLength + BLOCK_SIZE - textLength % BLOCK_SIZE;++i) newText[i] = PADDING;
    textLength += BLOCK_SIZE - textLength % BLOCK_SIZE;
    free(text);
    text = newText;
  }
  blockXOR();
  blockSwap();
}

void blockDecryption() {
  blockSwap();
  blockXOR();
  // remove padding
  if (textLength == 0) return;
  int paddingStart = 0;
  while (text[paddingStart] != PADDING) {
    ++paddingStart;
    if (paddingStart == textLength) break;
  }
  textLength = paddingStart;
}

void streamEncryptionDecryption(){
  int keyPosition = 0;
  for (int i=0;i<textLength;i++) {
    text[i] ^= key[keyPosition++];
    if (keyPosition == keyLength) keyPosition = 0;
  }
}

// arguments:
//  arg1: B or S, for block or stream
//  arg2: Source file to be encrypted/decrypted
//  arg3: Output file for result of encryption/decryption
//  arg4: File containing key to be used for encryption/decryption
//  arg5: E or D, for encryption or decryption
int main (int argc, char **argv){
  if (parseargs(argc,argv) == 1) exit(0);
  
  if (!block) streamEncryptionDecryption();
  else if (encrypting) blockEncryption();
  else blockDecryption();

  FILE* outputFile = fopen(argv[3],"wb");  
  fwrite(text,sizeof(char),textLength,outputFile);
  fclose(outputFile);

  free(text);
  free(key);
}
