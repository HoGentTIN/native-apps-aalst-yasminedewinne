using System;
using System.Collections.Generic;

namespace CarwashAPI.Models.Domain
{
    public class Carwash
    {
        public int Id { get; set; }

        public int AanbiederId { get; set; }
        public User Aanbieder { get; set; }

        public string Auto { get; set; }
        public string Tarief { get; set; }
        public string TakenUitleg { get; set; }
        public DateTime Datum { get; set; }
        public TimeSpan BeginUur { get; set; }

        public Carwash(){}
        public Carwash(int aanbiederId, DateTime datum, TimeSpan beginUur,string tarief, string auto, string takenUitleg)
        {
            this.AanbiederId = aanbiederId;
            this.Datum = datum;
            this.BeginUur = beginUur;
            this.Tarief = tarief;
            this.Auto = auto;
            this.TakenUitleg = takenUitleg;
        }
    }
}
