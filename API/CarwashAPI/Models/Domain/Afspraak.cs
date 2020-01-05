using System;
namespace CarwashAPI.Models.Domain
{
    public class Afspraak
    {
        public int Id { get; set; }

        public int GebruikerId { get; set; }
        public User Gebruiker { get; set; }
        public int CarwashId { get; set; }
        public Carwash Carwash { get; set; }

        public Afspraak(){}
        public Afspraak(int userId, int carwashId)
        {
            this.GebruikerId = userId;
            this.CarwashId = carwashId;
        }
    }
}
