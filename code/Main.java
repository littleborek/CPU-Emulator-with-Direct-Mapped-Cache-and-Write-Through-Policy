import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String programPath = "program.txt";
        String configPath = "config.txt";

        Scanner scanner;
        try {
            scanner = new Scanner(new File(configPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int loadAddress = Integer.decode(scanner.nextLine().trim());
        int initialPC = Integer.decode(scanner.nextLine().trim());
        scanner.close();

        Memory memory = new Memory();
        Cache cache = new Cache();
        CPUEmulator cpu = new CPUEmulator();

        memory.loadProgram(programPath, loadAddress);
        cpu.setPC(initialPC);

        while (cpu.getPC() < 65536 && !cpu.isHalted()) {
            int low = Byte.toUnsignedInt(memory.read(cpu.getPC()));
            int high = Byte.toUnsignedInt(memory.read(cpu.getPC() + 1));
            int instruction = (high << 8) | low;
            System.out.println("Executing instruction: "+Integer.toHexString(instruction));
            CPUEmulator.executeInstruction(instruction, cpu, memory, cache,loadAddress);
            cpu.setPC(cpu.getPC() + 2);
        }

        System.out.println("AC: " + (cpu.getAC()& 0xFF));
        System.out.printf("Cache Hit Ratio: %.2f%%%n", cache.getCacheHitRatio());


    }
}