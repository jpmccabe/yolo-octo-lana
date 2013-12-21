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
    
    
    
    public Operation getOperation(int index){
        return operations.get(index);
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
    
    
    
    /**
     * Runs the program, returning the output string.
     * @param timeOutLength
     * @return
     */
    public String ProgramRunner(int timeOutLength){
        assert(bracketsMatch());
        
        int pointer = 0;
        int numberOfInstructions = 0;
        String output = "";
        List<Integer> data = new ArrayList<Integer>();
        
        for(int i=0; i < getLength(); i++){
            Operation currentOperation = getOperation(i);
            
            if(currentOperation == Operation.INCREMENT_POINTER) pointer++;
            else if(currentOperation == Operation.DECREMENT_POINTER) pointer--;
            else if(currentOperation == Operation.INCREMENT_DATA){
                if(data.get(pointer) != 255){
                    data.add(pointer, data.get(pointer)+1);
                } else{
                    data.add(pointer, 0);
                }
            }
            else if(currentOperation == Operation.DECREMENT_DATA){
                if(data.get(pointer) != 0){
                    data.add(pointer, data.get(pointer)-1);
                } else{
                    data.add(pointer,255);
                }
            }
            else if(currentOperation == Operation.LOOP_BEGIN){
                i = (data.get(pointer) == 0) ? findLoopEnd(i) : i;
            }
            else if(currentOperation == Operation.LOOP_END){
                i = (data.get(pointer) != 0) ? findLoopStart(i) : i;
            }
            else if(currentOperation == Operation.PRINT){
                output += (char)(int)(data.get(pointer));
            }
            
            pointer = (pointer < 0) ? (pointer + data.size()) : pointer; // wrap pointer if negative
            
            if(pointer >= data.size()){ // increase the size of the list if the pointer is too large
                data.add(0);
            }
            
            if(numberOfInstructions > timeOutLength){
                return "";
            }      
        }
       
        return output;
    }

}
