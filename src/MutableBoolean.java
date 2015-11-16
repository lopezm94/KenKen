public class MutableBoolean {

  private Boolean value;

  public MutableBoolean() {
    this.value = false;
  }

  public MutableBoolean(Boolean value) {
    this.value = value;
  }

  public void setAnd(Boolean value) {
    this.value = this.value && value;
  }

  public void setOr(Boolean value) {
    this.value = this.value || value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }

  public Boolean getValue() {
    return this.value;
  }

  public Boolean equals(Boolean value) {
    return this.value.equals(value);
  }

  public String toString() {
    return value.toString();
  }
}
