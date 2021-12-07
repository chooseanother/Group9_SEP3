using System;
using System.Threading;
using System.Threading.Tasks;
using Group9_SEP3_Chess.Models;

namespace Group9_SEP3_Chess.Data
{
    public interface IRabbitMqService
    {
        Task<Message> SendRequestAsync(Message message, CancellationToken cancellationToken = default(CancellationToken));
        void Close();
    }
}