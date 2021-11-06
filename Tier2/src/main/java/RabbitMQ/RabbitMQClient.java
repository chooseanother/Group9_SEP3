package RabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitMQClient {
    void initRPCQueue() throws IOException, TimeoutException;
}
