using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using CarwashAPI.Models.Domain;

namespace CarwashAPI.DTO
{
    public class CarwashDTO
    {
        public int Id { get; set; }

        [Required]
        public int AanbiederId { get; set; }

        [Required]
        public string Auto { get; set; }

        [Required]
        public string Tarief { get; set; }

        public string TakenUitleg { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime Datum { get; set; }

        [Required]
        [DataType(DataType.Time)]
        public TimeSpan BeginUur { get; set; }

        public void UpdateFromModel(Carwash carwash)
        {
            carwash.AanbiederId = this.AanbiederId;
            carwash.Auto = this.Auto;
            carwash.Tarief = this.Tarief;
            carwash.TakenUitleg = this.TakenUitleg;
            carwash.Datum = this.Datum;
            carwash.BeginUur = this.BeginUur;
        }
    }
}
