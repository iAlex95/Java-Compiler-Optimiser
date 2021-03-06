package comp207p.main.utils;

import comp207p.main.exceptions.UnableToFetchValueException;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.generic.*;

public class Utilities {
    /**
     * Print out constants for debugging
     * @param cp Constant Pool
     */
    public static void printConstants(ConstantPool cp) {
        Constant[] constants = cp.getConstantPool();
        int constantCount = 0;
        for(Constant c : constants) {
            if((c == null) || (c instanceof ConstantString) || (c instanceof ConstantUtf8)) continue; //ignore these constant types

            System.out.println(c);

            constantCount++;
        }

        System.out.format("Total constants: %d\n", constantCount);
    }

    public static void printInstructionHandles(InstructionHandle[] handles, ConstantPoolGen cpgen, InstructionList list, String type) {
        for(InstructionHandle h : handles) {
            if(h.getInstruction() instanceof LoadInstruction || h.getInstruction() instanceof LDC2_W) {
                try {
                    System.out.format("%s | Val: %s\n", h, ValueLoader.getValue(h, cpgen, list, type));
                } catch (UnableToFetchValueException e) {
                    System.out.format("%s | Val: Could not get\n", h);
                }
            } else {
                System.out.println(h);
            }
        }
    }

    /**
     * Fold an arithmetic operation and get the result
     * @param operation Arithmetic operation e.g. IADD, DMUL, etc.
     * @param left Left value of binary operator
     * @param right Right side of binary operator
     * @return Result of the calculation
     */
    public static Number foldOperation(ArithmeticInstruction operation, Number left, Number right) {
        if(operation instanceof IADD || operation instanceof LADD) {
            return left.longValue() + right.longValue();
        } else if(operation instanceof FADD ||  operation instanceof DADD) {
            return left.doubleValue() + right.doubleValue();
        } else if(operation instanceof ISUB || operation instanceof LSUB) {
            return left.longValue() - right.longValue();
        } else if(operation instanceof FSUB ||  operation instanceof DSUB) {
            return left.doubleValue() - right.doubleValue();
        } else if(operation instanceof IMUL || operation instanceof LMUL){
            return left.longValue() * right.longValue();
        } else if(operation instanceof FMUL ||  operation instanceof DMUL) {
            return left.doubleValue() * right.doubleValue();
        } else if(operation instanceof IDIV || operation instanceof LDIV){
            return left.longValue() / right.longValue();
        } else if(operation instanceof FDIV ||  operation instanceof DDIV) {
            return left.doubleValue() / right.doubleValue();
        } else if(operation instanceof IREM || operation instanceof LREM){
            return left.longValue() % right.longValue();
        } else if(operation instanceof FREM ||  operation instanceof DREM) {
            return left.doubleValue() % right.doubleValue();
        //Bit operations
        } else if(operation instanceof IAND || operation instanceof  LAND){
            return left.longValue() & right.longValue();
        } else if(operation instanceof IOR || operation instanceof  LOR){
            return left.longValue() | right.longValue();
        } else if(operation instanceof IXOR || operation instanceof LXOR){
            return left.longValue() ^ right.longValue();
        } else if(operation instanceof ISHL || operation instanceof LSHL){
            return left.longValue() << right.longValue();
        } else if(operation instanceof ISHR || operation instanceof LSHR){
            return left.longValue() >> right.longValue();
        } else {
            throw new RuntimeException("Not supported operation");
        }
    }

    public static void printDynamicVariableDetected() {
        System.out.println("Possible Dynamic Variable detected. No folding will occur.");
        System.out.println("==================================");
    }
}
