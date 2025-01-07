package org.learners.sqrequlation;


enum SetModeList
{
    ALL_EQ_ZERO(0), 
    ALL_NE_ZERO(1), 
    ONLY_FIRST_EQ_ZERO(2), 
    ONLY_FIRST_NE_ZERO(3),
    ONLY_SECOND_EQ_ZERO(4),
    ONLY_SECOND_NE_ZERO(5),
    ONLY_THIRD_EQ_ZERO(6),
    ONLY_THIRD_NE_ZERO(7);       
    
    private int index;
    private SetModeList(int index) { this.index = index; }
    public int getIndex() { return index; }
};