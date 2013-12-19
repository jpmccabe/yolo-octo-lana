package model;
import java.util.*;

public class Program {
    private final List<Operation> operations;
    
    
    /**
     * Constructs a new instance of Program given a list of operations
     * the program should represent.
     * @param operations operations of the program
     */
    public Program(List<Operation> operations){
        this.operations = new ArrayList<Operation>(operations);
    }
    
    
    
    /**
     * Returns the number of operations in the program.
     * @return the number of operations
     */
    public int getLength(){
        return operations.size();
    }
    
    
    
    /**
     * Checks that the brackets properly match.
     * (i.e. the loops are properly formed)
     * @return true if the brackets are properly matched,
     *         false otherwise.
     */
    public boolean bracketsMatch(){
        int bracketCount = 0;
        
        for(Operation operation : operations){
            if(operation == Operation.LOOP_BEGIN){
                bracketCount++;
            } else if(operation == Operation.LOOP_END){
                bracketCount--;
            }
            if(bracketCount < 0){
                return false;
            }
        }
        
        return (bracketCount == 0) ? true : false;
    }
    
    
    
    /**
     * Returns the list of operations this program represents.
     * @return the operations of the program
     */
    public List<Operation> getOperations(){
        return new ArrayList<Operation>(operations);
    }
    
    
    
    /**
     * Returns the index of the LOOP_END that corresponds
     * to the LOOP_BEGIN at the given index. Requires that 
     * this.bracketsMatch() returns true.
     * @param indexOfLoopStart index of the Operation.LOOP_BEGIN that
     *        we should find the corresponding LOOP_END for.
     * @return the index of the corresponding LOOP_END
     */
    public int findLoopEnd(int indexOfLoopStart){
        assert(bracketsMatch());
        assert(operations.get(indexOfLoopStart) == Operation.LOOP_BEGIN);
        
        int bracketCount = 1;
        int numberOfOperationsAway = 1;
        
        for(int i = indexOfLoopStart+1; i < getLength(); i++){
            Operation currentOperation = operations.get(i);
            
            if(currentOperation == Operation.LOOP_BEGIN){
                bracketCount++;
            } else if(currentOperation == Operation.LOOP_END){
                bracketCount--;
            }
            
            if(bracketCount == 0){
                return numberOfOperationsAway + indexOfLoopStart;
            }
            
            numberOfOperationsAway++;
        }
        
        return 0;
    }
    
    
    
    /**
     * Returns the index of the LOOP_BEGIN that corresponds
     * to the LOOP_END at the given index. Requires that 
     * this.bracketsMatch() returns true.
     * @param indexOfLoopStart index of the Operation.LOOP_END that
     *        we should find the corresponding LOOP_BEGIN for.
     * @return the index of the corresponding LOOP_BEGIN
     */
    public int findLoopStart(int indexOfLoopEnd){
        assert(bracketsMatch());
        assert(operations.get(indexOfLoopEnd) == Operation.LOOP_END);
        
        int bracketCount = -1;
        int numberOfOperationsAway = -1;
        
        for(int i = indexOfLoopEnd-1; i >= 0; i--){
            Operation currentOperation = operations.get(i);
            
            if(currentOperation == Operation.LOOP_BEGIN){
                bracketCount++;
            } else if(currentOperation == Operation.LOOP_END){
                bracketCount--;
            }
            
            if(bracketCount == 0){
                return numberOfOperationsAway + indexOfLoopEnd;
            }
        }
        
        return 0;
    }

}
