
## Project Title: CPU Emulator with Direct Mapped Cache and Write-Through Policy

---

## 1. Overview

This project implements a CPU emulator with an integrated memory subsystem (RAM + Cache). A program file (`program.txt`) contains binary-formatted machine instructions. Initial addresses are read from `config.txt`. The CPU sequentially fetches and executes instructions from memory. A direct-mapped cache with a write-through policy enhances memory access performance.

---

## 2. Class Structure

### `Main.java`
- Entry point of the program.
- Reads `loadAddress` and `initialPC` from `config.txt`.
- Initializes instances of `Memory`, `Cache`, and `CPUEmulator`.
- Loads the program into memory.
- Loops through instructions and executes them until halt.

### `Memory.java`
- Simulates 64KB (65536 bytes) of RAM.
- `loadProgram()` loads binary machine instructions into memory.
- Provides byte-level and word-level read/write operations.
- Validates memory addresses during access.

### `Cache.java`
- Implements a 16 bytes total (8 blocks×2 bytes) direct-mapped cache.
- Each block holds 1 byte, a tag, and a valid bit.
- `read()` and `write()` access data with cache logic.
- Implements a write-through policy (writes are passed to memory).
- Tracks hit/miss ratios.

### `CPUEmulator.java`
- Simulates a basic CPU with an Accumulator (AC), Program Counter (PC), FLAGS register, and halted state.
- Supports an instruction set architecture (ISA).
- Updates flag registers: Zero (ZF), Sign (SF), Carry (CF), Overflow (OF), and Parity (PF).
- `executeInstruction()` interprets and executes instructions.

---

## 3. Instruction Set

| Opcode | Instruction | Description |
|--------|-------------|-------------|
| 0x0    | START       | No operation |
| 0x1    | LOAD        | AC ← operand |
| 0x2    | LOADM       | AC ← [operand] via cache |
| 0x3    | STORE       | [operand] ← AC |
| 0x4    | CMPM        | Compares memory to AC, updates flags |
| 0x5    | CJMP        | Conditional jump if SF is set |
| 0x6    | JMP         | Unconditional jump |
| 0x7    | ADD         | AC ← AC + operand |
| 0x8    | ADDM        | AC ← AC + [operand] |
| 0x9    | SUB         | AC ← AC - operand |
| 0xA    | SUBM        | AC ← AC - [operand] |
| 0xB    | MUL         | AC ← AC * operand |
| 0xC    | MULM        | AC ← AC * [operand] |
| 0xD    | DISP        | Displays AC value |
| 0xE    | HALT        | Halts CPU execution |

---

## 4. File Structure

- `Main.java`: Main class, control flow
- `CPUEmulator.java`: CPU logic and instruction execution
- `Memory.java`: 64KB memory model with validation
- `Cache.java`: Direct-mapped cache implementation
- `program.txt`: Program binary input
- `config.txt`: Load address and initial PC

---
"""


In addition to the project files, I have included a test program (in txt format), and the results are provided.
Test Program Outputs:
    • program.txt outputs: 
      CPU halted.
      AC: 210
      Cache Hit Ratio: 97,93%

    • program_2.txt outputs:
      CPU halted.
      AC: 20
      Cache Hit Ratio: 50,00%
      