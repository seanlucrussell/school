package crawl

import (
	"fmt"
	"strings"
	"net/http"
	"time"
	"links"
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

	r, err = http.Get(from)
	if err != nil {
	   return nil, err
	}
	defer r.Body.Close()
	links, err = xtr.Xtract(r.Body)
	// perform the http.get() on the url. check for error
	// Using the Xtractor, extract all the links and return the links and error
	// Remember the close the body of the response before closing

	return links, err

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
	fmt.Println("DFS")
	retlinks := make(map[string]links.Links)
	recurse = func(url string, depth int, indent int) {
		fmt.Println(strings.Repeat("    ",indent),url)
		if depth == 1 {
		   return
		}
		links,err := getLinksFromBody(url,c.hcli,xtr)
		if err != nil {
		   fmt.Println("Error: ",err)
		   return
		}
		retlinks[url] = links
		for _, link := range links {
		    recurse(link.String(), depth-1, indent+1)
		}
	}
	recurse(url,depth,0)

	return retlinks, err
}
