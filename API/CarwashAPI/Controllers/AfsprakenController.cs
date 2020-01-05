using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using CarwashAPI.DTO;
using CarwashAPI.Models.Domain;
using CarwashAPI.Models.Domain.Repositories;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace CarwashAPI.Controllers
{
    [ApiConventionType(typeof(DefaultApiConventions))]
    [Produces("application/json")]
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class AfsprakenController : ControllerBase
    {
        private readonly IAfspraakRepository _afspraakRepo;
        private readonly ICarwashRepository _carwashRepo;
        private readonly UserManager<User> _userManager;

        public AfsprakenController(IAfspraakRepository afspraakRepository, ICarwashRepository carwashRepository, UserManager<User> userManager)
        {
            this._afspraakRepo = afspraakRepository;
            this._carwashRepo = carwashRepository;
            this._userManager = userManager;
        }

        [HttpGet]
        public IEnumerable<Afspraak> GetAll()
        {
            return _afspraakRepo.GetAll();
        }

        [HttpGet("{id}")]
        public ActionResult<Afspraak> GetById(int id)
        {
            Afspraak afspraak = _afspraakRepo.GetById(id);
            if (afspraak == null)
                return NotFound();

            return afspraak;
        }

        [HttpGet]
        [Route("gebruiker/{id}")]
        public async Task<ActionResult<IEnumerable<Afspraak>>> GetAfsprakenByGebruikerAsync(int id)
        {
            return new ObjectResult(this._afspraakRepo.GetByUserId(id)); // ObjectResult moet om CastException te vermijden
        }

        [HttpPost]
        public async Task<IActionResult> PostAfspraakAsync(AfspraakDTO model)
        {
            var user = await _userManager.FindByIdAsync(model.GebruikerId.ToString());
            if (user == null)
                return NotFound("Gebruiker niet gevonden!");

            var carwash = this._carwashRepo.GetById(model.CarwashId);
            if (carwash == null)
                return NotFound("Carwash aanbieding niet gevonden!");

            Afspraak afspraak = new Afspraak();
            model.UpdateFromModel(afspraak);

            afspraak.Gebruiker = user;
            afspraak.Carwash = carwash;

            _afspraakRepo.Add(afspraak);
            _afspraakRepo.SaveChanges();
            return CreatedAtAction(nameof(GetAll), afspraak);
        }

        [HttpDelete("{id}")]
        public ActionResult<Afspraak> DeleteAfspraak(int id)
        {
            Afspraak afspraak = _afspraakRepo.GetById(id);
            if (afspraak == null)
                return NotFound("De afspraak werd niet gevonden");

            _afspraakRepo.Remove(afspraak);
            _afspraakRepo.SaveChanges();

            return afspraak;
        }
    }
}
