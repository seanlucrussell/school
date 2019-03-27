package main

import "fmt"
import "net/http"
import "time"
import "io/ioutil"
import "strings"

func main() {
	s := "https://www.golang-book.com/books/intro/10#section1"
	timeout := time.Duration(5 * time.Second)
	client := http.Client {
		Timeout: timeout,
	}
	response,_ := client.Get(s)
	body,_ := ioutil.ReadAll(response.Body)
	bodyStr := string(body)
	fmt.Println(strings.Count(bodyStr,"the"))
	fmt.Println(strings.Count(bodyStr,"and"))
	fmt.Println(strings.Count(bodyStr,"channels"))
}
