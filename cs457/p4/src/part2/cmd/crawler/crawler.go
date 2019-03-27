package main

import (
	"flag"
	"fmt"
	"time"
	"part2/links"
	"part2/crawl"
)

var (
	flagUrl         string
	linkSearchDepth int
	flagTimeoutSecs uint
)

func init() {
	// Import as explained here
	flag.StringVar(&flagUrl,"u","","url")
	flag.IntVar(&linkSearchDepth,"d",1,"search depth")
	flag.UintVar(&flagTimeoutSecs,"t",10,"timeout for http get in seconds")
}

func main() {
	flag.Parse()
	if flagUrl == "" {
	   fmt.Println("need to supply a url")
	   flag.Usage()
	   return
	}
	// Create a Time variable using the time package and record the time
	// Run the Crawl function and print the length of the Crawled output and the time taken
	parser := links.NewParserXtractor()
	crawler := crawl.NewDfsCrawler(flagTimeoutSecs)
	t1 := time.Now()
	links, err := crawler.Crawl(flagUrl,linkSearchDepth,parser)
	t2 := time.Now()
	fmt.Println("crawled:",len(links),"time:",t2.Sub(t1),"err:",err)
}
