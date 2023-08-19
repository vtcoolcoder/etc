#!/usr/bin/env python3


import sys


def fib(n):
    if (n < 3):
        return 1
        
    f = [1, 1] + [i*0 for i in range(2, n)]  
    
    for i in range(2, n):
        f[i] = f[i-1] + f[i-2]
        
    return f[-1]


def main():
    print(fib(int(sys.argv[1])))


if __name__ == "__main__":
    main()