Project 2B: Basic Math and Web Crawler
Submission by Sean Russell

Info: This folder contains the source code for a simple math utility and a web crawler implemented in go.
In order to run either of these, the GOPATH environment variable must be set with
   export GOPATH=[location_of_folder]/Russell_P2b
   
How do you run math and tests?
To run the math tests, from the root directory of this project type
   go test -v part1/math
To run the math program, which calculates a^2 + 2b + c*d, type
   go run src/part1/main.go
The values for a, b, c, and d are defaulted to 1. To changer their values, set their corresponding flags, eg:
   go run src/part1/main.go -a=4 -c=12

How do you run webcrawler?
The webcrawler is dependent on go's html parsing package. It can be installed with
   go get golang.org/x/net/html
To test the links package, run
   go test -v part2/links
To run the web crawler, type
   go run src/part2/cmd/crawler/crawler.go -u="http://www.cs.colostate.edu" -d=3
The flags the web crawler supports are as follows:
  -u string
    	url to start searching from. This flag is required
  -d int
    	depth for crawler to search (default 1)
  -t uint
    	timeout for http get in seconds (default 10)
