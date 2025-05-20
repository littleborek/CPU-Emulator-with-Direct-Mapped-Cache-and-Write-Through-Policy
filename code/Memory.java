import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Memory {
    byte[] memory;

    public Memory() {
        memory = new byte[65536];
    }

    public void loadProgram(String programPath, int startAddress) {

        try {

            Scanner scanner = new Scanner(new File(programPath));
            int currentAddress = startAddress;


            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;


                int instruction = Integer.parseInt(line, 2);

                byte lowByte = (byte) instruction;
                byte highByte = (byte) (instruction >> 8);

                memory[currentAddress] = lowByte;
                memory[currentAddress + 1] = highByte;

                System.out.println("Loaded instruction: " + Integer.toHexString(instruction) + " at address " + currentAddress); // for debug

                currentAddress += 2;

            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");;
        }
    }
    private boolean checkAddressRange(int address){
        if (address < 0 || address >= 65536) {
            System.out.println("Not a valid address: "+address);
            return false;
        }
        return true;
    }

    public byte read(int address) {
        if (checkAddressRange(address)){
            return memory[address];
        }
        return 0;
    }
    public void write(int address, byte data) {
        if (checkAddressRange(address)){
            memory[address] = data;
        }
    }
    public int readWord(int address) {
        if (checkAddressRange(address)) {
            int low = Byte.toUnsignedInt(memory[address]);
            int high = Byte.toUnsignedInt(memory[address + 1]);
            return (high << 8) | low;
        }
        return 0;
    }
    public void writeWord(int address, int data) {
        if (checkAddressRange(address)) {
            memory[address] = (byte) (data & 0xFF);
            memory[address + 1] = (byte) (data >> 8);
        }
    }
}
