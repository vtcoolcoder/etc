var data = new double[] {0.5, 0.75, 1.0, 1.25, 1.5, 2.0, 2.5, 5.0};

for (int i = 0; i < data.length - 1; ++i) {
    for (int j = i + 1; j < data.length; ++j) {
        System.out.println(STR."\{data[i]} + \{data[j]}");
    }
}


static void fill3() {
    final var diskAmount = 3;
    var outerSize = data.length - diskAmount;
    var indexes = new int[outerSize][][];
    
    for (int idx = 0; idx < outerSize; ++idx) {
        var offset = idx + 1;
        indexes[idx] = new int[data.length - (offset + 1)][];
        
        for (int j = offset, k = 0; j < data.length - 1; ++j, ++k) {
            indexes[idx][k] = new int[data.length - (j + 1)];
            
            for (int w = j + 1, q = 0; w < data.length; ++w, ++q) {
                indexes[idx][k][q] = w;
            }
        }
    }
    
    System.out.println(Arrays.deepToString(indexes));
}


static void fill2() {
    final var diskAmount = 2;
    var difference = data.length - diskAmount + 1;
    var indexes = new int[difference][];
    
    for (int idx = 0; idx < difference; ++idx) {
        var offset = idx + 1;
        indexes[idx] = new int[data.length - offset];
        
        for (int j = offset, k = 0; j < data.length; ++j, ++k) {
            indexes[idx][k] = j;
        }
    }
    
    System.out.println(Arrays.deepToString(indexes));
}



static void print(int i, int j, int k, int l, int m, int n, int o, int p) {
    if ((i == data.length - 8)
            && (j == data.length - 7)
            && (k == data.length - 6)
            && (l == data.length - 5)
            && (m == data.length - 4)
            && (n == data.length - 3)
            && (o == data.length - 2)
            && (p == data.length)) {
        return;
    } else if (p == data.length) {
        p = ++o + 1;
        
        if (o == data.length - 1) {
            o = ++n + 1;
            p = o + 1;
            
            if (n == data.length - 2) {
                n = ++m + 1;
                o = n + 1;
                p = o + 1;
                
                if (m == data.length - 3) {
                    m = ++l + 1;
                    n = m + 1;
                    o = n + 1;
                    p = o + 1;
                    
                    if (l == data.length - 4) {
                        l = ++k + 1;
                        m = l + 1;
                        n = m + 1;
                        o = n + 1;
                        p = o + 1;
                        
                        if (k == data.length - 5) {
                            k = ++j + 1;
                            l = k + 1;
                            m = l + 1;
                            n = m + 1;
                            o = n + 1;
                            p = o + 1;
                            
                            if (j == data.length - 6) {
                                j = ++i + 1;
                                k = j + 1;
                                l = k + 1;
                                m = l + 1;
                                n = m + 1;
                                o = n + 1;
                                p = o + 1;
                            }
                        }
                    }
                }
            }
        }
    }
    
    System.out.println(
        STR."\{data[i]} + \{data[j]} + \{data[k]} + \{data[l]} + \{data[m]} + \{data[n]} + \{data[o]} + \{data[p]} = \{data[i] + data[j] + data[k] + data[l] + data[m] + data[n] + data[o] + data[p]}");
        
    print(i, j, k, l, m, n, o, p + 1);
}


static void print(int i, int j, int k, int l, int m, int n, int o) {
    if ((i == data.length - 7)
            && (j == data.length - 6)
            && (k == data.length - 5)
            && (l == data.length - 4)
            && (m == data.length - 3)
            && (n == data.length - 2)
            && (o == data.length)) {
        return;
    } else if (o == data.length) {
        o = ++n + 1;
        
        if (n == data.length - 1) {
            n = ++m + 1;
            o = n + 1;
            
            if (m == data.length - 2) {
                m = ++l + 1;
                n = m + 1;
                o = n + 1;
                
                if (l == data.length - 3) {
                    l = ++k + 1;
                    m = l + 1;
                    n = m + 1;
                    o = n + 1;
                    
                    if (k == data.length - 4) {
                        k = ++j + 1;
                        l = k + 1;
                        m = l + 1;
                        n = m + 1;
                        o = n + 1;
                        
                        if (j == data.length - 5) {
                            j = ++i + 1;
                            k = j + 1;
                            l = k + 1;
                            m = l + 1;
                            n = m + 1;
                            o = n + 1;
                        }
                    }
                }
            }
        }
    }
    
    System.out.println(
        STR."\{data[i]} + \{data[j]} + \{data[k]} + \{data[l]} + \{data[m]} + \{data[n]} + \{data[o]} = \{data[i] + data[j] + data[k] + data[l] + data[m] + data[n] + data[o]}");
    
    print(i, j, k, l, m, n, o + 1);
}


static void print(int i, int j, int k, int l, int m, int n) {
    if ((i == data.length - 6)
            && (j == data.length - 5)
            && (k == data.length - 4)
            && (l == data.length - 3)
            && (m == data.length - 2)
            && (n == data.length)) {
        return;
    } else if (n == data.length) {
        n = ++m + 1;
        
        if (m == data.length - 1) {
            m = ++l + 1;
            n = m + 1;
            
            if (l == data.length - 2) {
                l = ++k + 1;
                m = l + 1;
                n = m + 1;
                
                if (k == data.length - 3) {
                    k = ++j + 1;
                    l = k + 1;
                    m = l + 1;
                    n = m + 1;
                    
                    if (j == data.length - 4) {
                        j = ++i + 1;
                        k = j + 1;
                        l = k + 1;
                        m = l + 1;
                        n = m + 1;
                    }
                }
            }
        }
    }
    
    System.out.println(
        STR."\{data[i]} + \{data[j]} + \{data[k]} + \{data[l]} + \{data[m]} + \{data[n]} = \{data[i] + data[j] + data[k] + data[l] + data[m] + data[n]}");
    
    print(i, j, k, l, m, n + 1);
}


static void print(int i, int j, int k, int l, int m) {
    if ((i == data.length - 5)
            && (j == data.length - 4)
            && (k == data.length - 3)
            && (l == data.length - 2)
            && (m == data.length)) {
        return;
    } else if (m == data.length) {
        m = ++l + 1;
        
        if (l == data.length - 1) {
            l = ++k + 1;
            m = l + 1;
            
            if (k == data.length - 2) {
                k = ++j + 1;
                l = k + 1;
                m = l + 1;
                
                if (j == data.length - 3) {
                    j = ++i + 1;
                    k = j + 1;
                    l = k + 1;
                    m = l + 1;
                }
            }
        }
    }
    
    System.out.println(STR.
        "\{data[i]} + \{data[j]} + \{data[k]} + \{data[l]} + \{data[m]} = \{data[i] + data[j] + data[k] + data[l] + data[m]}");
    
    print(i, j, k, l, m + 1);
}


static void print(int i, int j, int k, int l) {
    if ((i == data.length - 4) 
            && (j == data.length - 3) 
            && (k == data.length - 2) 
            && (l == data.length)) {
        return;
    } else if (l == data.length) {
        l = ++k + 1;
        
        if (k == data.length - 1) {
            k = ++j + 1;
            l = k + 1;
            
            if (j == data.length - 2) {
                j = ++i + 1;
                k = j + 1;
                l = k + 1;
            }
        }
    }
    
    System.out.println(
        STR."\{data[i]} + \{data[j]} + \{data[k]} + \{data[l]} = \{data[i] + data[j] + data[k] + data[l]}");
    
    print(i, j, k, l + 1);
}

 
static void print(int i, int j, int k) {    
    if ((i == data.length - 3) && (j == data.length - 2) && (k == data.length)) {
        return;
    } else if (k == data.length) {
        k = ++j + 1;
        
        if (j == data.length - 1) {
             j = ++i + 1;
             k = j + 1;
        }
    } 
    
    System.out.println(STR."\{data[i]} + \{data[j]} + \{data[k]} = \{data[i] + data[j] + data[k]}");
    print(i, j, k + 1);
}


static void print(int i, int j) {
    if ((i == data.length - 2) && (j == data.length)) {
        return;
    } else if (j == data.length) {
        j = ++i + 1;
    }
       
    System.out.println(STR."\{data[i]} + \{data[j]} = \{data[i] + data[j]}");  
    
    print(i, j + 1);
}