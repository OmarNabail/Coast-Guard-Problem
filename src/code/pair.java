package code;

import java.util.Objects;

public class pair<firstValue,secondValue> {
    private  int firstValue;
    private  int secondValue;

    public pair(int firstValue, int secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    public void setFirstValue(int firstValue) {
        this.firstValue = firstValue;
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        pair<?, ?> pair = (pair<?, ?>) o;
        return firstValue == pair.firstValue && secondValue == pair.secondValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstValue, secondValue);
    }

    public int getFirstValue() {
        return this.firstValue;
    }

    public int getSecondValue() {
        return this.secondValue;
    }

    public String ToString(){
        return "("+getFirstValue()+","+getSecondValue()+")";
    }

}
