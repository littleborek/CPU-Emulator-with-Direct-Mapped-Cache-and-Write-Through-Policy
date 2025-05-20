public class Cache {
    private final byte[][] cache = new byte[8][2]; // 8 blocks, each 2 bytes
    private final boolean[] valid = new boolean[8];
    private final int[] tag = new int[8];
    private int cacheHits = 0;
    private int cacheMisses = 0;

    public double getCacheHitRatio() {
        int total = cacheHits + cacheMisses;
        return total == 0 ? 0 : ((double) cacheHits / total) * 100;
    }

    public byte read(int address, Memory memory) {
        int blockIndex = (address / 2) % 8;
        int blockTag = address / 16;

        int blockStart = address & 0xFFFE;

        if (valid[blockIndex] && tag[blockIndex] == blockTag) {
            cacheHits++;
        } else {
            cacheMisses++;
            cache[blockIndex][0] = memory.read(blockStart);
            cache[blockIndex][1] = memory.read(blockStart + 1);
            tag[blockIndex] = blockTag;
            valid[blockIndex] = true;
        }

        return cache[blockIndex][address % 2];
    }

    public void write(int address, byte data, Memory memory) {
        int blockIndex = (address / 2) % 8;
        int blockTag = address / 16;

        int blockStart = address & 0xFFFE;

        if (valid[blockIndex] && tag[blockIndex] == blockTag) {
            cacheHits++;
        } else {
            cacheMisses++;
            cache[blockIndex][0] = memory.read(blockStart);
            cache[blockIndex][1] = memory.read(blockStart + 1);
            tag[blockIndex] = blockTag;
            valid[blockIndex] = true;
        }

        cache[blockIndex][address % 2] = data;
        memory.write(address, data); // write-through
    }
}
