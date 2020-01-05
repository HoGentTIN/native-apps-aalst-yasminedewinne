using System;
using System.Collections.Generic;
using CarwashAPI.Models.Domain;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Newtonsoft.Json;

namespace CarwashAPI.Data.Mapping
{
    public class CarwashConfiguration : IEntityTypeConfiguration<Carwash>
    {
        public void Configure(EntityTypeBuilder<Carwash> builder)
        {
            builder.ToTable("Carwash");
            builder.HasKey(x => x.Id);

            builder.Property(x => x.Auto).HasMaxLength(150).IsRequired();
            builder.Property(x => x.Datum).IsRequired();
            builder.Property(x => x.BeginUur).IsRequired();
            builder.Property(x => x.Tarief).IsRequired();


            builder
                .HasOne(x => x.Aanbieder)
                .WithMany()
                .IsRequired()
                .HasForeignKey(x => x.AanbiederId)
                .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
