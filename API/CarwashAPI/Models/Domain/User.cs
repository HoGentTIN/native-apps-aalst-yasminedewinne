using System;
using Microsoft.AspNetCore.Identity;

namespace CarwashAPI.Models.Domain
{
    public class User : IdentityUser<int>
    {
        public string Voornaam { get; set; }
        public string Familienaam { get; set; }
        public Adres Adres { get; set; }

        public User() { }
        public User(string firstName, string lastName, string email, string telefoonNr, Adres adres)
        {
            this.Voornaam = firstName;
            this.Familienaam = lastName;
            this.UserName = email; // Ander krijg je een error: Invalid
            this.Email = email;
            this.PhoneNumber = telefoonNr;
            this.Adres = adres;

            this.SecurityStamp = Guid.NewGuid().ToString();
        }
    }

    public class Adres
    {
        //public int Id { get; set; }
        public string HuisNr { get; set; }

        public string StraatNaam { get; set; }

        public string Postcode { get; set; }

        public string Stad { get; set; }

        public string Land { get; set; }

        public Adres() { }

        public Adres(string huisNr, string straatNaam, string postcode, string stad, string land)
        {
            this.HuisNr = huisNr;
            this.StraatNaam = straatNaam;
            this.Postcode = postcode;
            this.Stad = stad;
            this.Land = land;
        }
    }
}
