using System;
using CarwashAPI.Models.Domain;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Newtonsoft.Json;

namespace CarwashAPI.Data.Mapping
{
    public class UserConfiguration : IEntityTypeConfiguration<User>
    {
        public void Configure(EntityTypeBuilder<User> builder)
        {
            builder.ToTable("User");
            builder.HasKey(x => x.Id);

            builder
                .Property(x => x.Adres)
                .HasConversion(
                    entity => JsonConvert.SerializeObject(entity),
                    value => JsonConvert.DeserializeObject<Adres>(value)
                )
                .IsRequired();

            builder.Property(x => x.Voornaam).HasMaxLength(25).IsRequired();
            builder.Property(x => x.Familienaam).HasMaxLength(25).IsRequired();

            builder.Property(x => x.UserName).HasMaxLength(50).IsRequired();
            builder.Property(x => x.NormalizedUserName).HasMaxLength(50).IsRequired();

            builder.Property(x => x.Email).HasMaxLength(50).IsRequired();
            builder.Property(x => x.NormalizedEmail).HasMaxLength(50).IsRequired();

            builder.Property(x => x.PhoneNumber).HasColumnName("TelefoonNr").HasMaxLength(15).IsRequired();
            builder.Property(x => x.PasswordHash).HasMaxLength(128).IsRequired();

            builder.Property(x => x.LockoutEnabled);
            builder.Property(x => x.LockoutEnd).IsRequired(false);
            builder.Property(x => x.AccessFailedCount).HasMaxLength(2).IsRequired();
            builder.Property(x => x.SecurityStamp).IsRequired();

            builder.Ignore(x => x.PhoneNumberConfirmed);
            builder.Ignore(x => x.TwoFactorEnabled);
            builder.Ignore(x => x.ConcurrencyStamp);
            builder.Ignore(x => x.EmailConfirmed);

            // Identity
            builder.HasMany<IdentityUserClaim<int>>().WithOne().HasForeignKey(uc => uc.UserId).IsRequired().OnDelete(DeleteBehavior.Cascade);
            builder.HasMany<IdentityUserLogin<int>>().WithOne().HasForeignKey(ul => ul.UserId).IsRequired().OnDelete(DeleteBehavior.Cascade);
            builder.HasMany<IdentityUserToken<int>>().WithOne().HasForeignKey(ut => ut.UserId).IsRequired().OnDelete(DeleteBehavior.Cascade);
        }
    }
}
