package main

import "os"
import "fmt"
import "net/http"
import "time"
import "io/ioutil"
import "strings"

func main() {
	s := os.Args[1]
	fmt.Println(s)
	timeout := time.Duration(5 * time.Second)
	client := http.Client {
		Timeout: timeout,
	}
	response,_ := client.Get(s)
	body,_ := ioutil.ReadAll(response.Body)
	bodyStr := string(body)
	//fmt.Println(bodyStr)
	split := strings.Split(bodyStr,"\"")
	for _, element := range split {
		if strings.Contains(element,"http") {
			fmt.Println(element)
		}
	}
	//fmt.Println(strings.Split(bodyStr,"\""))
}
