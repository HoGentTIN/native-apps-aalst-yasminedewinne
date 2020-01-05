using System;
using System.ComponentModel.DataAnnotations;
using CarwashAPI.Models.Domain;

namespace CarwashAPI.DTO.Identity
{
    public class RegisterDTO
    {
        [Required]
        public string Voornaam { get; set; }

        [Required]
        public string Familienaam { get; set; }

        [Required]
        [EmailAddress]
        public string Email { get; set; }

        [Required]
        [Phone]
        public string TelefoonNr { get; set; }

        [Required]
        public Adres Adres { get; set; }

        [Required]
        [StringLength(maximumLength: 50, MinimumLength = 6)]
        public string Wachtwoord { get; set; }

        [Required]
        [Compare(nameof(Wachtwoord))]
        public string WachtwoordHerhaling { get; set; }
    }
}
