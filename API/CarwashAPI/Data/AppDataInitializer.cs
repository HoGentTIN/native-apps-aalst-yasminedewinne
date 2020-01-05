using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using CarwashAPI.Models.Domain;
using Microsoft.AspNetCore.Identity;

namespace CarwashAPI.Data
{
    public class AppDataInitializer
    {
        private readonly ApplicationDbContext _context;
        private readonly UserManager<User> _userManager;

        public AppDataInitializer(ApplicationDbContext context, UserManager<User> userManager)
        {
            _context = context;
            _userManager = userManager;
        }


        public async Task InitializeData(bool isDev)
        {
            if (isDev)
                _context.Database.EnsureDeleted();

            if (_context.Database.EnsureCreated())
            {
                var user1 = new User("Yasmine", "De Winne", "yasminedw@icloud.com", "+32400000000", new Adres("1", "Ninovestraat", "9450", "Haaltert", "België"));
                await _userManager.CreateAsync(user1, "UserWachtwoord");
                await _userManager.AddClaimAsync(user1, new Claim(ClaimTypes.Role, "User"));

                var user2 = new User("Vincent", "De Ridder", "vincent@mail.be", "+32400000000", new Adres("23", "teststraat", "5678", "Aalst", "België"));
                await _userManager.CreateAsync(user2, "UserWachtwoord");
                await _userManager.AddClaimAsync(user1, new Claim(ClaimTypes.Role, "User"));

                var carwash1 = new Carwash(user2.Id, DateTime.Today.AddDays(7), new TimeSpan(17, 00, 00), "14.50", "Tesla", "Wassen, stofzuigen en waxen");
                var carwash2 = new Carwash(user1.Id, DateTime.Today.AddDays(15), new TimeSpan(12, 00, 00), "20.50", "BMW", "Wassen en extra's");
                var carwash3 = new Carwash(user2.Id, DateTime.Today.AddDays(2), new TimeSpan(20, 00, 00), "17.50", "Volkswagen", "Wassen en stofzuigen");
                _context.Carwashes.AddRange(carwash1, carwash2, carwash3);
                _context.SaveChanges();

                carwash1.Aanbieder = user2;
                carwash2.Aanbieder = user1;
                carwash3.Aanbieder = user2;
                _context.SaveChanges();

                var afspraak1 = new Afspraak(user1.Id, carwash1.Id);
                var afspraak2 = new Afspraak(user2.Id, carwash2.Id);
                _context.Afspraken.AddRange(afspraak1, afspraak2);
                _context.SaveChanges();

                afspraak1.Gebruiker = user1;
                afspraak2.Gebruiker = user2;
                _context.SaveChanges();
            }
        }
    }
}
