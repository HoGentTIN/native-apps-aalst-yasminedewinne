using System;
using System.Collections.Generic;

namespace CarwashAPI.Models.Domain.Repositories
{
    public interface ICarwashRepository : IBaseRepository<Carwash>
    {
        IEnumerable<Carwash> GetCarwashesByStad(string stad);
    }
}
