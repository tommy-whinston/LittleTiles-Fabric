package team.creative.littletiles.common.structure.signal;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import team.creative.creativecore.common.util.math.utils.IntegerUtils;

public abstract class SignalState {
    
    public static SignalState of(boolean value) {
        return value ? TRUE : FALSE;
    }
    
    public static SignalState of(int number) {
        return new IntegerState(number);
    }
    
    public static SignalState of(long number) {
        throw new UnsupportedOperationException();
    }
    
    public static SignalState create(int bandwidth) {
        if (bandwidth == 1)
            return FALSE;
        if (bandwidth <= 32)
            return new IntegerState(0);
        
        throw new UnsupportedOperationException();
    }
    
    public static SignalState loadFromTag(Tag tag) {
        if (tag instanceof NumericTag number) {
            if (tag instanceof LongTag)
                throw new UnsupportedOperationException();
            if (tag instanceof ByteTag)
                return of(number.getAsByte() != 0);
            return of(number.getAsInt());
        }
        return FALSE;
    }
    
    public static SignalState read(FriendlyByteBuf buffer) {
        int length = buffer.readInt();
        if (length == 0)
            return of(buffer.readBoolean());
        if (length == 1)
            return new IntegerState(buffer.readInt());
        
        throw new UnsupportedOperationException();
    }
    
    public static SignalState copy(SignalState state) {
        if (state instanceof PrimitveState)
            return state;
        if (state instanceof IntegerState iState)
            return new IntegerState(iState.value);
        throw new UnsupportedOperationException();
    }
    
    public static int getRequiredBandwidth(int number) {
        int digit = 0;
        while (number != 0) {
            digit++;
            number = number / 10;
        }
        return digit;
    }
    
    public static final SignalState TRUE = new PrimitveState(true);
    public static final SignalState FALSE = new PrimitveState(false);
    
    public abstract boolean equals(Object object);
    
    public abstract SignalState overwrite(SignalState state);
    
    public abstract SignalState set(int index, boolean value);
    
    public abstract SignalState setNumber(int value);
    
    public abstract SignalState setLongNumber(long value);
    
    public abstract SignalState fill(boolean value);
    
    public abstract SignalState fill(SignalState other);
    
    public abstract SignalState reset();
    
    public abstract boolean equals(int bandwidth, SignalState state);
    
    public abstract boolean any();
    
    public abstract SignalState or(SignalState other);
    
    public abstract boolean is(int index);
    
    public boolean is(int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] == 2)
                continue;
            if (indexes[i] != (is(i) ? 1 : 0))
                return false;
        }
        return true;
    }
    
    public abstract SignalState load(Tag tag);
    
    public abstract SignalState load(int number);
    
    public abstract Tag save();
    
    public abstract int number();
    
    public abstract long longNumber();
    
    public abstract String toString();
    
    public abstract String print(int length);
    
    public abstract void write(FriendlyByteBuf buffer);
    
    public abstract SignalState invert();
    
    public abstract SignalStateSize size();
    
    public abstract void shrinkTo(int bandwidth);
    
    protected static class PrimitveState extends SignalState {
        
        private final boolean value;
        
        private PrimitveState(boolean value) {
            this.value = value;
        }
        
        public boolean is(int index) {
            if (index == 0)
                return value;
            return false;
        }
        
        public String toString() {
            return "[" + value + "]";
        }
        
        public String print(int length) {
            return toString();
        }
        
        public SignalState overwrite(SignalState state) {
            return of(state.any());
        }
        
        public SignalState set(int index, boolean value) {
            if (index == 0)
                return of(value);
            return this;
        }
        
        public SignalState reset() {
            return FALSE;
        }
        
        public boolean equals(int bandwidth, SignalState state) {
            return value == state.any();
        }
        
        public boolean any() {
            return value;
        }
        
        public SignalState load(Tag tag) {
            if (tag instanceof NumericTag num)
                return num.getAsByte() == 0 ? FALSE : TRUE;
            else
                return FALSE;
        }
        
        public Tag save() {
            return ByteTag.valueOf(value);
        }
        
        public int number() {
            return value ? 1 : 0;
        }
        
        public void write(FriendlyByteBuf buffer) {
            buffer.writeInt(0);
            buffer.writeBoolean(value);
        }
        
        public SignalState setNumber(int value) {
            return of(value != 0);
        }
        
        public SignalState setLongNumber(long value) {
            return of(value != 0);
        }
        
        public SignalState fill(boolean value) {
            return of(value);
        }
        
        public SignalState fill(SignalState other) {
            return of(other.any());
        }
        
        public SignalState or(SignalState other) {
            return of(value || other.any());
        }
        
        public SignalState load(int number) {
            return of(number != 0);
        }
        
        public long longNumber() {
            return value ? 1 : 0;
        }
        
        public SignalState invert() {
            return of(!value);
        }
        
        public SignalStateSize size() {
            return SignalStateSize.SINGLE;
        }
        
        public void shrinkTo(int bandwidth) {}
        
        public boolean equals(Object object) {
            if (object instanceof PrimitveState)
                return this == object;
            else if (object instanceof IntegerState integer)
                return integer.number() == number();
            else if (object instanceof SignalState state)
                return state.longNumber() == longNumber();
            return false;
        }
        
    }
    
    protected static class IntegerState extends SignalState {
        
        private int value;
        
        public IntegerState(int value) {
            this.value = value;
        }
        
        public boolean is(int index) {
            if (index < 32)
                return IntegerUtils.bitIs(value, index);
            return false;
        }
        
        public String toString() {
            return "[" + value + "]";
        }
        
        public String print(int length) {
            String result = "[";
            for (int i = 0; i < length; i++)
                result += is(i) ? "1" : "0";
            return result + "]";
        }
        
        public SignalState load(Tag tag) {
            if (tag instanceof NumericTag num)
                value = num.getAsInt();
            else
                value = 0;
            return this;
        }
        
        public Tag save() {
            return IntTag.valueOf(value);
        }
        
        public SignalState overwrite(SignalState state) {
            this.value = state.number();
            return this;
        }
        
        public SignalState set(int index, boolean value) {
            this.value = IntegerUtils.set(this.value, index, value);
            return this;
        }
        
        public SignalState setNumber(int value) {
            this.value = value;
            return this;
        }
        
        public SignalState setLongNumber(long value) {
            this.value = (int) value;
            return this;
        }
        
        public SignalState fill(boolean value) {
            this.value = value ? -1 : 0;
            return this;
        }
        
        public SignalState fill(SignalState other) {
            this.value = other.number();
            return this;
        }
        
        public SignalState reset() {
            this.value = 0;
            return this;
        }
        
        public boolean equals(int bandwidth, SignalState state) {
            return shrinkTo(state.number(), bandwidth) == shrinkTo(value, bandwidth);
        }
        
        public boolean any() {
            return value != 0;
        }
        
        public SignalState or(SignalState other) {
            this.value |= other.number();
            return this;
        }
        
        public SignalState load(int number) {
            this.value = number;
            return this;
        }
        
        public int number() {
            return value;
        }
        
        public long longNumber() {
            return value;
        }
        
        public void write(FriendlyByteBuf buffer) {
            buffer.writeInt(1);
            buffer.writeInt(value);
        }
        
        public SignalState invert() {
            value = ~value;
            return this;
        }
        
        public SignalStateSize size() {
            return SignalStateSize.INT;
        }
        
        public void shrinkTo(int bandwidth) {
            value = shrinkTo(value, bandwidth);
        }
        
        public boolean equals(Object object) {
            if (object instanceof IntegerState integer)
                return this.value == integer.value;
            else if (object instanceof SignalState state)
                return state.longNumber() == longNumber();
            return false;
        }
        
        private static int shrinkTo(int number, int bandwidth) {
            bandwidth--;
            return number & (-1 >>> (31 - bandwidth));
        }
    }
    
    public static enum SignalStateSize {
        
        SINGLE(1) {
            public SignalState create() {
                return FALSE;
            }
        },
        INT(32) {
            public SignalState create() {
                return new IntegerState(0);
            }
        },
        LONG(64) {
            public SignalState create() {
                throw new UnsupportedOperationException();
            }
        };
        
        public final int bandwidth;
        
        private SignalStateSize(int bandwidth) {
            this.bandwidth = bandwidth;
        }
        
        public abstract SignalState create();
        
        public SignalStateSize max(SignalStateSize size) {
            if (ordinal() > size.ordinal())
                return this;
            return size;
        }
        
    }
    
}
