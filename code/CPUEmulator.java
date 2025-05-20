public class CPUEmulator {
    private int AC;
    private int PC;
    private int FLAGS;
    private boolean halted;

    // flag register, flag locations
    public static final int CF = 0;  // Carry
    public static final int PF = 2; // Parity
    public static final int ZF = 6;  // Zero
    public static final int SF = 7;  // Sign
    public static final int OF = 11; // Overflow


    public CPUEmulator() {
        this.AC = 0;
        this.PC = 0;
        this.FLAGS = 0;
        this.halted = false;
    }

    public int getAC() {
        return AC;
    }

    public void setAC(int value) {
        AC = value;
        setFlag(ZF, AC == 0);
        setFlag(SF, AC < 0);
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getFlags() {
        return FLAGS;
    }


    public boolean getFlag(int bitPosition) {
        return (FLAGS & (1 << bitPosition)) != 0;
    }

    public void setFLAGS(int FLAGS) {
        this.FLAGS = FLAGS;
    }
    public void setFlag(int bitPosition, boolean value) {
        if (value) {
            FLAGS |= (1 << bitPosition);
        } else {
            FLAGS &= ~(1 << bitPosition);
        }
    }
    public boolean isHalted() {
        return halted;
    }

    public void halt() {
        this.halted = true;
        System.out.println("CPU halted.");
    }
    public void updateFlags(int result, int op1, int op2, char operation) {
        int res = result & 0xFFFF;
        setFlag(ZF, res == 0);
        setFlag(SF, (res & 0x8000) != 0);
        setFlag(PF, Integer.bitCount(res & 0xFF) % 2 == 0);

        switch (operation) {
            case '+': {
                setFlag(CF, result > 0xFFFF);
                boolean overflow = (((op1 ^ result) & (op2 ^ result)) & 0x8000) != 0;
                setFlag(OF, overflow);
                break;
            }
            case '-': {
                setFlag(CF, op1 < op2);
                boolean overflow = (((op1 ^ op2) & (op1 ^ result)) & 0x8000) != 0;
                setFlag(OF, overflow);
                break;
            }
            case '*': {
                // 16-bit dışına çıktıysa CF = 1
                setFlag(CF, result > 0xFFFF);
                setFlag(OF, false);
                break;
            }
            default: {
                setFlag(CF, false);
                setFlag(OF, false);
                break;
            }
        }
    }

    public static void executeInstruction(int instruction, CPUEmulator cpu, Memory memory, Cache cache,int loadAddress) {

        String hexString = String.format("%04X", instruction);

        int opcode = (instruction >> 12) & 0xF;
        int operand = instruction & 0x0FFF;

        switch (opcode) {
            case 0x0:  // START
                break;
            case 0x1:  // LOAD
                cpu.setAC(operand);
                break;
            case 0x2:  // LOADM
                cpu.setAC(Byte.toUnsignedInt(cache.read(operand, memory)));
                break;
            case 0x3:  // STORE
                cache.write(operand, (byte) (cpu.getAC() & 0xFF), memory);
                break;
            case 0x4:  // CMPM
                int ac = cpu.getAC();
                int memVal = Byte.toUnsignedInt(cache.read(operand, memory));
                int result = memVal - ac;
                cpu.updateFlags(result, ac, memVal, '-');
                break;
            case 0x5:  // CJMP
                if (cpu.getFlag(SF)) {
                    cpu.setPC(loadAddress + operand * 2 - 2);
                }
                break;
            case 0x6:  // JMP
                cpu.setPC(loadAddress + operand * 2 - 2);
                break;
            case 0x7:  // ADD
                int addResult = cpu.getAC() + operand;
                cpu.setAC(addResult);
                cpu.updateFlags(addResult, cpu.getAC(), operand, '+');
                break;
            case 0x8:  // ADDM
                int addmOperand = Byte.toUnsignedInt(cache.read(operand, memory));
                int addmResult = cpu.getAC() + addmOperand;
                cpu.setAC(addmResult);
                cpu.updateFlags(addmResult, cpu.getAC(), addmOperand, '+');
                break;
            case 0x9:  // SUB
                int subResult = cpu.getAC() - operand;
                cpu.setAC(subResult);
                cpu.updateFlags(subResult, cpu.getAC(), operand, '-');
                break;
            case 0xA:  // SUBM
                int submOperand = Byte.toUnsignedInt(cache.read(operand, memory));
                int submResult = cpu.getAC() - submOperand;
                cpu.setAC(submResult);
                cpu.updateFlags(submResult, cpu.getAC(), submOperand, '-');
                break;
            case 0xB:  // MUL
                int mulResult = cpu.getAC() * operand;
                cpu.setAC(mulResult);
                cpu.updateFlags(mulResult, cpu.getAC(), operand, '*');
                break;
            case 0xC:  // MULM
                int mulmOperand = Byte.toUnsignedInt(cache.read(operand, memory));
                int mulmResult = cpu.getAC() * mulmOperand;
                cpu.setAC(mulmResult);
                cpu.updateFlags(mulmResult, cpu.getAC(), mulmOperand, '*');
                break;
            case 0xD:  // DISP
                System.out.println("AC: " + (cpu.getAC() & 0xFF));
                break;
            case 0xE:  // HALT
                cpu.halt();
                break;
        }
    }
}

