using System;
using System.Collections.Generic;
using System.Linq;
using CarwashAPI.Models.Domain;
using CarwashAPI.Models.Domain.Repositories;
using Microsoft.EntityFrameworkCore;

namespace CarwashAPI.Data.Repositories
{
    public class CarwashRepository : ICarwashRepository
    {
        private readonly DbSet<Carwash> _carwashes;
        private readonly ApplicationDbContext _context;

        public CarwashRepository(ApplicationDbContext context)
        {
            this._context = context;
            this._carwashes = context.Carwashes;
        }

        public void Add(Carwash item)
        {
            _carwashes.Add(item);
        }

        public IEnumerable<Carwash> GetAll()
        {
            return _carwashes.AsNoTracking().Include(x=>x.Aanbieder).ToList();
        }

        public Carwash GetById(int id)
        {
            return _carwashes.Include(x=>x.Aanbieder).SingleOrDefault(x => x.Id == id);
        }

        public IEnumerable<Carwash> GetCarwashesByStad(string stad)
        {
            return _carwashes.AsNoTracking().Include(x => x.Aanbieder).Where(x => x.Aanbieder.Adres.Stad.ToLower() == stad.ToLower()).ToList();
        }

        public void Remove(Carwash item)
        {
            _carwashes.Remove(item);
        }

        public void SaveChanges()
        {
            _context.SaveChanges();
        }
    }
}
