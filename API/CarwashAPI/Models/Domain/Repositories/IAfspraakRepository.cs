using System;
using System.Collections.Generic;

namespace CarwashAPI.Models.Domain.Repositories
{
    public interface IAfspraakRepository : IBaseRepository<Afspraak>
    {
        IEnumerable<Afspraak> GetByUserId(int gebruikerId);
    }
}
