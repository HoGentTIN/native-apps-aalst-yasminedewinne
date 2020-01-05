using System;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity.UI.Services;

namespace CarwashAPI.Services
{
    public class EmailSender : IEmailSender
    {

        private readonly string host;
        private readonly int port;
        private readonly bool enableSSL;
        private readonly string userName;
        private readonly string password;

        // Wordt geladen uit application config
        public EmailSender(string host, int port, bool enableSSL, string userName, string password)
        {
            this.host = host;
            this.port = port;
            this.enableSSL = enableSSL;
            this.userName = userName;
            this.password = password;
        }

        public Task SendEmailAsync(string email, string subject, string htmlMessage)
        {
            var client = new SmtpClient(host, port)
            {
                Credentials = new NetworkCredential(userName, password),
                EnableSsl = enableSSL,
                Timeout = 30000
            };

            MailMessage mail = new MailMessage("<Sender>", email, subject, htmlMessage) { IsBodyHtml = true };
            return client.SendMailAsync(mail);
        }
    }
}
