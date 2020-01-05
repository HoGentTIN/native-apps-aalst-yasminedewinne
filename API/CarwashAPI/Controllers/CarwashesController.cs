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
    public class CarwashesController : ControllerBase
    {
        private readonly ICarwashRepository _carwashRepo;
        private readonly UserManager<User> _userManager;

        public CarwashesController(ICarwashRepository carwashRepository, UserManager<User> userManager)
        {
            this._carwashRepo = carwashRepository;
            this._userManager = userManager;
        }

        [HttpGet]
        public IEnumerable<Carwash> GetAll()
        {
            return _carwashRepo.GetAll();
        }

        [HttpGet("{id}")]
        public ActionResult<Carwash> GetById(int id)
        {
            Carwash carwash = _carwashRepo.GetById(id);
            if (carwash == null)
                return NotFound();

            return carwash;
        }

        [HttpGet]
        [Route("stad")]
        public IEnumerable<Carwash> GetByStad(string stad)
        {
            return _carwashRepo.GetCarwashesByStad(stad);
        }

        [HttpPost]
        public async Task<IActionResult> PostCarwashAsync(CarwashDTO model)
        {
            var user = await _userManager.FindByIdAsync(model.AanbiederId.ToString());
            if (user == null)
                return NotFound("Gebruiker niet gevonden!");

            Carwash carwash = new Carwash();
            model.UpdateFromModel(carwash);

            carwash.Aanbieder = user;

            _carwashRepo.Add(carwash);
            _carwashRepo.SaveChanges();
            return CreatedAtAction(nameof(GetAll), carwash);
        }

        [HttpPut("{id}")]
        public IActionResult PutCarwash(int id, CarwashDTO model)
        {
            Carwash carwash = _carwashRepo.GetById(id);
            if (carwash == null)
                return NotFound("Carwash aanbieding niet gevonden");

            model.UpdateFromModel(carwash);
            _carwashRepo.SaveChanges();
            return NoContent();
        }

        [HttpDelete("{id}")]
        public ActionResult<Carwash> DeleteCarwash(int id)
        {
            Carwash carwash = _carwashRepo.GetById(id);
            if (carwash == null)
                return NotFound();

            _carwashRepo.Remove(carwash);
            _carwashRepo.SaveChanges();

            return carwash;
        }
    }
}
