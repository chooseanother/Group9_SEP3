using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Json2DArrayHelp;
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

        private List<ChessPiece> removedChessPieces;
        private String MatchScores;

        public MatchService()
        {
            removedChessPieces = new List<ChessPiece>();
            
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
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
                Console.WriteLine(response.Data);
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
                ChessPiece chessPiece = JsonSerializer.Deserialize<ChessPiece>(response.Data);
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
                removedChessPieces = JsonSerializer.Deserialize<List<ChessPiece>>(response.DataSlot2);
                MatchScores = response.DataSlot3;
                Console.WriteLine(response.Data);
               ChessPiece[,] chessPieces = JsonSerializer.Deserialize<ChessPiece[,]>(response.Data, new JsonSerializerOptions
               {
                   Converters = { new Array2DConverter() },
               });
                
                return chessPieces;
            }
            return null;
        }

        public IList<ChessPiece> getRemovedChessPieces()
        {
            List<ChessPiece> chessPieces = new List<ChessPiece>(removedChessPieces);
            return chessPieces;
        }

        public string getMatchScores(bool Black)
        {
            string[] words = MatchScores.Split(" ");
            if (Black)
            {
                return words[0];
            }
            else
            {
                return words[1];
            }
            
        }
    }
    }
