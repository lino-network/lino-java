package network.lino.transport;

public class RpcException extends Exception {
  private static final long serialVersionUID = 1L;
  public RpcException (String message) {
    super(message);
  }
}
