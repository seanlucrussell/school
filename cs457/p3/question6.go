package main

import (
	"fmt";
	"time";
	"net/http";
	"os"
	"io"
)

func main() {
	s := "https://www.cs.colostate.edu/~cs457/yr2018sp/home_syllabus.php"
	resp,_ := http.Get(s)
	fmt.Println(res)
	timeout := time.Duration(5 * time.Second)
	client := http.Client {
		Timeout: timeout,
	}
	resp,_ = client.Get(s)
	file,_ := os.Create("result.txt")
	io.Copy(file,resp.Body)
}
