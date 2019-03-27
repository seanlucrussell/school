package main

import "part1/math"
import "fmt"
import "flag"

func main() {
     aFlag := flag.Float64("a",1,"first value")
     bFlag := flag.Float64("b",1,"second value")
     cFlag := flag.Float64("c",1,"third value")
     dFlag := flag.Float64("d",1,"fourth value")
     flag.Parse()
     a := math.Myfloat64{*aFlag}
     b := math.Myfloat64{*bFlag}
     c := math.Myfloat64{*cFlag}
     d := math.Myfloat64{*dFlag}
     asquared := a.Pow(2)
     twob := b.Multiply(math.Myfloat64{2})
     cd := c.Multiply(d)
     fmt.Println(asquared.Add(twob.Add(cd)).Value)
}