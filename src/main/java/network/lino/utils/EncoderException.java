package network.lino.utils;

public class EncoderException extends Exception {
  private static final long serialVersionUID = 1L;

  public EncoderException() {
    super();
  }

  public EncoderException (String message) {
    super(message);
  }

  public static class InvalidCharacter extends EncoderException {
    public final char character;
    public final int position;

    public InvalidCharacter(char character, int position) {
      super("Invalid character '" + Character.toString(character) + "' at position " + position);
      this.character = character;
      this.position = position;
    }
  }

  public static class InvalidDataLength extends EncoderException {
    public InvalidDataLength() {
      super();
    }

    public InvalidDataLength(String message) {
      super(message);
    }
  }

  public static class InvalidChecksum extends EncoderException {
    public InvalidChecksum() {
      super("Checksum does not validate");
    }

    public InvalidChecksum(String message) {
      super(message);
    }
  }

  public static class InvalidPrefix extends EncoderException {
    public InvalidPrefix() {
      super();
    }

    public InvalidPrefix(String message) {
      super(message);
    }
  }
}
