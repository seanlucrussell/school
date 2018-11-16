package math

import "testing"

func TestAdd(t *testing.T) {
     a := Myfloat64{100.0}
     b := Myfloat64{-100.0}
     c := a.Add(b)
     if c.Value != 0.0 {
     	t.Errorf("Subtract failed. Expected 0.0 but got %f",c.Value)
     }
}

func TestSubtract(t *testing.T) {
     a := Myfloat64{4.3}
     b := Myfloat64{12}
     c := a.Subtract(b)
     if c.Value != -7.7 {
     	t.Errorf("Subtract failed. Expected -7.7 but got %f",c.Value)
     }
}

func TestMultiply(t *testing.T) {
     a := Myfloat64{-5.1}
     b := Myfloat64{-5}
     c := a.Multiply(b)
     if c.Value != 25.5 {
     	t.Errorf("Subtract failed. Expected 25.5 but got %f",c.Value)
     }
}

func TestPow(t *testing.T) {
     a := Myfloat64{-2}
     c := a.Pow(3)
     if c.Value != -8 {
     	t.Errorf("Subtract failed. Expected -8 but got %f",c.Value)
     }
}
