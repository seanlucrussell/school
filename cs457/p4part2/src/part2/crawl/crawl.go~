package crawl

import (
	"fmt"

	"net/http"
	"time"
)

type Crawler interface {
	Crawl(url string, depth int, xtractor links.Xtractor) (map[string]links.Links, error)
}

//generates a string of  sequence of \t chars for print prefixing
func getPrefStr(forDepth int) string {
	prefixstr := make([]rune, forDepth)
	for i := range prefixstr {
		prefixstr[i] = '\t'
	}
	return string(prefixstr)
}

func getLinksFromBody(from string, hcli http.Client, xtr links.Xtractor) (links links.Links, err error) {
	var r *http.Response
	// perform the http.get() on the url. check for error
	// Using the Xtractor, extract all the links and return the links and error
	// Remember the close the body of the response before closing

	return

}


type dfsCrawler struct {
	hcli http.Client
}

func NewDfsCrawler(timeoutsec uint) *dfsCrawler {
	tdur := time.Duration(time.Duration(timeoutsec) * time.Second)
	return &dfsCrawler{
		hcli: http.Client{Timeout: tdur},
	}
}

func (c *dfsCrawler) Crawl(url string, depth int, xtr links.Xtractor) (map[string]links.Links, error) {
	var (
		err     error
		recurse func(string, int, int)
	)
	//create a map to store all the links extracted from one url.


	//get links from body using the function getLinksFromBody() defined above.
	// Using recursion or go routines, implement the depth first search.


	return retlinks, err
}
