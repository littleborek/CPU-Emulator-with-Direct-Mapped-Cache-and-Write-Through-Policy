Name: Berk Kocabörek

University ID: 20230808607

Department: Computer Engineering, Akdeniz University

Academic Year: 2nd Year


# CPU Emulator with Direct Mapped Cache and Write-Through Policy

This project simulates a simplified CPU architecture featuring direct-mapped cache memory using a write-through policy. The emulator is designed to execute binary machine instructions loaded into memory and supports a custom instruction set architecture (ISA).

---

## 🧠 Project Scope

- **University**: Akdeniz University  
- **Department**: Faculty of Engineering, Department of Computer Engineering  
- **Course**: Computer Organization  
- **Language**: Java (Version 23.0.2)  
- **Author**: Berk Kocabörek  
- **Student ID**: 20230808607  
- **Year of Study**: 2nd Year  

---

## 🛠️ Features

- 16-entry direct-mapped cache with valid bits and tag comparisons
- Write-through cache policy (every write updates main memory)
- Instruction set includes basic operations: LOAD, STORE, ADD, JMP, HALT, etc.
- Status flags: Zero (ZF), Sign (SF), Carry (CF), Overflow (OF), Parity (PF)
- Reads binary machine code from `program.txt` and executes sequentially
- Configuration file `config.txt` determines load address and initial program counter

---

## 📁 File Structure

├── Main.java // Program entry
├── CPUEmulator.java // Core CPU logic and instruction set
├── Memory.java // 64KB memory model with read/write/word ops
├── Cache.java // Direct-mapped cache implementation
├── program.txt // Machine code in binary format
├── config.txt // Load address and initial PC
