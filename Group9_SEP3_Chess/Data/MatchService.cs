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
    public class MatchService:IMatchService
    {
        private const string QUEUE_NAME = "rpc_queue";
        private readonly IConnection connection;
        private readonly IModel channel;
        private readonly string replyQueueName;
        private readonly EventingBasicConsumer consumer;
        private readonly ConcurrentDictionary<string, TaskCompletionSource<string>> callbackMapper =
            new ConcurrentDictionary<string, TaskCompletionSource<string>>();

        public MatchService()
        {
            var factory = new ConnectionFactory()
            {
                HostName = "localhost"
            };

            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            // declare a server-named queue
            replyQueueName = channel.QueueDeclare(queue: "").QueueName;
            consumer = new EventingBasicConsumer(channel);
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
        public async Task<ChessPiece> MoveChessPiece(Message message, CancellationToken cancellationToken = default(CancellationToken))
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
                routingKey: QUEUE_NAME,
                basicProperties: props,
                body: messageBytes);

            cancellationToken.Register(() => callbackMapper.TryRemove(correlationId, out var tmp));
            Message response = JsonSerializer.Deserialize<Message>(tcs.Task.Result);
            if (response.Action.Equals("Sending A chess Piece"))
            {
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Object);
                Console.WriteLine(response.Object);
                Console.WriteLine("Old position"+chessPiece.OldPosition.ToString());
                Console.WriteLine(chessPiece.Selected);
                Console.WriteLine(chessPiece.NewPosition.ToString());
                return chessPiece;
            }
            return null;
            
        }

        public async Task<ChessPiece> UpgradeChessPiece(Message message,CancellationToken cancellationToken = default(CancellationToken))
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
                routingKey: QUEUE_NAME,
                basicProperties: props,
                body: messageBytes);

            cancellationToken.Register(() => callbackMapper.TryRemove(correlationId, out var tmp));
            Message response = JsonSerializer.Deserialize<Message>(tcs.Task.Result);
            if (response.Action.Equals("Upgrade Chess Piece"))
            {
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Object);
                Console.WriteLine(response.Object);
                return chessPiece;
            }
            return null;
        }

        public async Task<ChessPiece[,]> LoadChessPieces(Message message, CancellationToken cancellationToken = default(CancellationToken))
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
                routingKey: QUEUE_NAME,
                basicProperties: props,
                body: messageBytes);

            cancellationToken.Register(() => callbackMapper.TryRemove(correlationId, out var tmp));
            Message response = JsonSerializer.Deserialize<Message>(tcs.Task.Result);
            if (response.Action.Equals("Load ChessBoard"))
            {
                Console.WriteLine(response.Object);
               ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Object);
                
                return chessPieces;
            }
            return null;
        }
        }
    }
