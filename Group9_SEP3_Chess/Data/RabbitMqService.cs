using System;
using System.Collections.Concurrent;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace Group9_SEP3_Chess.Data
{
    public class RabbitMqService : IRabbitMq
    {
        private const string QueueName = "rpc_queue";

        private readonly IConnection connection;
        private readonly IModel channel;
        private readonly string replyQueueName;

        private readonly ConcurrentDictionary<string, TaskCompletionSource<string>> callbackMapper =
            new ConcurrentDictionary<string, TaskCompletionSource<string>>();

        public RabbitMqService()
        {
            var factory = new ConnectionFactory()
            {
                HostName = "localhost"
            };

            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            // declare a server-named queue
            replyQueueName = channel.QueueDeclare(queue: "").QueueName;
            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, ea) =>
            {
                if (!callbackMapper.TryRemove(ea.BasicProperties.CorrelationId, out TaskCompletionSource<string> tcs))
                    return;
                var body = ea.Body.ToArray();
                var response = Encoding.UTF8.GetString(body);
                tcs.TrySetResult(response);
            };

            channel.BasicConsume(
                consumer: consumer,
                queue: replyQueueName,
                autoAck: true);
        }


        public async Task<Message> SendRequestAsync(Message message, CancellationToken cancellationToken = default(CancellationToken))
        {
            IBasicProperties props = channel.CreateBasicProperties();
            var correlationId = Guid.NewGuid().ToString();
            props.CorrelationId = correlationId;
            props.ReplyTo = replyQueueName;
            var jsonMessage = JsonSerializer.Serialize(message); 
            var messageBytes = Encoding.UTF8.GetBytes(jsonMessage);
            var tcs = new TaskCompletionSource<string>();
            callbackMapper.TryAdd(correlationId, tcs);

            channel.BasicPublish(
                exchange: "",
                routingKey: QueueName,
                basicProperties: props,
                body: messageBytes);

            cancellationToken.Register(() => callbackMapper.TryRemove(correlationId, out var tmp));
            return JsonSerializer.Deserialize<Message>(tcs.Task.Result);
        }

        public void Close()
        {
            connection.Close();
        }
    }
}