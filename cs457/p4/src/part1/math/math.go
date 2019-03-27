package math

type Myfloat64 struct {
     Value float64
}

func (a *Myfloat64) Add(b Myfloat64) Myfloat64 {
     return Myfloat64{a.Value + b.Value}
}

func (a *Myfloat64) Subtract(b Myfloat64) Myfloat64 {
     return Myfloat64{a.Value - b.Value}
}

func (a *Myfloat64) Multiply(b Myfloat64) Myfloat64 {
     return Myfloat64{a.Value * b.Value}
}

func (a *Myfloat64) Pow(b int) Myfloat64 {
     if b == 1 {
     	return *a
     }
     return Myfloat64{a.Value * a.Pow(b - 1).Value}
}
