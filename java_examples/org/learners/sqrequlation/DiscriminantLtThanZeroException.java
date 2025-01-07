package org.learners.sqrequlation;


class DiscriminantLtThanZeroException extends SqrEqualerException
{   
    public String toString()
    {
        return "На множестве действительных чисел нет корней!";
    }
}