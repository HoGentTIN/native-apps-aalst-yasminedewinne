using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using CarwashAPI.DTO.Identity;
using CarwashAPI.Models.Domain;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;

namespace CarwashAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly SignInManager<User> _signInManager;
        private readonly UserManager<User> _userManager;

        private readonly IConfiguration _config;

        public AuthController(
            SignInManager<User> signInManager,
            UserManager<User> userManager,
            IConfiguration config)
        {

            this._signInManager = signInManager;
            this._userManager = userManager;
            this._config = config;
        }

        // POST: api/auth/login
        /// <summary>
        /// Login a user with email and password.
        /// </summary>
        /// <param name="model">A DTO containing email, password & remember me parameters</param>
        /// <returns>A model containing a JWT and the user picture</returns>
        [HttpPost("login")]
        public async Task<IActionResult> PostLogin(LoginDTO model)
        {
            User user = await _userManager.FindByEmailAsync(model.Email);
            if (user != null)
            {
                var result = await _signInManager.CheckPasswordSignInAsync(user, model.Password, false);
                if (result.Succeeded)
                {
                    string token = await GetTokenAsync(user);

                    return Ok(new
                    {
                        Token = token
                    });
                }
            }

            return Unauthorized("Invalid email or password");
        }

        private async Task<string> GetTokenAsync(User user)
        {
            var roles = await _userManager.GetClaimsAsync(user);

            var userclaims = new[]
            {
              new Claim("id", user.Id.ToString()),
              new Claim("email", user.Email),
              new Claim("voornaam", user.Voornaam),
              new Claim("familienaam", user.Familienaam),
              new Claim("telNr", user.PhoneNumber),
              new Claim("straatnaam", user.Adres.StraatNaam),
              new Claim("huisNr", user.Adres.HuisNr),
              new Claim("postcode", user.Adres.Postcode),
              new Claim("stad", user.Adres.Stad),
              new Claim("land", user.Adres.Land)
            };

            List<Claim> claims = new List<Claim>();
            claims.AddRange(userclaims);
            claims.AddRange(roles);

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["Tokens:Key"]));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(
              null, null,
              claims,
              expires: DateTime.Now.AddYears(1),
              signingCredentials: creds);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }

        // POST: api/auth/register
        /// <summary>
        /// Register a user with all details
        /// </summary>
        /// <param name="model"></param>
        /// <returns>A model containing a JWT and the user picture</returns>
        [HttpPost("register")]
        public async Task<IActionResult> PostRegister(RegisterDTO model)
        {
            User user = new User(model.Voornaam, model.Familienaam, model.Email, model.TelefoonNr, model.Adres);

            var result = await _userManager.CreateAsync(user, model.Wachtwoord);
            if (result.Succeeded)
            {
                await _userManager.AddClaimAsync(user, new Claim(ClaimTypes.Role, "User"));

                string token = await GetTokenAsync(user);
                return Ok(new
                {
                    Token = token
                });
            }

            return BadRequest("Registration failed");
        }

        // POST: api/auth/logout
        /// <summary>
        /// Log out the current user
        /// </summary>
        /// <returns>Always returns 200 Ok</returns>
        [HttpPost("logout")]
        public async Task<IActionResult> PostLogout()
        {
            await _signInManager.SignOutAsync();
            return Ok();
        }
    }
}
