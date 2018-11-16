import math
import fractions

def factor(n):
    factors = []
    for i in range(2,n+1):
        if n % i == 0:
            factors.append(i)
            factors += factor(n/i)
            break
    return factors    

def legendre(a, p):
    return pow(a,(p-1)/2,p)

def squareRoot(a, N):
    for i in range(N):
        if pow(i,2,N) == a:
            print (i)

def pollard(N):
    a = 2
    for j in range(2,N):
        a = pow(a,j,N)
        d = fractions.gcd(a-1,N)
        if 1 < d and d < N:
            return d

def witness(n,a):
    if n % 2 == 0 or (1 < fractions.gcd(n,a) and fractions.gcd(n,a) < n):
        return 'composite'
    q = n-1
    k = 0
    while q % 2 == 0:
        k += 1
        q = q / 2
    a = pow(a,q,n)
    if a % n == 1:
        return 'test fails'
    for i in range(k):
        if a % n == -1 % n:
            return 'test fails'
        a = pow(a,2,n)
    return 'composite'

def countPrimes(N):
    return len(primes(N))

def primes(N):
    ps = []
    for i in range(2,N+1):
        if all( i % p != 0 for p in ps):
            ps.append(i)
    return ps

def cardD(p,q):
    card = 0
    for i in range(1,p*q+1):
        if i % p == 0:
            card += 1
    return card

def cardE(p,q):
    card = 0
    for i in range(1,p*q+1):
        if i % q == 0:
            card += 1
    return card

def cardDE(p,q):
    card = 0
    for i in range(1,p*q+1):
        if i % p == 0 and i % q == 0:
            card += 1
    return card

def modInverse(a,p):
    return pow(a, p-2, p)

def dumbInverse(a,p):
    for solution in range(p):
        if a * solution % p == 1:
            return solution

def modLog(a,b,n):
    m = int(math.ceil(math.sqrt(n)))
    table = {}
    for j in range(m):
        table[pow(a,j,n)] = j
    am = pow(pow(a, n - 2, n), m, n)
    gamma = b
    for i in range(m):
        if gamma in table:
            return i * m + table[gamma]
        gamma = gamma * am % n

def dumbLog(a,b,p):
    for i in range(p):
        if pow(a, i, p) == b:
            return i
'''
def factor(N, pmin1qmin1):
    pplusq = N + 1 - pmin1qmin1
    p = (pplusq + math.sqrt(pplusq**2-4*N))/2
    q = (pplusq - math.sqrt(pplusq**2-4*N))/2
    return p,q,p*q,p*q==N
'''
