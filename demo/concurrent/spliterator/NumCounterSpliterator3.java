package org.demo.concurrent.spliterator;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class NumCounterSpliterator3 implements Spliterator<Character> {

    private char[] str;
    private int currentChar = 0;
    private int end = Integer.MAX_VALUE;
    private boolean canSplit = true;

    public NumCounterSpliterator3(int currentChar,int end,char[] str,boolean canSplit) {
        this.str = str;
        this.currentChar = currentChar;
        this.canSplit = canSplit;
        this.end = end;
    }
    



    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept( str[currentChar++] );
        return currentChar <= end;
    }
    
   

    /* 
     * 此处只分割一次任务，所以只会产生一个worker执行任务
     */
    @Override
    public Spliterator<Character> trySplit() {
        int i = currentChar;
        int currentCharOld = currentChar;
        for(;canSplit && i <= end; ++i){
            if(!Character.isDigit(str[i])){
                int splitBeforeEnd = end;
                canSplit = false;
                if(i + 1 <= splitBeforeEnd){
                    currentChar = i + 1;
                    return new NumCounterSpliterator3(currentCharOld, i, str, true);
                }
            }
        }
        
        canSplit = false;
        return null;
    }

    @Override
    public long estimateSize() {
        return end - currentChar + 1 /*Long.MAX_VALUE*/ ;
    }

    public Comparator<? super Character> getComparator() {
        return null;
    }
    
    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE /*|SORTED*/;
    }

}



