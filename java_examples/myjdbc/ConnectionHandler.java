import java.sql.Connection; 


@FunctionalInterface
public interface ConnectionHandler {
    void handleConnection(Connection connection);
}
