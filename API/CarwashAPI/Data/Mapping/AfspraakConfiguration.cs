using System;
using CarwashAPI.Models.Domain;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace CarwashAPI.Data.Mapping
{
    public class AfspraakConfiguration : IEntityTypeConfiguration<Afspraak>
    {
        public void Configure(EntityTypeBuilder<Afspraak> builder)
        {
            builder.ToTable("Afspraken");
            builder.HasKey(x => x.Id);

            builder
                .HasOne(x => x.Gebruiker)
                .WithMany()
                .IsRequired()
                .HasForeignKey(x=>x.GebruikerId)
                .OnDelete(DeleteBehavior.Cascade);

            builder
                .HasOne(x => x.Carwash)
                .WithMany()
                .IsRequired()
                .HasForeignKey(x => x.CarwashId)
                .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
