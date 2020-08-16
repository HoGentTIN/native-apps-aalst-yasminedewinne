package be.hogent.yasminedewinne.carwashapp.util.converters

import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeParseException
import org.junit.Assert
import org.junit.Test

class DateConverterTest {
    private val dateConverter = DateConverter()

    @Test
    fun toDate_nullString_returnsNull() {
        val input: String? = null
        val result = dateConverter.toDate(input)

        Assert.assertNull(result)
    }

    @Test
    fun toDate_validString_returnsDate() {
        val input: String? = "2019-12-09"
        val result = dateConverter.toDate(input)

        val localDate = LocalDate.of(2019, Month.DECEMBER, 9)

        Assert.assertNotNull(result)
        Assert.assertEquals(
            result!!,
            localDate
        ) // Actual werd reeds een maal berekend via een andere methode
    }

    @Test(expected = DateTimeParseException::class)
    fun toDate_invalidString_throwException() {
        val input: String? = "Dit is invalid"
        dateConverter.toDate(input)
    }

    @Test
    fun fromDate_nullDate_returnsNull() {
        val input: LocalDate? = null
        val result = dateConverter.fromDate(input)

        Assert.assertNull(result)
    }

    @Test
    fun fromDate_validDate_returnsString() {
        val input = LocalDate.of(2019, Month.DECEMBER, 9)
        val result = dateConverter.fromDate(input)

        Assert.assertNotNull(result)
        Assert.assertEquals(result, "2019-12-09")
    }
}

class DateAdapterTest {

    private val dateAdapter = DateAdapter()

    @Test
    fun fromString_validString_returnsDate() {
        val input = "2019-12-09T00:00:00"
        val result = dateAdapter.fromString(input)

        val localDate = LocalDate.of(2019, Month.DECEMBER, 9)
        Assert.assertEquals(result, localDate)
    }

    @Test(expected = DateTimeParseException::class)
    fun toDate_invalidString_throwException() {
        val input = "Dit is invalid"
        dateAdapter.fromString(input)
    }

    @Test
    fun fromDate_validDate_returnsString() {
        val input = LocalDate.of(2019, Month.DECEMBER, 9)
        val result = dateAdapter.toString(input)

        Assert.assertEquals(result, "2019-12-09T00:00:00")
    }
}
