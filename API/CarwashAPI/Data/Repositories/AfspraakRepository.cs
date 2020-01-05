using System;
using System.Collections.Generic;
using System.Linq;
using CarwashAPI.Models.Domain;
using CarwashAPI.Models.Domain.Repositories;
using Microsoft.EntityFrameworkCore;

namespace CarwashAPI.Data.Repositories
{
    public class AfspraakRepository : IAfspraakRepository
    {
        private readonly DbSet<Afspraak> _afspraken;
        private readonly ApplicationDbContext _context;

        public AfspraakRepository(ApplicationDbContext context)
        {
            this._context = context;
            this._afspraken = context.Afspraken;
        }

        public void Add(Afspraak item)
        {
            _afspraken.Add(item);
        }

        public IEnumerable<Afspraak> GetAll()
        {
            return _afspraken.AsNoTracking().Include(x => x.Carwash).Include(x => x.Gebruiker).ToList();
        }

        public Afspraak GetById(int id)
        {
            return _afspraken.Include(x => x.Carwash).Include(x => x.Gebruiker).SingleOrDefault(x => x.Id == id);
        }

        public IEnumerable<Afspraak> GetByUserId(int gebruikerId)
        {
            return _afspraken.AsNoTracking().Include(x => x.Carwash).Include(x => x.Gebruiker).Where(x => x.GebruikerId == gebruikerId).ToList();
        }

        public void Remove(Afspraak item)
        {
            _afspraken.Remove(item);
        }

        public void SaveChanges()
        {
            _context.SaveChanges();
        }
    }
}
