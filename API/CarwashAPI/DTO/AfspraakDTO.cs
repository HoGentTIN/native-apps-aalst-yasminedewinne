using System;
using System.ComponentModel.DataAnnotations;
using CarwashAPI.Models.Domain;

namespace CarwashAPI.DTO
{
    public class AfspraakDTO
    {
        [Required]
        public int Id { get; set; }

        [Required]
        public int GebruikerId { get; set; }

        [Required]
        public int CarwashId { get; set; }

        internal void UpdateFromModel(Afspraak afspraak)
        {
            afspraak.GebruikerId = this.GebruikerId;
            afspraak.CarwashId = this.CarwashId;
        }
    }
}
