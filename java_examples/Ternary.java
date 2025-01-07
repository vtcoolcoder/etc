return 
    (abcd.getDiscriminant() < 0) ? 
        Roots.NO_ROOTS :

    (abcd.getDiscriminant() == 0) ?
        Roots.X1 + new Roots( -abcd.getB() /2* abcd.getA() ).getX1() :
        
        
        Roots.X1 + new Roots(
            ( ( -abcd.getB() - Math.sqrt(abcd.getDiscriminant()) ) /2* abcd.getA() ),       
            ( ( -abcd.getB() + Math.sqrt(abcd.getDiscriminant()) ) /2* abcd.getA() )
                ).getX1()
                 
                 + "\n" +  
                    
        Roots.X2 + new Roots(
            ( ( -abcd.getB() - Math.sqrt(abcd.getDiscriminant()) ) /2* abcd.getA() ),
            ( ( -abcd.getB() + Math.sqrt(abcd.getDiscriminant()) ) /2* abcd.getA() )
                ).getX2() ;
        
        
        
         
            
            
