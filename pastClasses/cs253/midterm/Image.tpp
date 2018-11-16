#ifndef IMAGE_TPP_DEFINED
#define IMAGE_TPP_DEFINED
#include <stdexcept>
#include <memory>
using std::shared_ptr;

// iterator for Image
template<typename PIXEL>
class ImageIterator {
public:
  ImageIterator(const shared_ptr<PIXEL>& src_ptr, PIXEL* current_ptr) : token(src_ptr), ptr(current_ptr) {}	// Initialize iterator
  PIXEL operator* () {return *ptr;}				// overload dereference operator
  ImageIterator<PIXEL>& operator++() {ptr++; return this;}	// overload preincrement operator
  ImageIterator<PIXEL> operator++(int) {ImageIterator<PIXEL> iter(*this); ptr++; return iter;} // overload postincrement operator
  bool operator == (const ImageIterator<PIXEL>& other) 		// overload equivilance operator
  {return ((token == other.token) && (ptr == other.ptr));}	// data and location must be the same
  bool operator != (const ImageIterator<PIXEL>& other) 		// overload non equivilance operation
  {return ((token != other.token) || (ptr != other.ptr));}	// data or location must be different

protected:
  shared_ptr<PIXEL> token;	// uses shared_ptr to hold information
  PIXEL* ptr;			// regular pointer for other stuff
};

template<typename PIXEL>
class Image {
public:
  typedef ImageIterator<PIXEL> iterator;				// typedef iterator for Image

  Image() : height(0), width(0), data(NULL), rows(NULL) {}		// no arg constructor, sets height & width to zero, sets data & rows to null
  Image(int h, int w) : height(h), width(w) { allocate_memory(); }	// two arg constructor, sets height witdth to args, calls allocate_memory()
  Image(const Image& img);						// copy constructor
  ~Image() { height = 0; width = 0; deallocate_memory(); }		// destructor, sets height & width to 0, calls deallocate_memory()

  inline int size() const { return width * height; }			// accessor, returns size of Image (calculated as height * width)
  inline PIXEL& operator() (unsigned int h, unsigned int w) 		// overload parenthesis operator, returns reference to pixel at (h,w)
  {return rows[h][w];}
  inline const PIXEL& operator() (unsigned int h, unsigned int w) const // const overlaod parenthesis op, returns const reference to pixel at (h,w)
  {return rows[h][w];}
  inline PIXEL& at(unsigned int h, unsigned int w);			// mutator, returns reference to pixel at (h,w)
  inline const PIXEL& at(unsigned int h, unsigned int w) const;		// const accessor, returns const reference to pixel at (h,w)

  iterator begin() {return ImageIterator<PIXEL>(data, data.get());}	// returns iterator at start of image?
  iterator end() 							// returns iterator at end of image?
  {return ImageIterator<PIXEL>(data, data.get() + size());}

protected:
  void allocate_memory();						// allocates memory?
  void deallocate_memory() { delete [] rows; }				// deallocates memory, still this.size() pointers plus the memory pointed at
  void copy_memory(PIXEL* src_data);					// copies memory?

  int height;
  int width;
  shared_ptr<PIXEL> data;
  PIXEL** rows;
};

// copy constructor
template<typename PIXEL>
Image<PIXEL>::Image(const Image<PIXEL>& img) : height(img.height), width(img.width)	// sets height and width to that of original
{
  allocate_memory();		// calls allocate memory
  copy_memory(img.data.get());	// calls copy_memory on the value of the pointer from the shared pointer (defeats purpose of shared pointer?)
}

// allocate_memory()
template<typename PIXEL>
void Image<PIXEL>::allocate_memory()
{
  data = shared_ptr<PIXEL>(new PIXEL[size()]);				// allocates contiguous space in memory for rows, sets data to point at head
  rows = new PIXEL*[height];						// creates 2darray of pointers
  PIXEL** row_end = rows + height;					// creates pointer to end of rows
  *rows = data.get();							// beginning of rows array equals pointer to data
  for(PIXEL** row_ptr = rows + 1; row_ptr < row_end; row_ptr++) {	// 2d array of pointers
    *row_ptr = *(row_ptr-1) + width;
  }
}

template<typename PIXEL>
void Image<PIXEL>::copy_memory(PIXEL* src_data) 
{
  PIXEL* ptr = data.get();
  PIXEL* end = ptr + size();
  while (ptr < end) { *ptr++ = *src_data++; }
}

// only accessor that throws exception
template<typename PIXEL>
PIXEL& Image<PIXEL>::at(unsigned int h, unsigned int w)
{
  if (h >= height) throw std::out_of_range("height");
  if (w >= width) throw std::out_of_range("width");
  return rows[h][w];
}

// const_casts other at function, still throws exeptions
template<typename PIXEL>
const PIXEL& Image<PIXEL>::at(unsigned int h, unsigned int w) const
{
  return (const_cast<Image<PIXEL>*>(this))->at(h,w);
}

#endif // IMAGE_TPP_DEFINED
